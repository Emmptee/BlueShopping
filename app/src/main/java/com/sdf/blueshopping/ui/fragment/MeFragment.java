package com.sdf.blueshopping.ui.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.ui.activity.TestActivity;
import com.sdf.blueshopping.utils.ToastUtil;
import com.sdf.blueshopping.utils.ToastUtilOra;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by shidongfang on 2017/8/8.
 */

public class MeFragment extends Fragment implements View.OnClickListener{

    private LinearLayout mFingerprintLayout;

    private static final String TAG = "CXSecuritySettings";
    private static final int FINGERPRINT_RENAME = 104;


    private TextView mChangePatternPasswd;
    private TextView mChangeNumberPasswd;
    private RadioGroup mLockPasswd;
    private ToggleButton mSafeProtect;

    private ContentResolver mContentResolver;
    private ToggleButton mAccessControlCard;
    private ToggleButton mIdCard;
    private ToggleButton mEntryUsageCard;
    private TextView mAddFingerprint;

    /*-----------------========================*/

    protected static final boolean DEBUG = true;

    private TextView mAdd;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cx_security_settings, null);

        ((TextView) view.findViewById(R.id.title)).setText(R.string.security_setting);
        TextView backView = (TextView) view.findViewById(R.id.title_back);
        backView.setOnClickListener(this);

        mChangePatternPasswd = (TextView) view.findViewById(R.id.change_pattern_passwd);
        mChangePatternPasswd.setOnClickListener(this);

        mChangeNumberPasswd = (TextView) view.findViewById(R.id.change_number_passwd);
        mChangeNumberPasswd.setOnClickListener(this);

        mLockPasswd = (RadioGroup)view.findViewById(R.id.rd_lock_passwd);
        /*mLockPasswd.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);*/
        //shidongfang2017/10/10

        /*btn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                 context = v.getContext();
                LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.add_fingerprint_item, null);
                relativeLayout.addView(view);
                ImageButton searchBoxBtn = (ImageButton) view .findViewById(R.id.searchBoxBtn);
                searchBoxBtn.setOnClickListener(new SearchBoxBtnOnClickListener( relativeLayout, view, tv));
                Toast.makeText(context, "searchBtn", Toast.LENGTH_SHORT).show();
            }
        });*/
        mAdd = (TextView) view.findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mFingerprintLayout = (LinearLayout) view.findViewById(R.id.fp_already_enroll);
        return view;

    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Context context = getActivity().getApplicationContext();
                Intent intent = new Intent(context, TestActivity.class);
                Bundle args = new Bundle();
                args.putString("fingerprint", "my");
                intent.putExtras(args);
                startActivityForResult(intent, FINGERPRINT_RENAME);
            break;
        }
    }
    private String nameReturn;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == FINGERPRINT_RENAME){
            Log.d(TAG, "onActivityResult: 成功启动activity");
            if (resultCode == TestActivity.FINGERPRINT_RENAME_FINISH){
//                Fingerprint fingerprintReturn = getActivity().getIntent().getParcelableExtra("fingerprint_rename");
                nameReturn = data.getStringExtra("fingerprint_rename_name");
//                Log.d(TAG, "onActivityResult: __" + fingerprintReturn.getName());
                Log.d(TAG, "onActivityResult: 2222222"  + nameReturn);
//                renameFingerPrint(fp.getFingerId(),nameReturn);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
