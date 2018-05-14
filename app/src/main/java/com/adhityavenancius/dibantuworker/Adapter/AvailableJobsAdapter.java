package com.adhityavenancius.dibantuworker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adhityavenancius.dibantuworker.Apihelper.UtilsApi;
import com.adhityavenancius.dibantuworker.MainActivity;
import com.adhityavenancius.dibantuworker.Model.ActivejobsItem;
import com.adhityavenancius.dibantuworker.Model.AvailablejobsItem;
import com.adhityavenancius.dibantuworker.OrderDetailActivity;
import com.adhityavenancius.dibantuworker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Adhitya Venancius on 5/10/2018.
 */

public class AvailableJobsAdapter extends RecyclerView.Adapter<AvailableJobsAdapter.AvailableJobsHolder> {
    List<AvailablejobsItem> availablejobsItemList;
    Context mContext;
    String categoryImageURL;

    public AvailableJobsAdapter(Context context, List<AvailablejobsItem> jobList){
        this.mContext = context;
        availablejobsItemList = jobList;
    }

    @Override
    public AvailableJobsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_jobs_card, parent, false);
        return new AvailableJobsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvailableJobsHolder holder, int position) {
        final AvailablejobsItem availablejobsitem = availablejobsItemList.get(position);
        holder.categoryname.setText(availablejobsitem.getCategoryname());
        holder.workername.setText(availablejobsitem.getUsername());
        holder.jobs_id.setText(availablejobsitem.getId());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        categoryImageURL = UtilsApi.UPLOAD_URL + availablejobsitem.getUserpicture();

        Glide.with(mContext).load(categoryImageURL).apply(options).into(holder.thumbnail);
//        Glide.with(mContext).load(R.drawable.tmp_logo).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return availablejobsItemList.size();
    }

    public class AvailableJobsHolder extends RecyclerView.ViewHolder{
        public TextView categoryname, workername,jobs_id;
        public ImageView thumbnail;

        public AvailableJobsHolder(View itemView) {
            super(itemView);
            categoryname = (TextView) itemView.findViewById(R.id.categoryname);
            workername = (TextView) itemView.findViewById(R.id.workername);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            jobs_id = (TextView) itemView.findViewById(R.id.jobs_id);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);

                    intent.putExtra("id_jobs",jobs_id.getText() );
                    v.getContext().startActivity(intent);
                }
            });

        }
    }


}
