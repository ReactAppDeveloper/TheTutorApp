package expence.ubiad.example.com.thetutor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ubiad on 07/06/2017.
 */
public class OurTutor extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ourtutor);
        listView = (ListView) findViewById(R.id.listView);
        this.setTitle("Out Tutor");
        listView.setOnItemClickListener(this);
        getJSON();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String firstname = jo.getString(Config.TAG_TUTOR_NAME);
                String education = jo.getString(Config.TAG_TUTOR_EDUCATION);
                String tu_experience = jo.getString(Config.TAG_TUTOR_EXPERIENCE);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_TUTOR_NAME,firstname);
                employees.put(Config.TAG_TUTOR_EDUCATION,education);
                employees.put(Config.TAG_TUTOR_EXPERIENCE,tu_experience);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                OurTutor.this, list, R.layout.list_item,
                new String[]{Config.TAG_TUTOR_NAME,Config.TAG_TUTOR_EDUCATION,Config.TAG_TUTOR_EXPERIENCE},
                new int[]{R.id.profile_name, R.id.txt_qualification, R.id.txt_experience});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OurTutor.this,"Fetching Data","Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_TUTOR);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
