package com.ryanburnsworth.waldosphotossample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ryanburnsworth.waldosphotossample.remote.APIClient;
import com.ryanburnworth.waldosphotossample.PhotoQuery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class PhotoGalleryViewModel extends ViewModel {
    private final String TAG = "PhotoGalleryViewModel";
    private MutableLiveData<List<String>> photoUrlLiveDataList;
    private List<String> photoUrlList;

    public PhotoGalleryViewModel() {
        photoUrlList = new ArrayList<>();
        photoUrlLiveDataList = new MutableLiveData<>();
        getPhotoUrls();
    }

    private void getPhotoUrls() {
        PhotoQuery photoQuery = PhotoQuery.builder().build();
        APIClient.getApiClient().query(photoQuery).enqueue(new ApolloCall.Callback<PhotoQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<PhotoQuery.Data> response) {
                try {
                    int recordSize = response.data().album().photos().records().size() - 1;
                    for (int i = 0; i < recordSize; i++) {
                        for (int j = 0; j < response.data().album().photos().records().get(i).urls().size() - 1; j++) {
                            photoUrlList.add(response.data().album().photos().records().get(i).urls().get(j).url());
                        }
                    }
                    photoUrlLiveDataList.postValue(photoUrlList);
                } catch (NullPointerException e) {
                    Log.e(TAG, "ERROR: Response was empty!");
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(TAG, "ERROR: " + e.getMessage());
            }
        });
    }

    public LiveData<List<String>> getPhotoUrlLiveDataList() {
        return photoUrlLiveDataList;
    }
}