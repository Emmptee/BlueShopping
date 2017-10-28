package com.sdf.blueshopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sdf.blueshopping.R;

/**
 * Created by shidongfang on 2017/8/8.
 */

public class MoreFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fingerprint_delete_layout, null);
        EditText editText = (EditText) view.findViewById(R.id.fingerprint_item_rename);

        return view;
    }
}
