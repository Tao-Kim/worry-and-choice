package com.tao.wnc.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tao.wnc.R;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.fragment.ListFragment;

public class MainActivity extends AppCompatActivity {


    private short listFragmentStatusCode = Constants.LIST_FRAGMENT_STATUS.NONE;

    public short getListFragmentStatusCode() {
        short returnStatusCode = listFragmentStatusCode;
        listFragmentStatusCode = Constants.LIST_FRAGMENT_STATUS.CREATED;
        return returnStatusCode;
    }

    public void setListFragmentStatusCode(short listFragmentStatusCode) {
        this.listFragmentStatusCode = listFragmentStatusCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_container, ListFragment.newInstance()).commit();
    }

    public void removeAndPop(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        fragmentManager.popBackStack();
    }

    public void replaceWithBackStack(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }

}