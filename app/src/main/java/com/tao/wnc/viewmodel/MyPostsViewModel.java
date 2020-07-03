package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.ListItem;

import java.util.ArrayList;

public class MyPostsViewModel extends ViewModel {
    private ArrayList<ListItem> items;

    public MyPostsViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<ListItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {

        ListItem item2 = new ListItem("고민이에요",
                "신발을 사려는데 무슨색을 살까요?",
                "50분전 | 타오",
                false,
                1,
                0);
        items.add(item2);

        ListItem item4 = new ListItem("TEST",
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
