package com.example.pushnotificationproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends Activity {
    EditText name ,token;
    String inputName,txtToken;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name =(EditText) findViewById(R.id.inputName);
        token = (EditText) findViewById(R.id.txtToken);
    }

    public void userSave(View view){
        inputName = name.getText().toString();
        txtToken = token.getText().toString();
        String method= "register";
        BackGroundTask backGroundTask = new BackGroundTask(this);
        backGroundTask.execute(method,inputName,txtToken);
        finish();

    }
}
