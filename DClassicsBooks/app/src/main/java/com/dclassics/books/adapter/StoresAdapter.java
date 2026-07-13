package com.dclassics.books.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dclassics.books.R;
import com.dclassics.books.model.Store;

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
        holder.tvName.setText(store.getName());
        holder.tvAddress.setText(store.getAddress());
        holder.tvPhone.setText(store.getPhone());
        holder.btnVisit.setOnClickListener(v ->
            Toast.makeText(v.getContext(), "Opening maps for " + store.getName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() { return stores.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPhone;
        Button btnVisit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_store_name);
            tvAddress = itemView.findViewById(R.id.tv_store_address);
            tvPhone = itemView.findViewById(R.id.tv_store_phone);
            btnVisit = itemView.findViewById(R.id.btn_visit_store);
        }
    }
}
