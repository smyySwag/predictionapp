package b1392982.uk.ac.tees.predictionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email;
    EditText password;
    CheckBox checkPassword;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.checkpassword);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticateLogin();

            }
        });
    }

    private void AuthenticateLogin(){
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (emailInput == null || emailInput.isEmpty() || emailInput.trim().isEmpty() || passwordInput == null || passwordInput.isEmpty() || passwordInput.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill the form", Toast.LENGTH_SHORT).show();
        } else fAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            if (fAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


        }
      });

    }

    private void checkbox(boolean isChecked, EditText text) {
        if (!isChecked) {
            // show password
            text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            // hide password
            text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}