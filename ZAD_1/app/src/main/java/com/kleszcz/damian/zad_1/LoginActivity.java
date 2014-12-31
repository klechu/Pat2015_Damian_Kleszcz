package com.kleszcz.damian.zad_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class LoginActivity extends Activity {
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("sign", true ))
            intentStart();


    }

    public void singIn (View view){
        EditText email = (EditText) findViewById(R.id.email);
        TextView emailValidation = (TextView) findViewById(R.id.email_validation);
        EditText pass = (EditText) findViewById(R.id.pass);
        TextView passValidation = (TextView) findViewById(R.id.pass_validation);


        if((emailValidate(email)) && (passValidate(pass))) {
            preferences.edit().putBoolean("sign", true).apply();
            intentStart();
        }

        if (emailValidate(email))
            emailValidation.setText(getString(R.string.empty));
        else
            emailValidation.setText(getString(R.string.emailValidation));
        if (passValidate(pass))
            passValidation.setText(getString(R.string.empty));
        else
            passValidation.setText(getString(R.string.passValidation));
    }

    final boolean emailValidate(EditText toValid){
        String toValidString = toValid.getText().toString().trim();
        String Pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return toValidString.matches(Pattern) && (toValidString.length() > 0);
    }

    final boolean passValidate(EditText toValid){
        String toValidString = toValid.getText().toString().trim();
        String Pattern = "((.*(?=.{8,})[a-z]+[0-9]+[A-Z]+.*)|(.*(?=.{8,})[0-9]+[A-Z]+[a-z]+.*)|(.*(?=.{8,})[A-Z]+[a-z]+[0-9]+.*))";
        return toValidString.matches(Pattern) && (toValidString.length() > 8);
    }

    private void intentStart(){
        Intent intent;
        intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();
    }






}
