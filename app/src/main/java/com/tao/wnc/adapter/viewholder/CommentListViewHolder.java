package com.tao.wnc.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.databinding.ItemCommentBinding;

public class CommentListViewHolder extends RecyclerView.ViewHolder {

    private ItemCommentBinding binding;

    public CommentListViewHolder(final ItemCommentBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemCommentBinding getBinding() {
        return binding;
    }
}
