package com.example.hy.recruitnew;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hy.recruitnew.bean.RegisterData;
import com.example.hy.recruitnew.util.StatusBarUtil;
import com.example.hy.recruitnew.util.ToastUtil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_sail)
    Button btnSail;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_school_number)
    EditText etSchoolNumber;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_academy)
    EditText etAcademy;
    @BindView(R.id.et_classes)
    EditText etClasses;
    @BindView(R.id.et_business)
    EditText etBusiness;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rb_background)
    RadioButton rbBackground;
    @BindView(R.id.rb_front)
    RadioButton rbFront;
    @BindView(R.id.rg_direction)
    RadioGroup rgDirection;
    @BindView(R.id.rb_android)
    RadioButton rbAndroid;
    @BindView(R.id.rb_bigData)
    RadioButton rbBigData;
    @BindView(R.id.rg_direction2)
    RadioGroup rgDirection2;
    @BindView(R.id.et_spectiality)
    EditText etSpectiality;
    @BindView(R.id.et_self_introduction)
    EditText etSelfIntroduction;
    @BindView(R.id.et_expection)
    EditText etExpection;
    @BindView(R.id.nsv_register)
    NestedScrollView nsvRegister;

    private Unbinder mUnbinder;
    private static final String KEY_TITLE = "keyTitle";
    private int mDirectionId = -1;
    private RadioButton mRadioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
        }
        setContentView(R.layout.activity_register);
        StatusBarUtil.compat(this);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(tlCommon);

        Intent intent = getIntent();
        int flag = -1;
        if (intent != null) {
            flag = intent.getIntExtra(KEY_TITLE, -1);
        }

        //设置toolbar
        setToolbatTitle(flag);
        tlCommon.setNavigationOnClickListener(v -> finish());

        mDirectionId = rbBackground.getId();
        mRadioButton = rbBackground;
        //选中按钮
        rgDirection.setOnCheckedChangeListener(((group, checkedId) -> {
            if(rbBackground.getId() == mDirectionId){
                mRadioButton = rbFront;
            }else if(rbFront.getId() == mDirectionId){
                mRadioButton = rbBackground;
            }else {
                mRadioButton = findViewById(mDirectionId);
                mRadioButton.setChecked(false);
                mDirectionId = group.getCheckedRadioButtonId();
                mRadioButton = findViewById(mDirectionId);
                mRadioButton.setChecked(true);
                Log.d("rain", "button: " + mRadioButton.getText().toString());
                return;
            }
            mDirectionId = group.getCheckedRadioButtonId();
            Log.d("rain", "button: " + mRadioButton.getText().toString());
        }));
        rgDirection2.setOnCheckedChangeListener(((group, checkedId) -> {
            if(rbAndroid.getId() == mDirectionId){
                mRadioButton = rbBigData;
            }else if(rbBigData.getId() == mDirectionId){
                mRadioButton = rbAndroid;
            }else {
                mRadioButton = findViewById(mDirectionId);
                mRadioButton.setChecked(false);
                mDirectionId = group.getCheckedRadioButtonId();
                mRadioButton = findViewById(mDirectionId);
                mRadioButton.setChecked(true);
                Log.d("rain", "button: " + mRadioButton.getText().toString());
                return;
            }
            mDirectionId = group.getCheckedRadioButtonId();
            Log.d("rain", "button: " + mRadioButton.getText().toString());
        }));

        //提交按钮
        btnSail.setOnClickListener(v -> register());

    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    public static void startActivity(Context context, int flag) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(KEY_TITLE, flag);
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityByExplode(Context context, int flag) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(KEY_TITLE, flag);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)context).toBundle());
    }

    /**
     * 提交报名表
     */
    private void register() {
        if(
                isEmpty(etName) || isEmpty(etSchoolNumber) || isEmpty(etSex) || isEmpty(etAcademy) ||
                        isEmpty(etClasses)|| isEmpty(etBusiness) || isEmpty(etPhoneNumber) || isEmpty(etQq) ||
                        isEmpty(etEmail) || isEmpty(etSpectiality) || isEmpty(etSelfIntroduction) || isEmpty(etExpection)
                ){
            ToastUtil.showToast(this, getString(R.string.register_error));
            return;
        }
        if(mRadioButton == null){
            ToastUtil.showToast(this, getString(R.string.register_error3));
            return;
        }
        RegisterData registerData = new RegisterData();
        registerData.setName(getEtString(etName));
        registerData.setSchoolNumber(getEtString(etSchoolNumber));
        registerData.setSex(getEtString(etSex));
        registerData.setClasses(getEtString(etClasses));
        registerData.setBusines(getEtString(etBusiness));
        registerData.setPhone(getEtString(etPhoneNumber));
        registerData.setQq(getEtString(etQq));
        registerData.setEmail(getEtString(etEmail));
        registerData.setSpectiality(getEtString(etSpectiality));
        registerData.setSpectiality(getEtString(etSelfIntroduction));
        registerData.setExpection(getEtString(etExpection));
        registerData.setDirection(mRadioButton.getText().toString());
        registerData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e != null){
                    if(e.getErrorCode() == 9015){
                        ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_error4));
                        return;
                    }
                    ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_error2));
                    return;
                }
                ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_success));
            }
        });
    }


    /**
     * 判断et是否填写
     */
    private boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    /**
     * 返回et中的字符串
     */
    private String  getEtString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 设置标题
     */
    private void setToolbatTitle(int flag) {
        switch (flag) {
            case 1:
                tlCommon.setTitle(R.string.register_android);
                break;
            case 2:
                tlCommon.setTitle(R.string.register_background);
                break;
            case 3:
                tlCommon.setTitle(R.string.register_front);
                break;
            case 4:
                tlCommon.setTitle(R.string.register_bigData);
                break;
            default:
                tlCommon.setTitle("");
                break;
        }
    }
}
