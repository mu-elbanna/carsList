package com.eo.cars.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.eo.cars.databinding.RowCarBinding;

class CarsListVH extends RecyclerView.ViewHolder {

    final RowCarBinding binding;

    CarsListVH(final RowCarBinding binding) {
       super(binding.getRoot());

       this.binding = binding;
   }
}