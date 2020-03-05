package com.eo.cars.ui;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eo.cars.R;
import com.eo.cars.model.CarsResponse;
import com.eo.cars.model.Data;
import com.eo.cars.model.NetworkState;
import com.eo.cars.rest.ApiClient;
import com.eo.cars.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eo.cars.BaseActivity.isNetworkAvailable;
import static com.eo.cars.utils.Constants.FAILED;
import static com.eo.cars.utils.Constants.RUNNING;
import static com.eo.cars.utils.Constants.SUCCESS;


public class MainVM extends AndroidViewModel {

    private MutableLiveData<NetworkState> networkStateMutableLiveData;
    private MutableLiveData<ArrayList<Data>> carsResponseMutableLiveData;

    public MainVM(@NonNull Application application) {
        super(application);

        networkStateMutableLiveData = new MutableLiveData<>();
        carsResponseMutableLiveData = new MutableLiveData<>();

        getCarsList(1);
    }

    MutableLiveData<ArrayList<Data>> getCarsResponseMutableLiveData() {
        return carsResponseMutableLiveData;
    }

    MutableLiveData<NetworkState> getNetworkStateMutableLiveData() {
        return networkStateMutableLiveData;
    }

    void getCarsList(int page) {
        if (isNetworkAvailable(getApplication())) {
            networkStateMutableLiveData.postValue(new NetworkState(RUNNING));

            ApiInterface apiService = ApiClient.getClient(60, 30, 30).create(ApiInterface.class);

            Call<CarsResponse> call = apiService.getCarsList(page);
            call.enqueue(new Callback<CarsResponse>() {
                @Override
                public void onResponse(Call<CarsResponse> call, Response<CarsResponse> response) {
                    if (response.isSuccessful()) {
                        carsResponseMutableLiveData.postValue(response.body().getData());

                        networkStateMutableLiveData.postValue(new NetworkState(SUCCESS));
                    } else {
                        networkStateMutableLiveData.postValue(new NetworkState(FAILED, getApplication().getString(R.string.error_getting_data)));
                    }
                }

                @Override
                public void onFailure(Call<CarsResponse> call, Throwable t) {
                    t.printStackTrace();
                    networkStateMutableLiveData.postValue(new NetworkState(FAILED, getApplication().getString(R.string.error_getting_data)));
                }
            });

        } else {
            networkStateMutableLiveData.postValue(new NetworkState(FAILED, getApplication().getString(R.string.no_net)));
        }
    }
}