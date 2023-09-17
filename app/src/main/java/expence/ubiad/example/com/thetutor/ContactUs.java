package expence.ubiad.example.com.thetutor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ubiad on 12/06/2017.
 */
public class ContactUs extends Activity implements View.OnClickListener{
    private Button btn_submit;
    private EditText user_id1,edt_student_name,edt_student_fathername,edt_student_mobile_no, edt_student_message;

    //private TextView forget_passwrod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        edt_student_name = (EditText)findViewById(R.id.edt_student_name);
        edt_student_fathername = (EditText)findViewById(R.id.edt_student_father_name);
        edt_student_mobile_no = (EditText)findViewById(R.id.edt_student_mobile_no);
        edt_student_message = (EditText)findViewById(R.id.edt_comment);
        btn_submit = (Button)findViewById(R.id.submit);
        btn_submit.setOnClickListener(this);



        //Initializing textview
        user_id1 = (EditText)findViewById(R.id.user_id);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String emp_id = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        user_id1.setText(emp_id);
//.............
        // functio call
        GETUSERINFO();
    }



    //to get data of user
    private void GETUSERINFO() {
        String id = user_id1.getText().toString().trim();
        String url = Config.URL_GET_USER_PROFILE+user_id1.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showprofile(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactUs.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showprofile(String response){
        String user_name="";
        String user_id="";
        String user_mbl_no = "";



        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.T_JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            user_name = collegeData.getString(Config.TAG_USER_NAME);
            user_id = collegeData.getString(Config.TAG_USER_EMAIL);
            user_mbl_no = collegeData.getString(Config.TAG_USER_NUMBER);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        edt_student_name.setText(user_name);
edt_student_fathername.setText(user_id);
        edt_student_mobile_no.setText(user_mbl_no);
    }




    private void Contact() {
        final String studentname = edt_student_name.getText().toString().trim();
        final String student_email = edt_student_fathername.getText().toString().trim();
        final String  studentmobileno =  edt_student_mobile_no.getText().toString().trim();
        final String studentmesage =  edt_student_message.getText().toString().trim();

        class StudentContact extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ContactUs.this, "Submiting...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Request Sent Successfully")){
                    Toast.makeText(ContactUs.this, "Request Sent Successfully! We will contact you soon. Thankyou!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ContactUs.this,Navigation.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ContactUs.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.TAG_STUDENT_NAME,studentname);
                hashMap.put(Config.TAG_STUDENT_FATHERNAME,student_email);
                hashMap.put(Config.TAG_STUDENT_MOBILENO,studentmobileno);
                hashMap.put(Config.TAG_STUDENT_MESSAGE,studentmesage);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.CONTACT_US, hashMap);
                return s;
            }
        }
        StudentContact ue = new StudentContact();
        ue.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_submit) {
            if (edt_student_name.getText().length()==0) {
                edt_student_name.setError("Please Enter Name");
            }
            if (edt_student_fathername.getText().length()==0) {
                edt_student_fathername.setError("Please Enter Email");
            }
            if (edt_student_mobile_no.getText().length()==0) {
                edt_student_mobile_no.setError("Please Enter  Mobile number");
            }
            if (edt_student_message.getText().length()==0) {
                edt_student_message.setError("Please Enter A Message");
            }
            else {
                Contact();
            }

        }
    }
}
