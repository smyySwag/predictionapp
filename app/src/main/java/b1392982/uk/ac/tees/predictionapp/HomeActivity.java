package b1392982.uk.ac.tees.predictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import extra_java_classes.NothingSelectedSpinnerAdapter;
import extra_java_classes.ShareCode;

public class HomeActivity extends ShareCode {
    Spinner spinner_cp, spinner_ca, spinner_fbs, spinner_thal, spinner_exang, spinner_restecg, spinner_gender;
    Button predict, setting;
    EditText  age, trestbps, chol, thalach, oldpeak, slope;
    TextView result;
    String gender_string, cp_string, fbs_string, thal_string, restecg_string, exang_string, ca_string;
    String url = "https://python-flask-api.azurewebsites.net/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, SettingActivity.class)));

        spinner_gender = findViewById(R.id.gender_spinner);
        spinner_cp = findViewById(R.id.cp_spinner);
        spinner_ca = findViewById(R.id.ca_spinner);
        spinner_fbs = findViewById(R.id.fbs_spinner);
        spinner_thal = findViewById(R.id.thalassemia_spinner);
        spinner_exang = findViewById(R.id.exang_spinner);
        spinner_restecg = findViewById(R.id.restecg_spinner);

        age = findViewById(R.id.age_text);
        trestbps = findViewById(R.id.trestbps_text);
        chol = findViewById(R.id.chol_text);
        thalach = findViewById(R.id.thalch_text);
        oldpeak = findViewById(R.id.oldpeak_text);
        slope = findViewById(R.id.slope_text);

        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);


        //for gender (sex)
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        gender_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for gender
        if (spinner_gender.getAdapter().toString().equals("female")){
            gender_string = "0";
        }else gender_string = "1";

        //for chest pain type (cp)
        ArrayAdapter<CharSequence> cp_adapter = ArrayAdapter.createFromResource(this, R.array.cp_array, R.layout.spinner_item);
        cp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cp.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        cp_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for cp
        if (spinner_cp.getAdapter().toString().equals("typical angina")){
            cp_string = "0";
        }else if (spinner_cp.getAdapter().toString().equals("atypical angina")){
            cp_string = "1";
        }else if (spinner_cp.getAdapter().toString().equals("non-anginal pain")){
            cp_string = "2";
        }else  cp_string = "3";

        //for fbs
        ArrayAdapter<CharSequence> fbs_adapter = ArrayAdapter.createFromResource(this, R.array.fbs_array, R.layout.spinner_item);
        fbs_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fbs.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        fbs_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for fbs
        if (spinner_fbs.getAdapter().toString().equals("no")){
            fbs_string = "0";
        }else fbs_string = "1";

        //for thalassaemia (thal)
        ArrayAdapter<CharSequence> thal_adapter = ArrayAdapter.createFromResource(this, R.array.thal_array, R.layout.spinner_item);
        thal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_thal.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        thal_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for thal
        if (spinner_thal.getAdapter().toString().equals("normal")) {
            thal_string = "0";
        }else if (spinner_thal.getAdapter().toString().equals("fixed defect")){
            thal_string = "1";
        }else thal_string = "2";

        //for exercise induced angina (exang)
        ArrayAdapter<CharSequence> exang_adapter = ArrayAdapter.createFromResource(this, R.array.exang_array, R.layout.spinner_item);
        exang_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_exang.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        exang_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for exang
        if (spinner_exang.getAdapter().toString().equals("no")){
            exang_string = "0";
        }else exang_string = "1";

        //for coronary artery disease (ca)
        ArrayAdapter<CharSequence> ca_adapter = ArrayAdapter.createFromResource(this, R.array.ca_array, R.layout.spinner_item);
        ca_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ca.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        ca_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for ca
        if (spinner_ca.getAdapter().toString().equals("none")){
            ca_string = "0";
        }else if (spinner_ca.getAdapter().toString().equals("Obstructive")){
            ca_string = "1";
        }else if (spinner_ca.getAdapter().toString().equals("Non-obstructive")){
            ca_string = "2";
        }else  ca_string = "3";

        //for resting electrocardiographic result (Restecg)
        ArrayAdapter<CharSequence> restecg_adapter = ArrayAdapter.createFromResource(this, R.array.restecg_array, R.layout.spinner_item);
        restecg_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_restecg.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        restecg_adapter,
                        R.layout.activity_home,
                        this));

        //changing values for restecg
        if (spinner_restecg.getAdapter().toString().equals("normal")) {
            restecg_string = "0";
        }else if (spinner_restecg.getAdapter().toString().equals("having ST-T wave abnormality")){
            restecg_string = "1";
        }else restecg_string = "2";

        //prediction
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("heart_disease");
                                    if(data.equals("1")){
                                        startActivity(new Intent(HomeActivity.this, HeartdiseaseActivity.class));
                                    }else{
                                        startActivity(new Intent(HomeActivity.this, NoheartdiseaseActivity.class));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(HomeActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put ("age",age.getText().toString());
                        params.put ("cp", cp_string);
                        params.put ("trestbps",trestbps.getText().toString());
                        params.put ("chol",chol.getText().toString());
                        params.put ("thalach",thalach.getText().toString());
                        params.put ("oldpeak", oldpeak.getText().toString());
                        params.put ("slope", slope.getText().toString());
                        params.put ("ca", ca_string);
                        params.put ("thal", thal_string);
                        params.put ("sex",gender_string);
                        params.put ("fbs", fbs_string);
                        params.put ("restecg", restecg_string);
                        params.put ("exang", exang_string);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                queue.add(stringRequest);
            }
        });
    }
    // This will clear the task stack and exit the app
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}