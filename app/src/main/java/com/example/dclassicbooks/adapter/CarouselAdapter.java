package com.example.dclassicbooks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbooks.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    public static class CarouselItem {
        public final int imageResId;
        public final String name;
        public final String address;

        public CarouselItem(int imageResId, String name, String address) {
            this.imageResId = imageResId;
            this.name = name;
            this.address = address;
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
        if (items.isEmpty()) return;
        int actualPosition = position % items.size();
        CarouselItem item = items.get(actualPosition);
        holder.ivStoreBg.setImageResource(item.imageResId);
        holder.tvStoreName.setText(item.name);
        holder.tvStoreAddress.setText(item.address);
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 0 : Integer.MAX_VALUE;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStoreBg;
        TextView tvStoreName, tvStoreAddress;

        ViewHolder(View itemView) {
            super(itemView);
            ivStoreBg = itemView.findViewById(R.id.iv_store_bg);
            tvStoreName = itemView.findViewById(R.id.tv_store_name);
            tvStoreAddress = itemView.findViewById(R.id.tv_store_address);
        }
    }
}
