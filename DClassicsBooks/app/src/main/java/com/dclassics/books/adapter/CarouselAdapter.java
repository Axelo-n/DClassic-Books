package com.dclassics.books.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dclassics.books.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    public static class CarouselItem {
        public final String name;
        public final String address;
        public final int imageResId;

        public CarouselItem(String name, String address, int imageResId) {
            this.name = name;
            this.address = address;
            this.imageResId = imageResId;
        }
    }

    private final List<CarouselItem> items;

    public CarouselAdapter(List<CarouselItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarouselItem item = items.get(position);
        holder.ivBg.setImageResource(item.imageResId);
        holder.tvName.setText(item.name);
        holder.tvAddress.setText(item.address);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBg;
        TextView tvName, tvAddress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBg = itemView.findViewById(R.id.iv_carousel_bg);
            tvName = itemView.findViewById(R.id.tv_carousel_name);
            tvAddress = itemView.findViewById(R.id.tv_carousel_address);
        }
    }
}
