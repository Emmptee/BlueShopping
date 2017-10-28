package com.sdf.blueshopping.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.widget.ClearEditText;

import static android.R.attr.name;
import static android.app.Activity.RESULT_OK;

/**
 * Created by SDF on 2017/10/28.
 */

public class TestActivity extends FragmentActivity implements View.OnClickListener{
    private static final String TAG = "TestActivity";
    public static final int FINGERPRINT_RENAME_FINISH = 100;

    private TextView mDelete;
    private TextView mBack;
    protected static final boolean DEBUG = true;

    private Context mContext;
//    private Fingerprint mFp;
    private EditText mDialogTextField;
    private String mFingerName;
    private Boolean mTextHadFocus;
    private int mTextSelectionStart;
    private int mTextSelectionEnd;
    private ClearEditText mRename;
    private Intent mIntent;
    private CharSequence nowName;
    private String newName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        name = mIntent.getStringExtra("fingerprint");
        Log.d(TAG, "onCreate: " + name);
        if (savedInstanceState != null) {
            mFingerName = savedInstanceState.getString("fingerName");
            mTextHadFocus = savedInstanceState.getBoolean("textHadFocus");
            mTextSelectionStart = savedInstanceState.getInt("startSelection");
            mTextSelectionEnd = savedInstanceState.getInt("endSelection");
        }
        setContentView(R.layout.fingerprint_delete_layout);
        mBack = (TextView) findViewById(R.id.title_back);
        mBack.setOnClickListener(this);

        mRename = (ClearEditText) findViewById(R.id.fingerprint_item_rename);
//        CharSequence name = mFingerName == null ? mFp.getName() : mFingerName;
        mRename.setText(name);
        mRename.setSelection(name.length());

        mRename.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newName = mRename.getText().toString();
                Log.d(TAG, "afterTextChanged:  " + newName);
            }
        });
        mDelete = (TextView) findViewById(R.id.fingerprint_delete);
        mDelete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                setResultRename(newName);
                finish();
                break;
            case R.id.fingerprint_delete:
                break;
        }
    }

    public void setResultRename(String mFingerName){
        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putString("fingerprint_rename_name",mFingerName);
        intent.putExtras(args);
        Log.d(TAG, "setResultRename: " + intent.toString());
        setResult(FINGERPRINT_RENAME_FINISH,intent);
        Log.d(TAG, "setResultRename---name: " +mFingerName);
    }
    public void setResultDelete(){

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDialogTextField != null) {
            outState.putString("fingerName", mRename.getText().toString());
            outState.putBoolean("textHadFocus", mRename.hasFocus());
            outState.putInt("startSelection", mRename.getSelectionStart());
            outState.putInt("endSelection", mRename.getSelectionEnd());
        }
    }
}
