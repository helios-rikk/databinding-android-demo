package com.namhv.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

public class SettingProfileActivity extends AppCompatActivity {

    private RadioGroup mRgGender, mRgAge, mRgEducation, mRgAccountPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        mRgGender = (RadioGroup) findViewById(R.id.rg_gender);
        mRgAge = (RadioGroup) findViewById(R.id.rg_age);
        mRgEducation = (RadioGroup) findViewById(R.id.rg_education);
        mRgAccountPlan = (RadioGroup) findViewById(R.id.rg_account_plan);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender = ((RadioButton) findViewById(mRgGender.getCheckedRadioButtonId())).getText().toString();
                String age = ((RadioButton) findViewById(mRgAge.getCheckedRadioButtonId())).getText().toString();
                String education = ((RadioButton) findViewById(mRgEducation.getCheckedRadioButtonId())).getText().toString();
                String accountPlan = ((RadioButton) findViewById(mRgAccountPlan.getCheckedRadioButtonId())).getText().toString();
                FirebaseAnalytics.getInstance(SettingProfileActivity.this).setUserProperty("UserGender", gender);
                FirebaseAnalytics.getInstance(SettingProfileActivity.this).setUserProperty("UserAge", age);
                FirebaseAnalytics.getInstance(SettingProfileActivity.this).setUserProperty("UserEducation", education);
                FirebaseAnalytics.getInstance(SettingProfileActivity.this).setUserProperty("UserAccountPlan", accountPlan);

                // Start to main
                startActivity(new Intent(SettingProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
