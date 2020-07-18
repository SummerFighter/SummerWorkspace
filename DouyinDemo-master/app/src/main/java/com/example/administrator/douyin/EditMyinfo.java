package com.example.administrator.douyin;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Controller.Constant;
import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditMyinfo extends AppCompatActivity {

    private EditText usernameET, schoolET, birthET, areaET,passwordET;
    private DatePicker datePicker;
    private RadioGroup radioGroup;
    private RadioButton radioButtonBoy;
    private RadioButton radioButtonGirl;

    private String gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        usernameET = findViewById(R.id.textView10);
        schoolET = findViewById(R.id.textView14);
        areaET = findViewById(R.id.textView15);
        birthET = findViewById(R.id.textView17);
        passwordET = findViewById(R.id.textView18);
        radioGroup = findViewById(R.id.textView9);
        radioButtonBoy = findViewById(R.id.man);
        radioButtonGirl = findViewById(R.id.woman);

        datePicker=findViewById(R.id.dp_datetimepicker_date);
        datePicker.init(2020, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                birthET.setText(format.format(calendar.getTime()));
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButtonBoy.isChecked()){
                    gender = "男";
                }else{
                    gender = "女";
                }
            }
        });

    }

    public void OnClick_editButton(View v)
    {
        final String username = usernameET.getText().toString();
        final String area = areaET.getText().toString();
        final String school = schoolET.getText().toString();
        final String birth = birthET.getText().toString();
        final String password = passwordET.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("account", Constant.currentUser.getAccount())
                .add("gender", gender)
                .add("username",username)
                .add("password",password)
                .add("school",school)
                .add("area",area)
                .build();
        String url = HttpUtil.rootUrl +"setUserInfo";
        HttpUtil.sendPostRequest(url, requestBody, new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    public void OnClick_avatarChange(View v){

    }

    public void OnClick_backInfo(View v){

    }
}
