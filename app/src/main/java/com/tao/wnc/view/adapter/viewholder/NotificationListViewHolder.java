package com.tao.wnc.view.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.databinding.ItemNotificationBinding;

public class NotificationListViewHolder extends RecyclerView.ViewHolder {

    private ItemNotificationBinding binding;

    public NotificationListViewHolder(final ItemNotificationBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemNotificationBinding getBinding() {
        return binding;
    }
}
