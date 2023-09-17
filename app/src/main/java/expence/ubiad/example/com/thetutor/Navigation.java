package expence.ubiad.example.com.thetutor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static expence.ubiad.example.com.thetutor.R.id.btn_classes;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private TextView name1,username;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//for wifi check
        if(!isNetworkAvailable()){
            //Create an alertdialog
            AlertDialog.Builder Checkbuilder=new  AlertDialog.Builder(Navigation.this);
            Checkbuilder.setIcon(R.drawable.error);
            Checkbuilder.setCancelable(false);
            Checkbuilder.setTitle("Error!");
            Checkbuilder.setMessage("Check Your Internet Connection.");
            //Builder Retry Button
            Checkbuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Restart The Activity
                    finish();
                }
            });
            Checkbuilder.setNegativeButton("Connect", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });

            AlertDialog alert=Checkbuilder.create();
            alert.show();

        }
        else {
            if (isNetworkAvailable()){

                Thread tr=new Thread(){
                    public  void  run(){
                        try {
                            sleep(4500);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {

                        }
                    }
                };
                tr.start();

            }
        }


        Button bt = (Button)findViewById(btn_classes);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this,Classes.class);
                startActivity(intent);
            }
        });
        Button bt1 = (Button)findViewById(R.id.btn_subject);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this,Subjects.class);
                startActivity(intent);
            }
        });
        Button bt2 = (Button)findViewById(R.id.btn_skills);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this,Skills.class);
                startActivity(intent);
            }
        });
        Button bt3 = (Button)findViewById(R.id.btn_ourtutor);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this,OurTutor.class);
                startActivity(intent);
            }
        });
        Button bt4 = (Button)findViewById(R.id.btn_notification);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this,NewsFeed.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // ......To get name of current user
        View hView =  navigationView.getHeaderView(0);
//Initializing textview
        name1 = (TextView)hView.findViewById(R.id.name1);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String emp_id = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview
        name1.setText(emp_id);

        //for load image
//


//.............
        navigationView.setNavigationItemSelectedListener(this);

//        getName();

    }
    //for chk network
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null;
    }


    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

//                        Starting login activity
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.profile) {
            Intent intent = new Intent(Navigation.this,Profile.class);
            startActivity(intent);

        } else if (id == R.id.get_tutor) {
            Intent intent = new Intent(Navigation.this,GetTutor.class);
            startActivity(intent);
        } else if (id == R.id.register_tutor) {
            Intent intent = new Intent(Navigation.this, RegisterTutor.class);
            startActivity(intent);
        }
            else if (id == R.id.contact) {
            Intent intent = new Intent(Navigation.this, ContactUs.class);
            startActivity(intent);
        }
//             else if (id == R.id.share) {
//            try {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_SUBJECT, "The Tutor");
//                String sAux = "\nLet me recommend you this application\n\n";
//              //  sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
//                i.putExtra(Intent.EXTRA_TEXT, sAux);
//                startActivity(Intent.createChooser(i, "choose one"));
//            } catch(Exception e) {
//                //e.toString();
//            }
        //}
        else if (id == R.id.logout) {
            logout();

        } else if (id == R.id.exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Navigation.this);
            builder.setMessage("Do You Want To Exit An App??");
            builder.setCancelable(true);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

////    to get name
//private void getName() {
//    String id = name1.getText().toString().trim();
//    String url = Config.URL_GET_USER_PROFILE+name1.getText().toString().trim();
//
//
//    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            showprofile(response);
//        }
//    },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(Navigation.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
//            });
//
//    RequestQueue requestQueue = Volley.newRequestQueue(this);
//    requestQueue.add(stringRequest);
//}
//    private void showprofile(String response){
//        String user_name="";
//
//
//
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray(Config.T_JSON_ARRAY);
//            JSONObject collegeData = result.getJSONObject(0);
//            user_name = collegeData.getString(Config.TAG_USER_NAME);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        username.setText(user_name);
//
//
//    }


    @Override
    public void onClick(View view) {


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        finishAffinity();
        System.exit(0);
    }
}
