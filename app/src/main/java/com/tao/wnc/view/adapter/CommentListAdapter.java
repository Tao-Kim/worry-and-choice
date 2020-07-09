package com.tao.wnc.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.R;
import com.tao.wnc.view.adapter.viewholder.CommentListViewHolder;
import com.tao.wnc.databinding.ItemCommentBinding;
import com.tao.wnc.model.domain.CommentItem;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListViewHolder> {

    private ArrayList<CommentItem> items;

    @NonNull
    @Override
    public CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comment, parent, false);
        return new CommentListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, final int position) {
        holder.getBinding().setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<CommentItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

}
