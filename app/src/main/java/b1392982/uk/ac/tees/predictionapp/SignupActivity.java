package b1392982.uk.ac.tees.predictionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^" + "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$");
    EditText email;
    EditText password;
    CheckBox checkPassword;
    CheckBox checkPassword2;
    EditText confirmPassword;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmInput(v);
            }
        });
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        checkPassword = findViewById(R.id.checkpassword);
        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             checkbox(isChecked, password);
            }
        });
        checkPassword2 = findViewById(R.id.checkpassword2);
        checkPassword2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkbox(isChecked,confirmPassword);
            }
        });
    }

    private boolean checkEmpty(EditText text) {
        String str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean validateEmail() {
        boolean emailbool = false;
        String emailInput = email.getText().toString();
        email.setError(null);
        if (checkEmpty(email)) {
            email.setError("This field can't be empty");
            emailbool = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("The email is incorrect");
            emailbool = true;
        }
        return emailbool;
    }


    private boolean validatePassword() {
        boolean passwordbool = false;
        String passwordInput = password.getText().toString();
        password.setError(null);
        if (checkEmpty(password)) {
            password.setError("This field can't be empty.");
            passwordbool = true;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("The password is incorrect");
            passwordbool = true;
        }
        return passwordbool;
    }

    private boolean validateConfirmPassword() {
        boolean confirmbool = false;
        String passwordInput = password.getText().toString();
        String confirmInput = confirmPassword.getText().toString();
        confirmPassword.setError(null);
        if (checkEmpty(confirmPassword)) {
            confirmPassword.setError("This field can't be empty.");
            confirmbool = true;
        } else if (!confirmInput.equals(passwordInput)) {
            confirmPassword.setError("Your password doesn't match.");
            confirmbool = true;
        }
        return confirmbool;
    }
     private void checkbox ( boolean isChecked, EditText text) {
         if (!isChecked) {
             // show password
             text.setTransformationMethod(PasswordTransformationMethod.getInstance());
         } else {
             // hide password
             text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
         }
     }


    private void confirmInput(View v) {
        validateEmail();
        validatePassword();
        if (!validateEmail() && !validatePassword() && !validateConfirmPassword()) {
            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
        }

    }
}