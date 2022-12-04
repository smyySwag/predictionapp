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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends ShareCode {
    Button login;
    EditText email;
    EditText password;
    Button resend;
    CheckBox checkPassword;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
       firebaseUser = fAuth.getCurrentUser();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Use method resendVerification to resend the verification link
        resend = findViewById(R.id.resend);
        resend.setOnClickListener(view -> resendVerification());

        //using method checkbox to show or hide the password
        checkPassword = findViewById(R.id.checkpassword);
        checkPassword.setOnCheckedChangeListener((buttonView, isChecked) -> checkbox(isChecked, password));

        //using AuthenticateLogin method to validate and open the home page
        login = findViewById(R.id.login);
        login.setOnClickListener(v -> AuthenticateLogin());
    }

    //resend verification link if the email has not verified.
    private void resendVerification(){
        if(firebaseUser!=null){
            firebaseUser.reload();
            if(!firebaseUser.isEmailVerified()) {
                firebaseUser.sendEmailVerification();
                Toast.makeText(LoginActivity.this, "Email Sent!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(LoginActivity.this, "Your email has been verified! You can login now.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // login with registered email using firebase
    private void AuthenticateLogin(){
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (emailInput == null || emailInput.isEmpty() || emailInput.trim().isEmpty() || passwordInput == null || passwordInput.isEmpty() || passwordInput.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill the form", Toast.LENGTH_SHORT).show();
        } else fAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(task -> {
            //check if the email is verified or not
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

}