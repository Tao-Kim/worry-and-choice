package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.domain.PostItem;

import java.util.ArrayList;

public class MyPostsViewModel extends ViewModel {
    private ArrayList<PostItem> items;

    public MyPostsViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<PostItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {

    }
}
