package com.tao.wnc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.R;
import com.tao.wnc.adapter.viewholder.NotificationListViewHolder;
import com.tao.wnc.databinding.ItemNotificationBinding;
import com.tao.wnc.model.NotificationItem;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

    private ArrayList<NotificationItem> items;

    @NonNull
    @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_notification, parent, false);
        return new NotificationListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, final int position) {
        holder.getBinding().setItem(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(items.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<NotificationItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void onItemClick(NotificationItem item, int position){}
}
