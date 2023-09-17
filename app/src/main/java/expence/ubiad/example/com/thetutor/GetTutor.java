package expence.ubiad.example.com.thetutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ubiad on 07/06/2017.
 */
public class GetTutor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    ViewFlipper viewFlipper;
    private String JSON_STRING;
    private Button btn_submit,Next,Previous;
    private Spinner edt_st_gender,edt_st_class,edt_category;
    private EditText  user_id1,edt_st_name,edt_st_fname,edt_st_subj,edt_st_cell,edt_st_age,edt_st_email, edt_st_address, edt_st_school,edt_st_timing,edt_st_comment ;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettutor);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        this.setTitle("GetTutor");
        //fliper
        Next = (Button) findViewById(R.id.Next);
        Previous = (Button) findViewById(R.id.Previous);
        Previous.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    viewFlipper.showPrevious();
            }
        });

        edt_st_name = (EditText)findViewById(R.id.edt_student_name);
        //edt_st_name.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_fname = (EditText)findViewById(R.id.edt_student_father_name);
        //edt_st_fname.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_cell = (EditText)findViewById(R.id.edt_student_mobile_no);
        //edt_st_cell.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_age = (EditText)findViewById(R.id.edt_student_age);
       // edt_st_age.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_email = (EditText)findViewById(R.id.edt_student_email);
       // edt_st_email.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_address = (EditText)findViewById(R.id.edt_student_address);
       // edt_st_address.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_gender = (Spinner) findViewById(R.id.student_gender);
        edt_st_school = (EditText)findViewById(R.id.edt_student_schoolname);
        //edt_st_school.setTextColor(Color.parseColor("#8b8a8a"));

        edt_st_class = (Spinner)findViewById(R.id.edt_student_class);
        edt_category = (Spinner)findViewById(R.id.edt_category);
        edt_category.setOnItemSelectedListener(this);

        edt_st_subj = (EditText)findViewById(R.id.edt_student_major_subject);
       // edt_st_subj.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_timing = (EditText)findViewById(R.id.edt_student_tution_timing);
       // edt_st_timing.setTextColor(Color.parseColor("#8b8a8a"));
        edt_st_comment = (EditText)findViewById(R.id.edt_student_comment);
       // edt_st_comment.setTextColor(Color.parseColor("#8b8a8a"));



        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        Next.setOnClickListener(this);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.edt_student_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
         awesomeValidation.addValidation(this, R.id.edt_student_mobile_no, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.edt_student_age, Range.closed(13, 60), R.string.ageerror);




//        for spinner Qualification

        edt_st_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                TextView tmpView = (TextView) edt_st_gender.getSelectedView().findViewById(android.R.id.text1);
                tmpView.setTextColor(Color.BLACK);
                // TODO Auto-generated method stub

                //   Toast.makeText(getBaseContext(), ET_SITE_TYPE.getSelectedItem().toString(),
                //      Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


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
                        Toast.makeText(GetTutor.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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

        edt_st_name.setText(user_name);
        edt_st_email.setText(user_id);
        edt_st_cell.setText(user_mbl_no);
    }

    //to send data into database

    private void Register() {
        final String st_name = edt_st_name.getText().toString().trim();
        final String st_fname = edt_st_fname.getText().toString().trim();
        final String st_gender =  edt_st_gender.getSelectedItem().toString().trim();
        final String st_age =  edt_st_age.getText().toString().trim();
        final String st_phoneno = edt_st_cell.getText().toString().trim();
        final String st_email =  edt_st_email.getText().toString().trim();
        final String st_address =  edt_st_address.getText().toString().trim();
        final String st_school =  edt_st_school.getText().toString().trim();
        final String st_timing =  edt_st_timing.getText().toString().trim();
        final String st_class = edt_st_class.getSelectedItem().toString().trim();
        final String st_subject = edt_st_subj.getText().toString().trim();
        final String st_comment = edt_st_comment.getText().toString().trim();
        final String st_category = edt_category.getSelectedItem().toString().trim();




        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GetTutor.this, "Requesting...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Request Sent Successfully")){
                    Toast.makeText(GetTutor.this, "Request Sent Successfully! We will contact you soon. Thankyou!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GetTutor.this,Navigation.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(GetTutor.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_STUDENT_NAME,st_name);
                hashMap.put(Config.KEY_STUDENT_FNAME,st_fname);
                hashMap.put(Config.KEY_STUDENT_GENDER,st_gender);
                hashMap.put(Config.KEY_STUDENT_AGE,st_age);
                hashMap.put(Config.KEY_STUDENT_PHONENO,st_phoneno);
                hashMap.put(Config.KEY_STUDENT_EMAIL,st_email);
                hashMap.put(Config.KEY_STUDENT_ADDRESS,st_address);
                hashMap.put(Config.KEY_STUDENT_SCHOOL,st_school);
                hashMap.put(Config.KEY_STUDENT_TIMING,st_timing);
                hashMap.put(Config.KEY_STUDENT_CLASS,st_class);
                hashMap.put(Config.KEY_STUDENT_SUBJECTS,st_subject);
                hashMap.put(Config.KEY_STUDENT_COMMENT,st_comment);
                hashMap.put(Config.KEY_STUDENT_CATEGORY,st_category);


                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.GET_TUTOR, hashMap);
                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }


    @Override
    public void onClick(View view) {

        /**
         //         * Validation
         //
         //         */
        boolean invalid = false;

        if (view == Next) {

viewFlipper.showNext();
//
        }
        if (view == btn_submit) {
            if (edt_st_name.getText().length()==0) {
                invalid = true;
                edt_st_name.requestFocus();
                edt_st_name.setError("Please Enter Name");
            }
            if (edt_st_fname.getText().length()==0) {
                invalid = true;
                edt_st_name.requestFocus();
            edt_st_fname.setError("Please Enter Father Name");
             }
            if (edt_st_age.getText().length()==0) {
                invalid = true;
                edt_st_age.requestFocus();
            edt_st_age.setError("Please Enter Age");
            }
            if (edt_st_cell.getText().length()==0) {
                invalid = true;
                edt_st_cell.requestFocus();
            edt_st_cell.setError("Please Enter Mobile No");
            }
            if (edt_st_cell.getText().length() < 11) {
                invalid = true;
                edt_st_cell.requestFocus();
                edt_st_cell.setError("Please Enter Valid Mobile No");
            }
            if (edt_st_cell.getText().length() > 11) {
                invalid = true;
                edt_st_cell.requestFocus();
                edt_st_cell.setError("Please Enter Valid Mobile No");
            }

            if (edt_st_email.getText().length()==0) {
                invalid = true;
                edt_st_email.requestFocus();
                edt_st_email.setError("Please Enter Email");
            }
            if (edt_st_address.getText().length()==0) {
                invalid = true;
                edt_st_address.requestFocus();
                edt_st_address.setError("Please Enter Address");
            }
            if (edt_st_school.getText().length()==0) {
                invalid = true;
                edt_st_school.requestFocus();
                edt_st_school.setError("Please Enter Your School");
            }
//            if (edt_st_subj.getText().length()==0) {
//                edt_st_subj.setError("Please Enter Subjects");
//            }
            if (edt_st_timing.getText().length()==0) {
                invalid = true;
                edt_st_timing.requestFocus();
                edt_st_timing.setError("Please Enter Timiing");
            }
            if (edt_st_name.getText().length() == 0 || edt_st_fname.getText().length() == 0 || edt_st_age.getText().length() == 0||edt_st_cell.getText().length() == 0||edt_st_email.getText().length() == 0 || edt_st_address.getText().length() == 0 ||edt_st_school.getText().length() == 0 || edt_st_timing.getText().length() == 0) {
                Toast.makeText(GetTutor.this, "PLEASE FIll ALL FIELD", Toast.LENGTH_LONG).show();
            }
               else
            {
                Register();
            }
        }

        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView tmpView = (TextView) edt_category.getSelectedView().findViewById(android.R.id.text1);
        tmpView.setTextColor(Color.BLACK);


        // TODO Auto-generated method stub
        String sp1= String.valueOf(edt_category.getSelectedItem());
        //Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("Class")) {
            List<String> list = new ArrayList<String>();
            list.add("Please Select Your Class");
            list.add("Pre School");
            list.add("Primary");
            list.add("Middle");
            list.add("Matric");
            list.add("Intermediate");
            list.add("O Level");
            list.add("A Level");
            list.add("Bachelor");
            list.add("Other");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            edt_st_class.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Skills")) {
            List<String> list = new ArrayList<String>();
            list.add("Please Select any Skill");
            list.add("English Language");
            list.add("Computer Programing");
            list.add("Web Programing");
            list.add("Graphics Designing");
            list.add("Judu Karate & Self Defence");
            list.add("Painting");
            list.add("Cloth Swing");
            list.add("Yoga");
            list.add("Coocking");
            list.add("Quran Reciting");
            list.add("Beutician Training");
            list.add("Confidence Building");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            edt_st_class.setAdapter(dataAdapter2);
        }
        if(sp1.contentEquals("Subjects")) {
            List<String> list = new ArrayList<String>();
            list.add("Please Select any Subject");
            list.add("English");
            list.add("Mathematics");
            list.add("Physics");
            list.add("Chemistry");
            list.add("Biology");
            list.add("Zoology");
            list.add("Botany");
            list.add("Computer Science");
            list.add("Pak Studies");
            list.add("Urdu");
            list.add("Islamiat");
            list.add("Sindhi");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            edt_st_class.setAdapter(dataAdapter2);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



