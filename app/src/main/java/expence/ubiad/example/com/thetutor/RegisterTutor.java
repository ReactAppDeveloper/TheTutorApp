package expence.ubiad.example.com.thetutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ubiad on 07/06/2017.
 */
public class RegisterTutor extends AppCompatActivity implements View.OnClickListener{

    ViewFlipper viewFlipper;
    private Button btn_submit,Next,Next1, Previous,Previous1,GetImageFromGalleryButton;
    private Spinner edt_gender,edt_education,teaching_exp;
    private CheckBox pre_school,cls_1to5,cls_6to8,cls_matric,cls_inter, cls_o_levl,a_lvl,bechlor,masters,test_prep,diploma,home_tutor,coaching,online;
    private EditText user_id1,edt_firstname,edt_fathername, edt_dob, edt_mobileno, edt_cnic, edt_address,edt_prg_study, edt_email,teaching_subj,teaching_timings;
    ImageView ShowSelectedImage;
    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream ;
    byte[] byteArray ;
    String ConvertImage ;
    //upload cv
    Button UploadButton;
//    Uri uri;
//    public static final String PDF_UPLOAD_HTTP_URL = "http://www.thetutor.pk/thetutor/upload.php";
//    String PdfNameHolder, PdfPathHolder, PdfID;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        AllowRunTimePermission();
        this.setTitle("Register Tutor");
        //uper layer-------
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        Next1 = (Button) findViewById(R.id.Next1);
        Next = (Button) findViewById(R.id.Next);
        Previous = (Button) findViewById(R.id.Previous);
        Previous1= (Button) findViewById(R.id.Previous1);
//        SelectButton = (Button) findViewById(R.id.btn_upload_cv);
        UploadButton = (Button) findViewById(R.id.btn_submit);



        //Initializing textview
        user_id1 = (EditText)findViewById(R.id.user_id);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String emp_id = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        user_id1.setText(emp_id);
//.............
//to call function
        GETUSERINFO();

        Next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (edt_fathername.getText().length() == 0) {
                    edt_fathername.setError("Please Enter Father Name");
                }
                else if (edt_dob.getText().length() == 0) {
                    edt_dob.setError("Please Enter Date Of Birth");
                }
                else if (edt_email.getText().length() == 0) {
                    edt_email.setError("Please Enter Email address");
                }
                else if (edt_cnic.getText().length() == 0) {
                    edt_cnic.setError("Please Enter Valid CNIC ");
                }
                else if (edt_cnic.getText().length() > 13) {
                    edt_cnic.setError("Please Enter Valid CNIC ");
                }
                else if (edt_cnic.getText().length() < 13) {
                    edt_cnic.setError("Please Enter Valid CNIC ");
                }
                else if (edt_address.getText().length() == 0) {
                    edt_address.setError("Please Enter Address");
                }
                else if(edt_gender.getSelectedItem().toString().trim().equals("Please Select Your Gender"))
                {
                    Toast.makeText(RegisterTutor.this,"Please Select Your Gender",Toast.LENGTH_LONG).show();
                }
                else if (FixBitmap == null) {
                    Toast.makeText(RegisterTutor.this,"Please Insert An Image",Toast.LENGTH_LONG).show();
                }
                else {
                    viewFlipper.showNext();
                }
            }

        });
        Previous.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                    viewFlipper.showPrevious();
            }
        });
Next1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(edt_education.getSelectedItem().toString().trim().equals("Please Select Your Qualification"))
        {
            Toast.makeText(RegisterTutor.this,"Please Select Qualification",Toast.LENGTH_LONG).show();
        }
       else if (edt_prg_study.getText().length() == 0) {
            edt_prg_study.setError("Please Enter Program of Study");
        }
       else if(teaching_exp.getSelectedItem().toString().trim().equals("Please Select Your Experience"))
        {
            Toast.makeText(RegisterTutor.this,"Please Select Your Experience",Toast.LENGTH_LONG).show();
        }

        else if (teaching_subj.getText().length()== 0) {
            teaching_subj.setError("Please Enter Subjects");
        }
        else {
            viewFlipper.showNext();
        }
    }
//if(SelectButton.getText().toString().equals("PDF is Selected"))
//{
//    viewFlipper.showNext();
//}else {
//   Toast.makeText(RegisterTutor.this,"Please Select CV",Toast.LENGTH_LONG).show();
//}
//    }
});
Previous1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        viewFlipper.showPrevious();
    }
});
    //---------------
        edt_firstname = (EditText)findViewById(R.id.edt_first_name);
       // edt_firstname.setTextColor(Color.parseColor("#8b8a8a"));
        edt_fathername = (EditText)findViewById(R.id.edt_father_name);
       // edt_fathername.setTextColor(Color.parseColor("#8b8a8a"));
        edt_dob = (EditText)findViewById(R.id.edt_dob);
       // edt_dob.setTextColor(Color.parseColor("#8b8a8a"));
        edt_mobileno = (EditText)findViewById(R.id.edt_mobile_no);
       // edt_mobileno.setTextColor(Color.parseColor("#8b8a8a"));
        edt_email = (EditText)findViewById(R.id.edt_email);
       // edt_email.setTextColor(Color.parseColor("#8b8a8a"));
        edt_cnic = (EditText)findViewById(R.id.edt_cnic);
       // edt_cnic.setTextColor(Color.parseColor("#8b8a8a"));
        edt_address = (EditText)findViewById(R.id.edt_address);
       // edt_address.setTextColor(Color.parseColor("#8b8a8a"));
        edt_gender = (Spinner)findViewById(R.id.edt_gender);

        GetImageFromGalleryButton = (Button) findViewById(R.id.selectimage);
        ShowSelectedImage = (ImageView) findViewById(R.id.register_image);
        byteArrayOutputStream = new ByteArrayOutputStream();
        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 0);

            }
        });
        //2nd flip
        edt_education = (Spinner)findViewById(R.id.tu_qualification);
        edt_prg_study = (EditText)findViewById(R.id.edt_prg_study);
       // edt_prg_study.setTextColor(Color.parseColor("#8b8a8a"));
        teaching_exp = (Spinner)findViewById(R.id.teaching_exp);
        teaching_subj = (EditText) findViewById(R.id.teaching_subj);
      //  teaching_subj.setTextColor(Color.parseColor("#8b8a8a"));
//        3rd flip
        teaching_timings = (EditText)findViewById(R.id.teaching_timings);
       // teaching_timings.setTextColor(Color.parseColor("#8b8a8a"));
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.edt_first_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.edt_father_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.edt_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
       // awesomeValidation.addValidation(this, R.id.edt_mobile_no, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.edt_dob,
                "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|" +
                        "(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))" +
                        "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?" +
                        "(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|" +
                        "1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
      //  awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);
        // check boxes
        pre_school = (CheckBox)findViewById(R.id.pre_school);
       // pre_school.setTextColor(Color.parseColor("#8b8a8a"));
        pre_school.setOnClickListener(this);
        cls_1to5 = (CheckBox)findViewById(R.id.cls_1to5);
        cls_1to5.setOnClickListener(this);
        cls_6to8 = (CheckBox)findViewById(R.id.cls_6to8);
        cls_6to8.setOnClickListener(this);
        cls_matric = (CheckBox)findViewById(R.id.cls_matric);
        cls_matric.setOnClickListener(this);
        cls_inter = (CheckBox)findViewById(R.id.cls_inter);
        cls_inter.setOnClickListener(this);
        cls_o_levl = (CheckBox)findViewById(R.id.cls_o_levl);
        cls_o_levl.setOnClickListener(this);
        a_lvl = (CheckBox)findViewById(R.id.a_lvl);
        a_lvl.setOnClickListener(this);
        bechlor = (CheckBox)findViewById(R.id.bechlor);
        bechlor.setOnClickListener(this);
        masters = (CheckBox)findViewById(R.id.masters);
        masters.setOnClickListener(this);
        test_prep = (CheckBox)findViewById(R.id.test_prep);
        test_prep.setOnClickListener(this);
        diploma = (CheckBox)findViewById(R.id.diploma);
        diploma.setOnClickListener(this);
        home_tutor = (CheckBox)findViewById(R.id.home_tutor);
        home_tutor.setOnClickListener(this);
        coaching = (CheckBox)findViewById(R.id.coaching);
        coaching.setOnClickListener(this);
        online = (CheckBox)findViewById(R.id.online);
        online.setOnClickListener(this);

        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
//cv
//        SelectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // PDF selection code start from here .
//
//                Intent intent = new Intent();
//
//                intent.setType("application/pdf");
//
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1);
//
//            }
//        });
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);
        switch(RC){
            case 0: // Do your stuff here...
                if (RC == 0 && RQC == RESULT_OK && I != null && I.getData() != null) {
                    Uri uri = I.getData();
                    try {
                        FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ShowSelectedImage.setImageBitmap(FixBitmap);


                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
                break;
//            case 1: // Do your other stuff here...
//                if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
//                    uri = I.getData();
//                    SelectButton.setText("PDF is Selected");
//                    }
//                break;

        }


    }
    //cv
//    public void PdfUploadFunction() {
//        PdfNameHolder =edt_email.getText().toString().trim();
//        PdfPathHolder = FilePath.getPath(this, uri);
//
//            try {
//                PdfID = UUID.randomUUID().toString();
//                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
//                        .addFileToUpload(PdfPathHolder, "pdf")
//                        .addParameter("name", PdfNameHolder)
//                        .setNotificationConfig(new UploadNotificationConfig())
//                        .setMaxRetries(5)
//                        .startUpload();
//
//            } catch (Exception exception) {
//
//                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//    }
//    public void AllowRunTimePermission(){
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterTutor.this, Manifest.permission.READ_EXTERNAL_STORAGE))
//        {
//
//            Toast.makeText(RegisterTutor.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();
//
//        } else {
//
//            ActivityCompat.requestPermissions(RegisterTutor.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {
//
//        switch (RC) {
//
//            case 1:
//
//                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
//
////                    Toast.makeText(RegisterTutor.this,"Permission Granted", Toast.LENGTH_LONG).show();
//
//                } else {
//
////                    Toast.makeText(RegisterTutor.this,"Permission Canceled", Toast.LENGTH_LONG).show();
//
//                }
//                break;
//        }
//    }



    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
          //  Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();

            //process the data further
            Register();
        }
    }

    //to get user info
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
                        Toast.makeText(RegisterTutor.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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

        edt_firstname.setText(user_name);
        edt_email.setText(user_id);
        edt_mobileno.setText(user_mbl_no);
    }



    private void Register() {
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        final String firstname = edt_firstname.getText().toString().trim();

        final String fathername =  edt_fathername.getText().toString().trim();
        final String dob =  edt_dob.getText().toString().trim();
        final String mobileno = edt_mobileno.getText().toString().trim();
        final String email =  edt_email.getText().toString().trim();
        final String cnic =  edt_cnic.getText().toString().trim();
        final String address =  edt_address.getText().toString().trim();
        final String gender =  edt_gender.getSelectedItem().toString().trim();
        final String education = edt_education.getSelectedItem().toString().trim();
        final String tu_study_program = edt_prg_study.getText().toString().trim();
        final String tu_experience = teaching_exp.getSelectedItem().toString().trim();
        final String tu_teaching_subj =  teaching_subj.getText().toString().trim();
        final String timingteach =  teaching_timings.getText().toString().trim();
        final String tu_stdy_lvl_can_teach =  pre_school.getText().toString().trim();
        final String tu_stdy_lvl_can_teach1 =  cls_1to5.getText().toString().trim();
        final String tu_stdy_lvl_can_teach2 =  cls_6to8.getText().toString().trim();
        final String tu_stdy_lvl_can_teach3 =  cls_matric.getText().toString().trim();
        final String tu_stdy_lvl_can_teach4 =  cls_inter.getText().toString().trim();
        final String tu_stdy_lvl_can_teach5 =  cls_o_levl.getText().toString().trim();
        final String tu_stdy_lvl_can_teach6 =  a_lvl.getText().toString().trim();
        final String tu_stdy_lvl_can_teach7 =  bechlor.getText().toString().trim();
        final String tu_stdy_lvl_can_teach8 =  masters.getText().toString().trim();
        final String tu_stdy_lvl_can_teach9 =  test_prep.getText().toString().trim();
        final String tu_stdy_lvl_can_teach10 =  diploma.getText().toString().trim();
        final String tu_tutoring_opt =  home_tutor.getText().toString().trim();




        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterTutor.this, "REGISTERING...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Mail sent successfully")){
                    Toast.makeText(RegisterTutor.this, "Request Sent Successfully! We will contact you soon. Thankyou!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterTutor.this,Navigation.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegisterTutor.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_TU_FIRSTNAME,firstname);
                hashMap.put(Config.KEY_TU_FATHERNAME,fathername);
                hashMap.put(Config.KEY_TU_GENDER,gender);
                hashMap.put(Config.KEY_TU_DOB,dob);
                hashMap.put(Config.KEY_TU_MOBILENO,mobileno);
                hashMap.put(Config.KEY_TU_CNIC,cnic);
                hashMap.put(Config.KEY_TU_ADDRESSS,address);
                hashMap.put(Config.KEY_TU_EMAIL,email);
                hashMap.put(Config.KEY_TU_IMAGE, ConvertImage);
                hashMap.put(Config.KEY_TU_EDUCATION,education);
                hashMap.put(Config.KEY_TU_STUDYPROGRAM,tu_study_program);
                hashMap.put(Config.KEY_TU_EXPERIENCE,tu_experience);
                hashMap.put(Config.KEY_TU_TEACHINGSUB,tu_teaching_subj);
                hashMap.put(Config.KEY_TU_TIMINGTEACH,timingteach);
                hashMap.put(Config.KEY_TU_STUDYLEVEL,tu_stdy_lvl_can_teach);
                hashMap.put(Config.KEY_TU_STUDYLEVEL1,tu_stdy_lvl_can_teach1);
                hashMap.put(Config.KEY_TU_STUDYLEVEL2,tu_stdy_lvl_can_teach2);
                hashMap.put(Config.KEY_TU_STUDYLEVEL3,tu_stdy_lvl_can_teach3);
                hashMap.put(Config.KEY_TU_STUDYLEVEL4,tu_stdy_lvl_can_teach4);
                hashMap.put(Config.KEY_TU_STUDYLEVEL5,tu_stdy_lvl_can_teach5);
                hashMap.put(Config.KEY_TU_STUDYLEVEL6,tu_stdy_lvl_can_teach6);
                hashMap.put(Config.KEY_TU_STUDYLEVEL7,tu_stdy_lvl_can_teach7);
                hashMap.put(Config.KEY_TU_STUDYLEVEL8,tu_stdy_lvl_can_teach8);
                hashMap.put(Config.KEY_TU_STUDYLEVEL9,tu_stdy_lvl_can_teach9);
                hashMap.put(Config.KEY_TU_STUDYLEVEL10,tu_stdy_lvl_can_teach10);
                hashMap.put(Config.KEY_TU_TUTORING,tu_tutoring_opt);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.REGISTER_TUTOR, hashMap);
                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    @Override
    public void onClick(View view) {
//        if (view == Next) {
//            if (edt_firstname.getText().length() == 0) {
//                edt_firstname.setError("Please Enter Name");
//            }
//            if (edt_fathername.getText().length() == 0) {
//                edt_fathername.setError("Please Enter Father Name");
//            }
//            if (edt_dob.getText().length() == 0) {
//                edt_dob.setError("Please Enter Age");
//            }
//            if (edt_mobileno.getText().length() == 0) {
//                edt_mobileno.setError("Please Enter Mobile No");
//            }
//            if (edt_email.getText().length() == 0) {
//                edt_email.setError("Please Enter Email");
//            }
//            if (edt_address.getText().length() == 0) {
//                edt_address.setError("Please Enter Address");
//            }
//            if (edt_cnic.getText().length() == 0) {
//                edt_cnic.setError("Please Enter CNIC");
//            }
////            if (edt_prg_study.getText().length() == 0) {
////                edt_prg_study.setError("Please Enter Program of Study");
////            }
////            if (teaching_subj.getText().length() == 0) {
////                teaching_subj.setError("Please Enter Teaching Subjects");
////            }
//
//        }
//        else {
//            viewFlipper.showNext();
//        }
        if (view == btn_submit) {
            if(edt_education.getSelectedItem().toString().trim().equals("Please Select Your Qualification"))
            {
                Toast.makeText(RegisterTutor.this,"Please Select Qualification",Toast.LENGTH_LONG).show();
            }
            if (edt_prg_study.getText().length() == 0) {
                edt_prg_study.setError("Please Enter Program of Study");
            }
            else if(teaching_exp.getSelectedItem().toString().trim().equals("Please Select Your Experience"))
            {
                Toast.makeText(RegisterTutor.this,"Please Select Your Experience",Toast.LENGTH_LONG).show();
            }

            else if (teaching_subj.getText().length()== 0) {
                teaching_subj.setError("Please Enter Subjects");
            }
            else if (teaching_timings.getText().length() == 0) {
                teaching_timings.setError("Please Enter Timing");
            }
            else {
                submitForm();
//                PdfUploadFunction();
            }
        }
    }
}