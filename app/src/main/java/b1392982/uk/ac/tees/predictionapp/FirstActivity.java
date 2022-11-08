package b1392982.uk.ac.tees.predictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

    public class FirstActivity extends AppCompatActivity {
        Button email;
        Button login;
        Button google;
        GoogleSignInOptions gso;
        GoogleSignInClient gsc;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first);

            email = findViewById(R.id.email);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(FirstActivity.this, SignupActivity.class));
                }
            });

            login = findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                }
            });

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if(account!= null){
                navigateToHomeActivity();
            }

            google = findViewById(R.id.google);
            google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });

        }
        private void signIn() {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 10000){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try{
                    task.getResult(ApiException.class);
                    navigateToHomeActivity();
                } catch (ApiException e){
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void navigateToHomeActivity(){
            finish();
            Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
            startActivity(intent);
        }
}