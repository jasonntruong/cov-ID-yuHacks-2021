package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Hashtable;

public class onOpen extends AppCompatActivity {

    Hashtable<String, String> userInfo;
    Hashtable<String, String> serviceInfo;

    TextView welcome, prompt;
    Button login, forgot;
    ImageView covidlogo;
    EditText email, password;

    Animation fadein, uptodownPrompt, uptodownEmail, uptodownPassword, uptodownLogin, uptodownForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_open);

        userInfo = new Hashtable<String, String>();
        serviceInfo = new Hashtable<String, String>();

        userInfo.put("tb@user.com", "tarun");
        userInfo.put("rl@user.com", "ryan");
        userInfo.put("tr@user.com", "tharuveen");
        userInfo.put("jt@user.com", "jason");

        serviceInfo.put("tb@service.com", "tarun");
        serviceInfo.put("rl@service.com", "ryan");
        serviceInfo.put("tr@service.com", "tharuveen");
        serviceInfo.put("jt@service.com", "jason");

        welcome = findViewById(R.id.welcome);
        prompt = findViewById(R.id.prompt);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);

        email.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        uptodownPrompt = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownEmail = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownPassword = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownLogin = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownForgot = AnimationUtils.loadAnimation(this, R.anim.uptodown);


        welcome.startAnimation(fadein);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                welcome.setAlpha(255);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                prompt.startAnimation(uptodownPrompt);
                uptodownPrompt.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        prompt.setAlpha(255);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        email.startAnimation(uptodownEmail);
                        uptodownEmail.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                email.setAlpha(255);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                password.startAnimation(uptodownPassword);
                                uptodownPassword.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        password.setAlpha(255);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        login.startAnimation(uptodownLogin);
                                        uptodownLogin.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                login.setAlpha(255);
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {

                                                forgot.startAnimation(uptodownForgot);
                                                uptodownForgot.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {
                                                        forgot.setAlpha(255);
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = String.valueOf(email.getText());
                String enteredPassword = String.valueOf(password.getText());

                if (userInfo.containsKey(enteredEmail)){
                    if (userInfo.get(enteredEmail).equals(enteredPassword)){
                        Intent userSide = new Intent(getApplicationContext(), pastMedical.class);
                        startActivity(userSide);
                    }

                }
                else if (serviceInfo.containsKey(enteredEmail)) {
                    if (serviceInfo.get(enteredEmail).equals(enteredPassword)) {
                        Intent serviceSide = new Intent(getApplicationContext(), MainActivity.class); //REPLACE WITH SERVICE SIDE HOME
                        startActivity(serviceSide);
                    }

                }

                else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}