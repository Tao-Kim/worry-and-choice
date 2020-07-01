package com.tao.wnc.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.databinding.ItemPostBinding;

public class PostListViewHolder extends RecyclerView.ViewHolder {

    private ItemPostBinding binding;

    public PostListViewHolder(final ItemPostBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemPostBinding getBinding() {
        return binding;
    }
}
