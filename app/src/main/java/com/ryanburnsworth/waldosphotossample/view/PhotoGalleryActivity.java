package com.ryanburnsworth.waldosphotossample.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ryanburnsworth.waldosphotossample.R;
import com.ryanburnsworth.waldosphotossample.viewmodel.PhotoGalleryViewModel;

import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PhotoGalleryViewModel photoGalleryViewModel = ViewModelProviders.of(this).get(PhotoGalleryViewModel.class);
        photoGalleryViewModel.getPhotoUrlLiveDataList().observe(this, new android.arch.lifecycle.Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                for (String str : strings) {
                    // TODO: pass strings to PhotoGalleryAdapter
                }
            }
        });
    }
}
