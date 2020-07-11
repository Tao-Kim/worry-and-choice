package com.tao.wnc.view.fragment;

import androidx.fragment.app.Fragment;

import com.coder.zzq.smartshow.dialog.LoadingDialog;
import com.tao.wnc.R;

public class BaseFragment extends Fragment {
    private LoadingDialog loadingDialog;

    protected void showProgressBar()  {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog()
                    .middle()
                    .withMsg(false)
                    .windowBackground(R.drawable.bg_item28);
        }
        loadingDialog.showInActivity(getActivity());
    }

    protected void hideProgressBar() {
        if(loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
