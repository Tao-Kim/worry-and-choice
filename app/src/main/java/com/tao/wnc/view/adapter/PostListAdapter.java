package com.tao.wnc.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.R;
import com.tao.wnc.view.adapter.viewholder.PostListViewHolder;
import com.tao.wnc.databinding.ItemPostBinding;
import com.tao.wnc.model.domain.PostItem;

import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<PostListViewHolder> {

    private ArrayList<PostItem> items;

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_post, parent, false);
        return new PostListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, final int position) {
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

    public void setItems(ArrayList<PostItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void onItemClick(PostItem item, int position){}
}
