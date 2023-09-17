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
public class NewsFeed extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        listView = (ListView) findViewById(R.id.new_feed);
        this.setTitle("News Feed");
        listView.setOnItemClickListener(this);
        //To Get News From Database
        getNEWS();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_NEWS_FEED);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String date = jo.getString(Config.TAG_NEWS_DATE);
                String title = jo.getString(Config.TAG_NEWS_TITLE);
                String description = jo.getString(Config.TAG_NEWS_DESCRIPTION);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_NEWS_DATE,date);
                employees.put(Config.TAG_NEWS_TITLE,title);
                employees.put(Config.TAG_NEWS_DESCRIPTION,description);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                NewsFeed.this, list, R.layout.list_news,
                new String[]{Config.TAG_NEWS_DATE,Config.TAG_NEWS_TITLE,Config.TAG_NEWS_DESCRIPTION},
                new int[]{R.id.news_date, R.id.news_title, R.id.news_description});

        listView.setAdapter(adapter);
    }

    private void getNEWS(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewsFeed.this,"Fetching News","Please Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_NEWS_FEED);
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
