package com.ryanburnsworth.waldosphotossample.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ryanburnsworth.waldosphotossample.R;

import java.util.List;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.PhotoViewHolder> {
    private List<String> photoUrls;
    private Context context;

    public PhotoGalleryAdapter(Context context, List<String> photoUrls) {
        this.photoUrls = photoUrls;
        this.context = context;
    }

    @Override
    public PhotoGalleryAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoGalleryAdapter.PhotoViewHolder holder, int position) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.OVERLAY);
        circularProgressDrawable.start();
        Glide.with(context)
                .load(photoUrls.get(position))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .thumbnail(0.5f)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photoUrls.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
        }
    }

    public void addItems(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        notifyDataSetChanged();
    }
}
