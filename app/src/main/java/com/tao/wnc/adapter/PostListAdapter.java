package com.tao.wnc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.R;
import com.tao.wnc.adapter.viewholder.ListViewHolder;
import com.tao.wnc.databinding.ItemListBinding;
import com.tao.wnc.model.ListItem;

import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private ArrayList<ListItem> items;

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list, parent, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.getBinding().setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ListItem> items){
        this.items = items;
        notifyDataSetChanged();
    }
}
