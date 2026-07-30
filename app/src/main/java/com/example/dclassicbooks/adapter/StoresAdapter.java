package com.example.dclassicbooks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbooks.R;
import com.example.dclassicbooks.model.Store;

import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {

    private final List<Store> stores;

    public StoresAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.tvStoreName.setText(store.getName());
        holder.tvStoreAddress.setText(store.getAddress());
        holder.tvStorePhone.setText(store.getPhone());
    }

    @Override
    public int getItemCount() { return stores.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStoreName, tvStoreAddress, tvStorePhone;

        ViewHolder(View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tv_store_name);
            tvStoreAddress = itemView.findViewById(R.id.tv_store_address);
            tvStorePhone = itemView.findViewById(R.id.tv_store_phone);
        }
    }
}
