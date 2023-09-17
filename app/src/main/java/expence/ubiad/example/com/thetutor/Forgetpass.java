package expence.ubiad.example.com.thetutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Forgetpass extends AppCompatActivity implements View.OnClickListener{
    private Button reset;
    private EditText email,password,retype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        email = (EditText) findViewById(R.id.user_id);
        email.setTextColor(Color.parseColor("#8b8a8a"));
        password = (EditText) findViewById(R.id.user_pass);
        password.setTextColor(Color.parseColor("#8b8a8a"));
        retype = (EditText) findViewById(R.id.re_type);
        retype.setTextColor(Color.parseColor("#8b8a8a"));
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }



    private void Resetpass() {

        final String user_id = email.getText().toString().trim();
        final String user_pass = password.getText().toString().trim();




        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Forgetpass.this, "Password Reseting...", "Please Wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Password Reset Successfully")){
                    Toast.makeText(Forgetpass.this, "Password Reset Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Forgetpass.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Forgetpass.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(Config.R_USER_ID, user_id);
                hashMap.put(Config.R_USER_PASS, user_pass);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_RESET_PASS, hashMap);
                return s;

            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }


    @Override
    public void onClick(View v) {
        final String user_id = email.getText().toString().trim();
        /**
         //         * Validation
         //
         //         */
        boolean invalid = false;
        if (email.getText().length() == 0) {
            email.requestFocus();
            email.setError("Please Enter Your Emai Address");
        } else if (!user_id.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            email.requestFocus();
            email.setError("Invalid Email Address");
        } else if (password.getText().length() == 0) {
            email.requestFocus();
            password.setError("Please Enter A Password");
        } else if (retype.getText().length() == 0) {
            email.requestFocus();
            retype.setError("Retype Password");
        } else if (v == reset) {
            if (password.getText().toString().equals(retype.getText().toString())) {
                Resetpass();

            } else {
                //Toast is the pop up message
                Toast.makeText(getApplicationContext(), "Password does not match!",
                        Toast.LENGTH_LONG).show();
            }

        }


    }
}
