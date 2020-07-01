package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.CommentItem;

import java.util.ArrayList;

public class ReadPostViewModel extends ViewModel {
    private ArrayList<CommentItem> items;

    public ReadPostViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<CommentItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {

        CommentItem item = new CommentItem("길동이",
                "피시방 알바 좋아요!!!",
                "2시간전");
        items.add(item);
        CommentItem item2 = new CommentItem("호동이",
                "편의점 알바 좋아요!!!",
                "3시간전");
        items.add(item2);
        CommentItem item3 = new CommentItem("타오",
                "둘다 좋아요!!!",
                "4시간전");
        items.add(item3);
        CommentItem item4 = new CommentItem("TEST",
                "test",
                "0시간전");
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
