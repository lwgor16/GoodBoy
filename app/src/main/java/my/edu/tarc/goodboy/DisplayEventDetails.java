package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventDetails extends AppCompatActivity {
    private static RequestQueue queue;
    private ProgressDialog pDialog;
    List<Event> eaList;
    ListView listViewEvent;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String EVENT_URL = "https://khorwe.000webhostapp.com/select_event.php";
    Intent intent;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_details);

        pDialog = new ProgressDialog(this);

        eaList = new ArrayList<>();

        intent = getIntent();

        position = intent.getIntExtra("position",0);

        listViewEvent = (ListView) findViewById(R.id.listView);

        downloadEvent(this, EVENT_URL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void downloadEvent(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Syn with server...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            eaList.clear();
                            //for (int i = 0; i < response.length(); i++) {
                                JSONObject courseResponse = (JSONObject) response.get(position);
                                String id = courseResponse.getString("id");
                                String name = courseResponse.getString("name");
                                String desc = courseResponse.getString("desc");
                                String date_time = courseResponse.getString("date_time");
                                String location = courseResponse.getString("location");
                                String organizer = courseResponse.getString("organizer");
                                Event event = new Event(id, name, desc, date_time, location, organizer);
                                eaList.add(event);
                            //}
                            loadEvent();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext().getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext().getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadEvent() {
        final EventAdapter adapter = new EventAdapter(this, R.layout.fragment_event, eaList);
        listViewEvent.setAdapter(adapter);
        Toast.makeText(this.getApplicationContext(), "Count :" + eaList.size(), Toast.LENGTH_LONG).show();
    }
}
