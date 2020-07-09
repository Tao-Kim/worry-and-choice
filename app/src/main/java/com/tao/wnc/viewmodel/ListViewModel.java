package com.tao.wnc.viewmodel;


import androidx.lifecycle.ViewModel;

import com.tao.wnc.model.domain.PostItem;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private ArrayList<PostItem> items;

    public ListViewModel() {
        items = new ArrayList<>();
        setTestDataSet();
    }

    public ArrayList<PostItem> getListItems() {

        return items;
    }

    private void setTestDataSet() {
        PostItem item = new PostItem("치킨",
                "치킨을 먹으려는데 무슨 치킨을 먹어야 할까요 너무 많아서 고민이에요 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ",
                "30분전 | 아유",
                false,
                1,
                0);
        items.add(item);

        PostItem item2 = new PostItem("고민이에요",
                "신발을 사려는데 무슨색을 살까요?",
                "50분전 | 타오",
                false,
                1,
                0);
        items.add(item2);

        PostItem item3 = new PostItem("무슨 알바를 할까요?",
                "피시방알바와 편의점알바 자리가 있는데 어떤 알바를 하는게 더 좋을까요? 추천 부탁해요!!!!!!!!!!!!!",
                "2시간전 | 아유",
                true,
                14,
                12);
        items.add(item3);

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
    }
}
