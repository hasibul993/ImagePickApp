package com.imagepickapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imagepickapp.Model.ImageModel;
import com.imagepickapp.R;

import java.io.File;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    Context context;
    List<ImageModel> modelArrayList;


    public ImageAdapter(Context context, List<ImageModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView fileNameTV;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.icon);
            fileNameTV = (TextView) itemView.findViewById(R.id.fileNameTV);

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            ImageModel imageModel = modelArrayList.get(position);

            holder.fileNameTV.setText(imageModel.fileName);
            try {
                Glide.with(context)
                        .load(Uri.fromFile(new File(imageModel.filePath)))
                        .placeholder(context.getResources().getDrawable(R.drawable.profile_icon_grey))
                        .error(context.getResources().getDrawable(R.drawable.profile_icon_grey))
                        .into(holder.imageView);
            } catch (Exception ex) {
                ex.printStackTrace();
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return modelArrayList != null ? modelArrayList.size() : 0;
    }

    public ImageModel getItem(int position) {
        // TODO Auto-generated method stub
        return modelArrayList.get(position);
    }


    public void UpdateList(List<ImageModel> newList) {
        try {
            modelArrayList = newList;
            notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addItem(ImageModel imageModel) {
        try {
            modelArrayList.add(imageModel);
            notifyItemRangeInserted(0, modelArrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}