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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

public class Login extends AppCompatActivity {

    Hashtable<String, String> userInfo;
    Hashtable<String, String> serviceInfo;

    TextView welcome, prompt;
    Button login, forgot;
    EditText email, password;

    Animation fadein, uptodownPrompt, uptodownEmail, uptodownPassword, uptodownLogin, uptodownForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_open);

        userInfo = new Hashtable<String, String>();
        serviceInfo = new Hashtable<String, String>();

        userInfo.put("tb@user.com", "tarun");       //Hashtable stores username as key and password as value. userInfo stores our accounts for the User Side
        userInfo.put("rl@user.com", "ryan");
        userInfo.put("tr@user.com", "tharuveen");
        userInfo.put("jt@user.com", "jason");

        serviceInfo.put("tb@service.com", "tarun"); //like userInfo - serviceInfo stores our accounts for the Service Side
        serviceInfo.put("rl@service.com", "ryan");
        serviceInfo.put("tr@service.com", "tharuveen");
        serviceInfo.put("jt@service.com", "jason");

        welcome = findViewById(R.id.welcome);
        prompt = findViewById(R.id.prompt);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);

        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        uptodownPrompt = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownEmail = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownPassword = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownLogin = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        uptodownForgot = AnimationUtils.loadAnimation(this, R.anim.uptodown);

        //A LOAD OF DOMINO EFFECT ANIMATIONS (ANIMATION STARTS WHEN PREVIOUS ANIMATION ENDS)
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


        login.setOnClickListener(new View.OnClickListener() {   //if login button is clicked...
            @Override
            public void onClick(View v) {
                String enteredEmail = String.valueOf(email.getText());
                String enteredPassword = String.valueOf(password.getText());

                if (userInfo.containsKey(enteredEmail)){        //check if the user's entered email is in the Hashtable
                    if (userInfo.get(enteredEmail).equals(enteredPassword)){    //if it is then check if its value (password) is the same as the user's entered password
                        Intent userSide = new Intent(getApplicationContext(), userSide.class);
                        startActivity(userSide);    //if it is then open the userSide Activity
                    }

                }
                else if (serviceInfo.containsKey(enteredEmail)) {   //check if the service's entered email is in the Hashtable
                    if (serviceInfo.get(enteredEmail).equals(enteredPassword)) {    //if it is then check if its value (password) is the same as the service's entered password
                        Intent serviceSide = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(serviceSide); //if it is then open the serviceSide Activity
                    }

                }

                else {  //else then email or password is incorrect so we send a notification message to the user/service
                    Toast.makeText(getApplicationContext(), "Incorrect email/password!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}