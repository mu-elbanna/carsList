package com.eo.cars.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eo.cars.R;
import com.eo.cars.databinding.RowCarBinding;
import com.eo.cars.model.Data;

import java.util.ArrayList;

public class CarsListAdapter extends RecyclerView.Adapter<CarsListVH> {

    private ArrayList<Data> items;

    CarsListAdapter(ArrayList<Data> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CarsListVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        RowCarBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_car, viewGroup, false);
        return new CarsListVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsListVH holder, int i) {
        final Data data = items.get(i);

        holder.binding.setVar(data);
    }

    void clearItems() {
        items.clear();
    }

    void updateItems(ArrayList<Data> Data) {
        items.addAll(Data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items == null || items.size() == 0) {
            return 0;
        }
        return items.size();
    }
}