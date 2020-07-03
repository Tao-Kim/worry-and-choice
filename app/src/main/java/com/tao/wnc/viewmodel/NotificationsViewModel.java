package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.NotificationItem;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    private ArrayList<NotificationItem> items;

    public NotificationsViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<NotificationItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {

        NotificationItem item = new NotificationItem("나의 고민",
                "새로운 선택이 도착했어요.",
                "50분전");
        items.add(item);

        NotificationItem item2 = new NotificationItem("도와준 고민",
                "글쓴이가 선택했어요!",
                "2시간전");
        items.add(item2);


        NotificationItem item3 = new NotificationItem("나의 고민",
                "새로운 댓글이 도착했어요.",
                "1일전");
        items.add(item3);

        NotificationItem item4 = new NotificationItem("TEST",
                "test",
                "0일전");
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
