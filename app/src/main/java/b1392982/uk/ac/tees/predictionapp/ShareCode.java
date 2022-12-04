package b1392982.uk.ac.tees.predictionapp;
import android.app.Activity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ShareCode extends AppCompatActivity {

    //hide keyboard when tapped the blank spaces
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //check box to hide password or show them
    public void checkbox(boolean isChecked, EditText text) {
        if (!isChecked) {
            // show password
            text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            // hide password
            text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }


}
