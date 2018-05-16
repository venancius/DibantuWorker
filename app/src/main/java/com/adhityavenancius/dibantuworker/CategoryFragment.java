package com.adhityavenancius.dibantuworker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adhityavenancius.dibantuworker.Adapter.AvailableJobsAdapter;
import com.adhityavenancius.dibantuworker.Adapter.CategoryAdapter;
import com.adhityavenancius.dibantuworker.Apihelper.BaseApiService;
import com.adhityavenancius.dibantuworker.Apihelper.UtilsApi;
import com.adhityavenancius.dibantuworker.Helper.BottomNavigationViewHelper;
import com.adhityavenancius.dibantuworker.Model.AllcategoryItem;
import com.adhityavenancius.dibantuworker.Model.AvailablejobsItem;
import com.adhityavenancius.dibantuworker.Model.ResponseAvailableJobs;
import com.adhityavenancius.dibantuworker.Model.ResponseCategory;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    List<AvailablejobsItem> availablejobsItemList = new ArrayList<>();
    AvailableJobsAdapter availableJobsAdapter;


    BaseApiService mApiService;
    RecyclerView rvCategory;
    ProgressDialog loading;

    SessionManager session;
    String id_worker;
    Context mContext;

    private OnFragmentInteractionListener mListener;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.104:3000");
        } catch (URISyntaxException e) {}
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.on("new job", onNewJob);
        mSocket.connect();
    }

    private Emitter.Listener onNewJob = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Handler uiThreadHandler = new Handler(Looper.getMainLooper());
            uiThreadHandler.post(new Runnable()
            {
                public void run()
                {
                    getResultListCategory();
                }
            } );


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rvCategory = (RecyclerView) view.findViewById(R.id.recycler_view);

        mContext = getActivity();
        mApiService = UtilsApi.getAPIService();

        // get user data from session
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        // obtain id
        id_worker = user.get(SessionManager.KEY_ID);

        availableJobsAdapter = new AvailableJobsAdapter(mContext, availablejobsItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvCategory.setLayoutManager(mLayoutManager);
        rvCategory.setItemAnimator(new DefaultItemAnimator());

        getResultListCategory();



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getResultListCategory(){
        loading = ProgressDialog.show(mContext, null, "Processing Request..", true, false);

        mApiService.getAvailableJobs(id_worker).enqueue(new Callback<ResponseAvailableJobs>() {
            @Override
            public void onResponse(Call<ResponseAvailableJobs> call, Response<ResponseAvailableJobs> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<AvailablejobsItem> availablejobsItems = response.body().getAvailablejobs();

                    rvCategory.setAdapter(new AvailableJobsAdapter(mContext, availablejobsItems));
                    availableJobsAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toasty.error(mContext, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAvailableJobs> call, Throwable t) {
                loading.dismiss();
                Toasty.error(mContext, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
