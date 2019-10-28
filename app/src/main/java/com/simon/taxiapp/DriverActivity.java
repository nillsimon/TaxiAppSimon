package com.simon.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverActivity extends AppCompatActivity {

    private static final String TAG = "DriverSignInActivity";

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private ProgressBar authBar;

    private Button loginSignUpButton;
    private TextView toggleLoginSignUpTextView;

    private boolean isLoginModeActive;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        authBar = findViewById(R.id.authBar);

        loginSignUpButton = findViewById(R.id.loginSignUpButton);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);

        auth = FirebaseAuth.getInstance();

    }

    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Введите адрес электронной почты");
            return false;
        }else {
            textInputEmail.setError("");
            return true;
        }
    }
    private boolean validateName(){
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            textInputName.setError("Введите адрес электронной почты");
            return false;
        }else if (nameInput.length() > 15 ) {
            textInputName.setError("Длина имени не более 15 символов");
            return false;
        }else {
            textInputName.setError("");
            return true;
        }
    }

       private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Введите адрес электронной почты");
            return false;
        }else if (passwordInput.length() < 7 ) {
            textInputPassword.setError("Длина пароля не менее 7 символов");
            return false;
        }else {
            textInputPassword.setError("");
            return true;
        }
    }



    private boolean validateConfirmPassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

           if (!passwordInput.equals(confirmPasswordInput)) {
               textInputPassword.setError("Пароль не совпвдают");
               return false;
           }else {
               textInputPassword.setError("");
               return true;
           }
    }

    public void loginSignUpUser(View view) {
        authBar.setVisibility(ProgressBar.VISIBLE);

        if(!validateEmail() || !validateName() || !validatePassword()){
         return;
      }
        if(isLoginModeActive){
            auth.signInWithEmailAndPassword(
                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInUserWithEmail:success");
                                authBar.setVisibility(ProgressBar.INVISIBLE);

                                Toast.makeText(DriverActivity.this,"Вы успешно вошли "+"\n" + "с адресом: "
                                        + textInputEmail.getEditText().getText().toString().trim()  + "\n" +"c именем: "
                                        + textInputName.getEditText().getText().toString().trim() + "\n"+"и паролем: "
                                        + textInputPassword.getEditText().getText().toString().trim(), Toast.LENGTH_LONG).show();

                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(DriverActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                               // updateUI(null);
                            }

                            // ...
                        }
                    });
        }else {
            if(!validateEmail() | !validateName() | !validatePassword() | !validateConfirmPassword()){
                return;
            }
            auth.createUserWithEmailAndPassword(

                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                authBar.setVisibility(ProgressBar.INVISIBLE);

                                Toast.makeText(DriverActivity.this,"Вы успешно зарегистрированы "+"\n" + "с адресом: "
                                        + textInputEmail.getEditText().getText().toString().trim()  + "\n" +"c именем: "
                                        + textInputName.getEditText().getText().toString().trim() + "\n"+"и паролем: "
                                        + textInputPassword.getEditText().getText().toString().trim(), Toast.LENGTH_LONG).show();

                                FirebaseUser user = auth.getCurrentUser();
                                // updateUI(user);
                            } else {
                                authBar.setVisibility(ProgressBar.INVISIBLE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(DriverActivity.this, "Такой пользователь уже зарегистрирован",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    public void toggleLoginSignUp(View view) {

        if (isLoginModeActive) {
            isLoginModeActive = false;
            loginSignUpButton.setText("Sign Up");
            toggleLoginSignUpTextView.setText("Or, log in");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            loginSignUpButton.setText("Log In");
            toggleLoginSignUpTextView.setText("Or, sign up");
            textInputConfirmPassword.setVisibility(View.GONE);
        }
    }
}
