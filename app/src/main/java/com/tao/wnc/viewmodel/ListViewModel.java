package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.ListItem;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private ArrayList<ListItem> items;

    public ListViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<ListItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {
        ListItem item = new ListItem("치킨",
                "치킨을 먹으려는데 무슨 치킨을 먹어야 할까요 너무 많아서 고민이에요 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ",
                "30분전 | 아유",
                false,
                1,
                0);
        items.add(item);

        ListItem item2 = new ListItem("고민이에요",
                "신발을 사려는데 무슨색을 살까요?",
                "50분전 | 타오",
                false,
                1,
                0);
        items.add(item2);

        ListItem item3 = new ListItem("무슨 알바를 할까요?",
                "피시방알바와 편의점알바 자리가 있는데 어떤 알바를 하는게 더 좋을까요? 추천 부탁해요!!!!!!!!!!!!!",
                "2시간전 | 아유",
                true,
                14,
                12);
        items.add(item3);

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
    }
}
