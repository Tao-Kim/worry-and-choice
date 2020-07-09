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

        PostItem item2 = new PostItem("고민이에요",
                "신발을 사려는데 무슨색을 살까요?",
                "50분전 | 타오",
                false,
                1,
                0);
        items.add(item2);

        PostItem item4 = new PostItem("TEST",
                "teset",
                "0시간전 | 타오",
                true,
                0,
                0);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
        items.add(item4);
    }
}
