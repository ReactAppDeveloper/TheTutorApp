package expence.ubiad.example.com.thetutor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ubiad on 12/06/2017.
 */
public class Profile extends Activity implements View.OnClickListener{

    private String JSON_STRING;
    private ProgressDialog loading;
    private Button send,btnCamera;
    private EditText name,password,number,user_id1;

//    for image waqas
//    ImageView capturedImage;
    CircleImageView capturedImage;
    Bitmap FixBitmap1;
    ByteArrayOutputStream byteArrayOutputStream1 ;
    byte[] byteArray ;
    String ConvertImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        name = (EditText)findViewById(R.id.edt_name);
       // name.setTextColor(Color.parseColor("#8b8a8a"));
        password = (EditText)findViewById(R.id.user_pass);
       // password.setTextColor(Color.parseColor("#8b8a8a"));
        number = (EditText)findViewById(R.id.edt_mobile_no);
       // number.setTextColor(Color.parseColor("#8b8a8a"));
        capturedImage = (CircleImageView) findViewById(R.id.profile_image);


        send=(Button)findViewById(R.id.btn_send);
        send.setOnClickListener(this);


//Initializing textview
        user_id1 = (EditText)findViewById(R.id.user_id);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String emp_id = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        user_id1.setText(emp_id);
//.............

        getJSON();

//        //image
        btnCamera = (Button) findViewById(R.id.btnCamera);

        byteArrayOutputStream1 = new ByteArrayOutputStream();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });
    }
    //image on image view
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                FixBitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                capturedImage.setImageBitmap(FixBitmap1);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    private void getJSON() {
        String id = user_id1.getText().toString().trim();
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.URL_GET_USER_PROFILE+user_id1.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showprofile(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showprofile(String response){
        String user_name="";
        String user_pass="";
        String user_mbl_no = "";
        String capturedImage1 = "";



        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.T_JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            user_name = collegeData.getString(Config.TAG_USER_NAME);
            user_pass = collegeData.getString(Config.TAG_USER_PASS);
            user_mbl_no = collegeData.getString(Config.TAG_USER_NUMBER);
            capturedImage1 = collegeData.getString(Config.TAG_USER_IMAGE);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        name.setText(user_name);
        password.setText(user_pass);
        number.setText(user_mbl_no);
        Picasso.with(this)
                .load("http://www.thetutor.pk/thetutor/profileimages/"+capturedImage1)
                .placeholder(R.drawable.pic)   // optional
                .error(R.drawable.stdcircle) // optional
                .resize(400,400)                // optional
                .into(capturedImage);



    }

    private void updateuser() {
//waqas image
//        FixBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
//        byteArray = byteArrayOutputStream1.toByteArray();
//        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        final String user_id = user_id1.getText().toString().trim();
        final String user_name = name.getText().toString().trim();
        final String user_pass = password.getText().toString().trim();
        final String user_mbl_no = number.getText().toString().trim();



        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile.this, "User Updating...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("User Updated Successfully")){
                    Toast.makeText(Profile.this, "User Updated Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Profile.this,Navigation.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Profile.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.U_USER_ID, user_id);
                hashMap.put(Config.U_USER_NAME, user_name);
                hashMap.put(Config.U_USER_PASS, user_pass);
                hashMap.put(Config.U_USER_NUMBER, user_mbl_no);
             //  hashMap.put(Config.U_USER_IMAGE, ConvertImage);





                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_USER, hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    @Override
    public void onClick(View v) {

        //         * Validation
        //
        //         */
        boolean invalid = false;
        if (name.equals("")) {
            invalid = true;
            name.requestFocus();
            name.setError("USER ID CANNOT BE EMPTY");

        }
        if (number.equals("")) {
            invalid = true;
            number.requestFocus();
            number.setError("USER NUMBER CANNOT BE EMPTY");

        }
        if (number.getText().length() < 11) {
            number.setError("Please Enter Valid Mobile No");
        }
        if (number.getText().length() > 11) {
            number.setError("Please Enter Valid Mobile No");
        }
        if (password.equals("")) {
            invalid = true;
            password.requestFocus();
            password.setError("USER PASSWORD CANNOT BE EMPTY");

        }


        else if(v == send){
            updateuser();
//            finish();
        }
    }


}