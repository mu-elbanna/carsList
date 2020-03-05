package com.eo.cars.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eo.cars.BaseActivity;
import com.eo.cars.R;
import com.eo.cars.databinding.ActivityMainBinding;
import com.eo.cars.model.Data;
import com.eo.cars.model.NetworkState;

import java.util.ArrayList;

import static com.eo.cars.utils.Constants.FAILED;
import static com.eo.cars.utils.Constants.RUNNING;
import static com.eo.cars.utils.Constants.SUCCESS;

public class MainActivity extends BaseActivity {

    public MainVM vm;
    public ActivityMainBinding binding;
    private CarsListAdapter carsListAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        vm = ViewModelProviders.of(this).get(MainVM.class);

        binding.setLifecycleOwner(this);

        binding.mainView.addView(progressBar);

        setCarsListAdapter();

        vm.getNetworkStateMutableLiveData().observe(this, this::onChangedNetwork);
        vm.getCarsResponseMutableLiveData().observe(this, this::onChangedData);

        binding.pullToRefresh.setOnRefreshListener(() -> {
            page = 1;
            vm.getCarsList(page);
        });
    }

    private void initScrollListener() {
        binding.rcvCarsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == carsListAdapter.getItemCount() - 1) {
                    page = page + 1;
                    vm.getCarsList(page);
                }
            }
        });
    }

    private void onChangedData(ArrayList<Data> results) {
        if (results != null) {
            if (page == 1)
                carsListAdapter.clearItems();

            carsListAdapter.updateItems(results);
        }
    }

    private void setCarsListAdapter() {
        binding.rcvCarsList.setItemAnimator(new DefaultItemAnimator());

        carsListAdapter = new CarsListAdapter(new ArrayList<>());
        binding.rcvCarsList.setAdapter(carsListAdapter);

        initScrollListener();
    }

    private void onChangedNetwork(@Nullable NetworkState networkState) {
        if (networkState != null) {
            switch (networkState.getStatus()) {
                case RUNNING:
                    showProgress();
                    break;

                case SUCCESS:
                    hideProgress();
                    binding.pullToRefresh.setRefreshing(false);
                    break;

                case FAILED:
                    hideProgress();
                    binding.pullToRefresh.setRefreshing(false);
                    showMsg(networkState.getPayLoad());
                    break;
            }
        }
    }
}
