package com.example.hy.recruitnew;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hy.recruitnew.http.HttpUtils;
import com.example.hy.recruitnew.http.RegisterData;
import com.example.utilslibrary.ToastUtil;
import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_sail)
    Button btnSail;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_school_number)
    EditText etSchoolNumber;
    @BindView(R.id.et_profession)
    EditText etProfession;
    @BindView(R.id.et_classes)
    EditText etClasses;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
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
    @BindView(R.id.et_self_introduction)
    EditText etSelfIntroduction;
    @BindView(R.id.nsv_register)
    NestedScrollView nsvRegister;
    @BindView(R.id.cb_nan)
    CheckBox cbNan;
    @BindView(R.id.cb_nv)
    CheckBox cbNv;
    @BindView(R.id.til_school_number)
    TextInputLayout tilSchoolNumber;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.til_profession)
    TextInputLayout tilProfession;
    @BindView(R.id.til_classes)
    TextInputLayout tilClasses;
    @BindView(R.id.til_phone_number)
    TextInputLayout tilPhoneNumber;
    @BindView(R.id.til_self_introduction)
    TextInputLayout tilSelfIntroduction;

    private static String TAG = RegisterActivity.class.getSimpleName() + "Rain";
    private Unbinder mUnbinder;
    private static final String KEY_TITLE = "keyTitle";
    private int mDirectionId = -1;
    private RadioButton mRadioButton = null;
    private HttpUtils mHttpUtils;
    private int tempId = -1;

    /**
     * api1，需替换成自己的服务器URL
     */
    private static final String captchaURL = "http://47.106.131.6/user/init";
    /**
     * api2，需替换成自己的服务器URL
     */
    private static final String validateURL = "http://47.106.131.6/user/validateAndAdd";

    private GT3GeetestUtils gt3GeetestUtils;
    private GT3ConfigBean gt3ConfigBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
        }
        setContentView(R.layout.activity_register);

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

        //性别选中按钮
        cbNan.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                if (tempId != -1 && tempId != view.getId()) {
                    CheckBox checkBox = findViewById(tempId);
                    if (checkBox != null) checkBox.setChecked(false);
                }
            }
            tempId = view.getId();
        });
        cbNv.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                if (tempId != -1 && tempId != view.getId()) {
                    CheckBox checkBox = findViewById(tempId);
                    if (checkBox != null) checkBox.setChecked(false);
                }
            }
            tempId = view.getId();
        });

        //方向选中按钮
        rgDirection.setOnCheckedChangeListener(((group, checkedId) -> {
            if (rbBackground.getId() == mDirectionId) {
                mRadioButton = rbFront;
            } else if (rbFront.getId() == mDirectionId) {
                mRadioButton = rbBackground;
            } else {
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
            if (rbAndroid.getId() == mDirectionId) {
                mRadioButton = rbBigData;
            } else if (rbBigData.getId() == mDirectionId) {
                mRadioButton = rbAndroid;
            } else {
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

        //在oncreate方法里初始化GT3GeetestUtils
        gt3GeetestUtils = new GT3GeetestUtils(this);

    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        gt3GeetestUtils.destory();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 横竖屏切换
        gt3GeetestUtils.changeDialogLayout();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityByExplode(Context context, int flag) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(KEY_TITLE, flag);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
    }

    /**
     * 提交报名表
     */
    private void register() {

        boolean isChecked = true;

        tilClasses.setErrorEnabled(false);
        tilName.setErrorEnabled(false);
        tilPhoneNumber.setErrorEnabled(false);
        tilProfession.setErrorEnabled(false);
        tilSelfIntroduction.setErrorEnabled(false);
        tilSchoolNumber.setErrorEnabled(false);

        if (mRadioButton == null) {
            ToastUtil.showToast(this, getString(R.string.register_error3));
            return;
        }
        if (!cbNan.isChecked() && !cbNv.isChecked()) {
            ToastUtil.showToast(this, getString(R.string.register_error5));
            return;
        }

        //判空
        if (isEmpty(etName)){
            showError(tilName, getString(R.string.register_error10));
            isChecked = false;
        }
        if (isEmpty(etProfession)){
            showError(tilProfession, getString(R.string.register_error10));
            isChecked = false;
        }
        if (isEmpty(etClasses)){
            showError(tilClasses, getString(R.string.register_error10));
            isChecked = false;
        }
        if (isEmpty(etSelfIntroduction)){
            showError(tilSelfIntroduction, getString(R.string.register_error10));
            isChecked = false;
        }
        if (isEmpty(etSchoolNumber)){
            showError(tilSchoolNumber, getString(R.string.register_error10));
            isChecked = false;
        }
        if (isEmpty(etPhoneNumber)){
            showError(tilPhoneNumber, getString(R.string.register_error10));
            isChecked = false;
        }
        if(!isChecked) return;

        //判长短
        if (getEtString(etSchoolNumber).length() < 10 || getEtString(etSchoolNumber).length() > 10) {
            showError(tilSchoolNumber, getString(R.string.register_error6));
            isChecked = false;
        }
        if (getEtString(etPhoneNumber).length() < 11 || getEtString(etPhoneNumber).length() > 11) {
            showError(tilPhoneNumber, getString(R.string.register_error7));
            isChecked = false;
        }
        if(!isChecked) return;

        //判合法字符
        if(!getEtString(etSchoolNumber).matches("[0-9]{0,}")){
            showError(tilSchoolNumber, getString(R.string.register_error8));
            isChecked = false;
        }
        if(!getEtString(etPhoneNumber).matches("[0-9]{0,}")){
            showError(tilPhoneNumber, getString(R.string.register_error8));
            isChecked = false;
        }
        if(isValidity(getEtString(etName))){
            showError(tilName, getString(R.string.register_error9));
            isChecked = false;
        }
        if(isValidity(getEtString(etProfession))){
            showError(tilProfession, getString(R.string.register_error9));
            isChecked = false;
        }
        if(isValidity(getEtString(etClasses))){
            showError(tilClasses, getString(R.string.register_error9));
            isChecked = false;
        }
        if(!isChecked) return;

        if(isChecked){
            if (mHttpUtils == null)
                mHttpUtils = new HttpUtils(this);
            // 配置bean文件，也可在oncreate初始化
            gt3ConfigBean = new GT3ConfigBean();
            // 设置验证模式，1：bind，2：unbind
            gt3ConfigBean.setPattern(1);
            // 设置点击灰色区域是否消失，默认不消失
            gt3ConfigBean.setCanceledOnTouchOutside(false);
            // 设置debug模式，开代理可调试 TODO 线上版本关闭
            gt3ConfigBean.setDebug(false);
            // 设置语言，如果为null则使用系统默认语言
            gt3ConfigBean.setLang(null);
            // 设置加载webview超时时间，单位毫秒，默认10000，仅且webview加载静态文件超时，不包括之前的http请求
            gt3ConfigBean.setTimeout(10000);
            // 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
            gt3ConfigBean.setWebviewTimeout(10000);
            //设置回调监听
            gt3ConfigBean.setListener(new GT3Listener() {

                /**
                 * api1回调
                 */
                @Override
                public void onButtonClick() {
                    Log.e(TAG, "GT3BaseListener-->onButtonClick()");
                    //启动api1
                    new RequestAPI1().execute();
                }

                /**
                 * api1结果回调
                 */
                @Override
                public void onApi1Result(String s) {
                    Log.e(TAG, "GT3BaseListener-->onApi1Result()-->" + s);

                }

                /**
                 * 验证码加载完成
                 * @param s 加载时间和版本等信息，为json格式
                 */
                @Override
                public void onDialogReady(String s) {
                    Log.e(TAG, "GT3BaseListener-->onDialogReady-->" + s);

                }

                /**
                 * 验证结果
                 */
                @Override
                public void onDialogResult(String s) {
                    Log.e(TAG, "GT3BaseListener-->onDialogResult-->" + s);
                    //启动api2
                    new RequestAPI2().execute(s);
                }

                /**
                 * api2结果回调
                 */
                @Override
                public void onApi2Result(String s) {
                    Log.e(TAG, "GT3BaseListener-->onApi2Result-->" + s);
                }

                /**
                 * 统计信息，参考接入文档
                 */
                @Override
                public void onStatistics(String s) {
                    Log.e(TAG, "GT3BaseListener-->onStatistics-->" + s);
                }

                /**
                 * 验证码被关闭
                 * @param i 1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码
                 */
                @Override
                public void onClosed(int i) {
                    Log.e(TAG, "GT3BaseListener-->onClosed-->" + i);
                }

                /**
                 * 验证成功回调
                 */
                @Override
                public void onSuccess(String s) {
                    Log.e(TAG, "GT3BaseListener-->onSuccess-->" + s);
                }

                /**
                 * 验证失败回调
                 * @param gt3ErrorBean 版本号，错误码，错误描述等信息
                 */
                @Override
                public void onFailed(GT3ErrorBean gt3ErrorBean) {
                    Log.e(TAG, "GT3BaseListener-->onFailed-->" + gt3ErrorBean.toString());
                }

            });
            // 开启验证
            gt3GeetestUtils.init(gt3ConfigBean);
            gt3GeetestUtils.startCustomFlow();
        }
    }


    /**
     * 请求api1
     */
    class RequestAPI1 extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String string = mHttpUtils.requestGet(captchaURL);
            Log.e(TAG, "RequestAPI1-->doInBackground: " + string);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(string);
                jsonObject.put("new_captcha", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            // 继续验证
            Log.i(TAG, "RequestAPI1-->onPostExecute: " + parmas);
            // SDK可识别格式为
            // {"success":1,"challenge":"06fbb267def3c3c9530d62aa2d56d018","gt":"019924a82c70bb123aae90d483087f94","new_captcha":true}
            // TODO 设置返回api1数据，即使为null也要设置，SDK内部已处理
            gt3ConfigBean.setApi1Json(parmas);
            // 继续api验证
            gt3GeetestUtils.getGeetest();
        }
    }

    /**
     * 请求api2
     */
    class RequestAPI2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (!TextUtils.isEmpty(params[0])) {
                try {
                    JSONObject jsonObject = new JSONObject(params[0]);
                    RegisterData registerData = new RegisterData();
                    registerData.setChallenge(jsonObject.getString("geetest_challenge"));
                    registerData.setSeccode(jsonObject.getString("geetest_seccode"));
                    registerData.setValidate(jsonObject.getString("geetest_validate"));
                    registerData.setName(getEtString(etName));
                    registerData.setSchoolNumber(getEtString(etSchoolNumber));
                    registerData.setClasses(getEtString(etClasses));
                    registerData.setPhone(getEtString(etPhoneNumber));
                    registerData.setSelfIntroduction(getEtString(etSelfIntroduction));
                    registerData.setProfession(getEtString(etProfession));
                    registerData.setDirection(mRadioButton.getText().toString());
                    registerData.setSex(cbNan.isChecked() ? 0 : 1);
                    return mHttpUtils.requestPost(validateURL, registerData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "RequestAPI2-->onPostExecute: " + result);
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("result");
                    if ("success".equals(status)) {
                        gt3GeetestUtils.showSuccessDialog();
                        ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_success));
                    } else {
                        String fail = jsonObject.getString("message");
                        gt3GeetestUtils.dismissGeetestDialog();
                        ToastUtil.showToast(RegisterActivity.this, fail);
                    }
                } catch (Exception e) {
                    gt3GeetestUtils.showFailedDialog();
                    ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_error2));
                    e.printStackTrace();
                }
            } else {
                gt3GeetestUtils.dismissGeetestDialog();
                ToastUtil.showToast(RegisterActivity.this, getString(R.string.register_error2));
            }
        }
    }

    /**
     * 显示错误提示，并获取焦点
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 判断是否含有非法字符
     */
    private boolean isValidity(String matchStr){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(matchStr);
        return m.find();
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
    private String getEtString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 设置标题
     */
    private void setToolbatTitle(int flag) {
        switch (flag) {
            case 1:
                tlCommon.setTitle(R.string.register_android);
                mDirectionId = rbAndroid.getId();
                mRadioButton = rbAndroid;
                mRadioButton.setChecked(true);
                rbBackground.setEnabled(false);
                rbBigData.setEnabled(false);
                rbFront.setEnabled(false);
                break;
            case 2:
                tlCommon.setTitle(R.string.register_background);
                mDirectionId = rbBackground.getId();
                mRadioButton = rbBackground;
                mRadioButton.setChecked(true);
                rbAndroid.setEnabled(false);
                rbBigData.setEnabled(false);
                rbFront.setEnabled(false);
                break;
            case 3:
                tlCommon.setTitle(R.string.register_front);
                mDirectionId = rbFront.getId();
                mRadioButton = rbFront;
                mRadioButton.setChecked(true);
                rbBackground.setEnabled(false);
                rbBigData.setEnabled(false);
                rbAndroid.setEnabled(false);
                break;
            case 4:
                tlCommon.setTitle(R.string.register_bigData);
                mDirectionId = rbBigData.getId();
                mRadioButton = rbBigData;
                mRadioButton.setChecked(true);
                rbBackground.setEnabled(false);
                rbAndroid.setEnabled(false);
                rbFront.setEnabled(false);
                break;
            default:
                tlCommon.setTitle("");
                break;
        }
    }

    public static void startActivity(Context context, int flag) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(KEY_TITLE, flag);
        context.startActivity(intent);
    }
}
