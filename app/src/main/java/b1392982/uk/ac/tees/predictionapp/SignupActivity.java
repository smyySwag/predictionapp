package b1392982.uk.ac.tees.predictionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    //pattern for password
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
        //for full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(v -> confirmInput());

        email = findViewById(R.id.email);
        
        password = findViewById(R.id.password);
        //using method checkbox to show or hide the password
        checkPassword = findViewById(R.id.checkpassword);
        checkPassword.setOnCheckedChangeListener((buttonView, isChecked) -> checkbox(isChecked, password));

        confirmPassword = findViewById(R.id.confirmPassword);
        //using method checkbox to show or hide the password
        checkPassword2 = findViewById(R.id.checkpassword2);
        checkPassword2.setOnCheckedChangeListener((buttonView, isChecked) -> checkbox(isChecked, confirmPassword));
    }

    //validate email address
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

    //validate password
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

    //validate if the retyped password is the same as the first password
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


    //check if the edit text fields are empty or not.
    private boolean checkEmpty(EditText text) {
        String str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //check box to hide password or show them
    private void checkbox(boolean isChecked, EditText text) {
        if (!isChecked) {
            // show password
            text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            // hide password
            text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    //hide the keyboard when pressing any blank layout spaces
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    // open another page when everything is validated.
    private void confirmInput() {
        validateEmail();
        validatePassword();
        if (!validateEmail() && !validatePassword() && !validateConfirmPassword()) {
            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
        }

    }

}