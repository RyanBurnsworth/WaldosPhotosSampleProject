package com.ryanburnsworth.waldosphotossample.view;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ryanburnsworth.waldosphotossample.R;
import com.ryanburnsworth.waldosphotossample.util.NetworkConnectionUtil;
import com.ryanburnsworth.waldosphotossample.viewmodel.PhotoGalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkConnectionUtil networkConnectionUtil = new NetworkConnectionUtil(getBaseContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(getApplicationContext(), new ArrayList<String>());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        PhotoGalleryViewModel photoGalleryViewModel = ViewModelProviders.of(this).get(PhotoGalleryViewModel.class);
        if (networkConnectionUtil.isNetworkAvailable()) {
            photoGalleryViewModel.getPhotoUrlLiveDataList().observe(this, new android.arch.lifecycle.Observer<List<String>>() {
                @Override
                public void onChanged(@Nullable List<String> strings) {
                    adapter.addItems(strings);
                }
            });
        } else {
            Toast.makeText(getBaseContext(), R.string.error_no_network, Toast.LENGTH_SHORT).show();
        }
    }
}
