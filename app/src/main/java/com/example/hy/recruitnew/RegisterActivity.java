package com.example.hy.recruitnew;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(tlCommon);
    }
}
