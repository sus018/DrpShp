package com.susmita.drpshp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    public List<Products> productsList;
    public ProductListAdapter(List<Products> productsList){
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.productId.setText(productsList.get(position).getProductId());
        holder.customerId.setText(productsList.get(position).getCustomerId());
        holder.brandCode.setText(productsList.get(position).getBrandCode());
        holder.brandName.setText(productsList.get(position).getBrandName());
        holder.productCode.setText(productsList.get(position).getProductCode());
        holder.productDesc.setText(productsList.get(position).getProductDesc());
        holder.mrp.setText(productsList.get(position).getMrp());
        holder.expiry.setText(productsList.get(position).getExpiry());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productId, customerId, brandCode, brandName, productCode, productDesc, mrp, expiry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.productId);
            customerId = itemView.findViewById(R.id.customerId);
            brandCode = itemView.findViewById(R.id.brandCode);
            brandName = itemView.findViewById(R.id.brandName);
            productCode = itemView.findViewById(R.id.productCode);
            productDesc = itemView.findViewById(R.id.productDesc);
            mrp = itemView.findViewById(R.id.mrp);
            expiry = itemView.findViewById(R.id.expiry);

        }
    }
}
