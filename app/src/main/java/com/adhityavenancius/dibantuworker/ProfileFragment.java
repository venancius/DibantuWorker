package com.adhityavenancius.dibantuworker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.adhityavenancius.dibantuworker.Adapter.ActiveJobsAdapter;
import com.adhityavenancius.dibantuworker.Adapter.HistoryJobsAdapter;
import com.adhityavenancius.dibantuworker.Apihelper.BaseApiService;
import com.adhityavenancius.dibantuworker.Apihelper.UtilsApi;
import com.adhityavenancius.dibantuworker.Model.ActivejobsItem;
import com.adhityavenancius.dibantuworker.Model.HistoryjobsItem;
import com.adhityavenancius.dibantuworker.Model.ResponseJobs;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SessionManager session;
    String id_user,role_user,tmp_name,tmp_email,tmp_phone;
    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;
    public TextView tvName,tvAddress,tvCity,tvEmail,tvPhone,tvLogout;
    public RatingBar ratingBar;
    public ImageView imgPicture;
    String pictureImageURL;
    private ViewSwitcher cardViewSwitcher;
    Button btnUpload,btnEditProfile,btnSaveProfile,btnCancel;
    public EditText etName,etEmail,etPhone;
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestStoragePermission();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = (TextView)view.findViewById(R.id.tvName);
        tvAddress = (TextView)view.findViewById(R.id.tvAddress);
        tvCity = (TextView)view.findViewById(R.id.tvCity);
        tvEmail = (TextView)view.findViewById(R.id.tvEmail);

        tvPhone = (TextView)view.findViewById(R.id.tvPhone);
        imgPicture = (ImageView)view.findViewById(R.id.imgPicture);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        //viewswitcher
        cardViewSwitcher = (ViewSwitcher)view.findViewById(R.id.cardViewSwitcher);
        //animation untuk viewswitcher
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        //set animasi ke viewswitcher
        cardViewSwitcher.setInAnimation(in);
        cardViewSwitcher.setOutAnimation(out);
        //edittext di bind di viewswitcher
        etName = (EditText)cardViewSwitcher.findViewById(R.id.etName);
        etPhone = (EditText)cardViewSwitcher.findViewById(R.id.etPhone);
        etEmail = (EditText)cardViewSwitcher.findViewById(R.id.etEmail);

        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        // obtain id
        id_user = user.get(SessionManager.KEY_ID);
        role_user = user.get(SessionManager.KEY_ROLE);
        
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService();

        btnUpload = (Button)view.findViewById(R.id.btnUpload);
        btnEditProfile = (Button)view.findViewById(R.id.btnEditProfile);
        btnSaveProfile = (Button)view.findViewById(R.id.btnSaveProfile);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        tvLogout = (TextView)view.findViewById(R.id.tvLogout);

        tvLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                session.logoutUser();
                getActivity().finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                showFileChooser();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cardViewSwitcher.showNext();

            }
        });

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tmp_name = etName.getText().toString();
                tmp_phone = etPhone.getText().toString();
                tmp_email = etEmail.getText().toString();

                setUserProfile(id_user,tmp_name,tmp_phone,tmp_email);
                cardViewSwitcher.showNext();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cardViewSwitcher.showNext();
            }
        });



        
        getWorkerProfile();
        
        return view;
    }
    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//                Toast.makeText(mContext,filePath.toString(),Toast.LENGTH_SHORT).show();
                uploadMultipart();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //upload
    public void uploadMultipart() {

        //getting the actual path of the image
        String path = getPath(filePath);
//        Toast.makeText(mContext,path,Toast.LENGTH_SHORT).show();

        File file =  new File(path);
//        Toast.makeText(mContext,file.getName(),Toast.LENGTH_SHORT).show();


        RequestBody reqFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(filePath)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), reqFile);

        RequestBody role =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, role_user);

        RequestBody id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, id_user);

        loading = ProgressDialog.show(mContext, null, "Processing Request..", true, false);

        mApiService.uploadImage(body,id,role).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){

//                            String upload_data = jsonRESULTS.getString("upload_data");
                            Toasty.success(mContext, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            getWorkerProfile();

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("error");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    Toasty.error(mContext, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toasty.error(mContext, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

//    @Override
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
    
    private void getWorkerProfile(){
        loading = ProgressDialog.show(mContext, null, "Processing Request..", true, false);

        mApiService.getWorkerProfile(id_user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){

                            String name = jsonRESULTS.getJSONObject("userdata").getString("name");
                            String phone = jsonRESULTS.getJSONObject("userdata").getString("phone");
                            String address = jsonRESULTS.getJSONObject("userdata").getString("address");
                            String city = jsonRESULTS.getJSONObject("citydata").getString("name");
//                            String identityno = jsonRESULTS.getJSONObject("userdata").getString("identity_no");
                            String email = jsonRESULTS.getJSONObject("userdata").getString("email");
                            String picture = jsonRESULTS.getJSONObject("userdata").getString("picture");
                            String rate = jsonRESULTS.getJSONObject("userdata").getString("rate");

                            Float float_rate = Float.parseFloat(rate);
                            ratingBar.setRating(float_rate);
                            ratingBar.setEnabled(false);
                            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                            if(picture.equals("")){
                                picture = "tmp_logo.png";
                            }

                            tvName.setText(name);
                            tvPhone.setText(phone);
                            tvCity.setText(city);
                            tvEmail.setText(email);
//                            tvIdentityNo.setText(identityno);
                            tvAddress.setText(address);

                            etName.setText(name, TextView.BufferType.EDITABLE);
                            etPhone.setText(phone, TextView.BufferType.EDITABLE);
                            etEmail.setText(email, TextView.BufferType.EDITABLE);

                            pictureImageURL = UtilsApi.UPLOAD_URL + picture ;
                            RequestOptions options = new RequestOptions()
                                    .centerCrop().circleCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);
                            Glide.with(mContext).load(pictureImageURL).apply(options).into(imgPicture);


                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("message");
                            Toasty.error(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    Toasty.error(mContext, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toasty.error(mContext, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toasty.success(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toasty.error(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setUserProfile(String id,String name,String phone,String email){
        loading = ProgressDialog.show(mContext, null, "Processing Request..", true, false);

        mApiService.setWorkerProfile(id,name,phone,email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){

                            String message = jsonRESULTS.getString("message");
                            Toasty.success(mContext, message, Toast.LENGTH_SHORT).show();

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("message");
                            Toasty.error(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    Toasty.error(mContext, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toasty.error(mContext, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
