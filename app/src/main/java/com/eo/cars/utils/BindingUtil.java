package com.eo.cars.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.eo.cars.R;
import com.squareup.picasso.Picasso;

public class BindingUtil {
    @BindingAdapter("imageURL")
    public static void loadImage(ImageView imageView, String image_url) {
        Picasso
                .with(imageView.getContext())
                .load(image_url)
                .placeholder(R.mipmap.car_placeholder)
                .into(imageView);
    }
}
