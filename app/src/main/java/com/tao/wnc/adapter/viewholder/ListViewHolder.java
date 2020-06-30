package com.tao.wnc.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.databinding.ItemListBinding;

public class ListViewHolder extends RecyclerView.ViewHolder {

    private ItemListBinding binding;

    public ListViewHolder(ItemListBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemListBinding getBinding() {
        return binding;
    }
}
