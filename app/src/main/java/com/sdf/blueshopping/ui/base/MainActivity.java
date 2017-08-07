package com.sdf.blueshopping.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.ui.base.ui.base.BaseActivity;

import static android.R.id.tabcontent;
import static android.R.id.tabhost;

public class MainActivity extends BaseActivity {

    private FragmentTabHost tabhost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void initview(){
        tabhost = (FragmentTabHost) findViewById(R.id.main_tabHost);
        tabhost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        for (int i = 0; i <; i++) {

        }
    }
}
