package com.devprotocols.oracledatabase.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devprotocols.oracledatabase.databinding.ItemLayoutBinding;
import com.devprotocols.oracledatabase.models.MyModel;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<MyModel> myModelList = new ArrayList<>();
    Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemLayoutBinding.txtArticleCode.setText(myModelList.get(position).getArticleCode());
        holder.itemLayoutBinding.txtBarcode.setText(myModelList.get(position).getBarcode());
        holder.itemLayoutBinding.txtDesEng.setText(myModelList.get(position).getDescriptionEng());
        holder.itemLayoutBinding.txtDesAr.setText(myModelList.get(position).getDescriptionAr());
        holder.itemLayoutBinding.txtPrice.setText(myModelList.get(position).getPrice());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<MyModel> myList) {
        myModelList = new ArrayList<>();
        myModelList = myList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return myModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutBinding itemLayoutBinding;

        public ViewHolder(ItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }
}
