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


public class DisplayOrganisationDetails extends AppCompatActivity {

    private static RequestQueue queue;
    private ProgressDialog pDialog;
    List<Organization> oaList;
    ListView listViewOrganization;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String ORGANIZATION_URL = "https://khorwe.000webhostapp.com/select_organization.php";
    int position = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_organisation_details);

        pDialog = new ProgressDialog(this);

        oaList = new ArrayList<>();

        intent = getIntent();

        position = intent.getIntExtra("position",0);

        listViewOrganization = (ListView) findViewById(R.id.listView);

        downloadOrganization(this, ORGANIZATION_URL);
    }

    private void downloadOrganization(Context context, String url) {
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
                        try
                        {
                            oaList.clear();
                            //for (int i = 0; i < response.length(); i++)
                            //{
                                JSONObject courseResponse = (JSONObject) response.get(position);
                                String id = courseResponse.getString("id");
                                String name = courseResponse.getString("name");
                                String location = courseResponse.getString("location");
                                String owner = courseResponse.getString("owner");
                                String phone_no = courseResponse.getString("phone_no");
                                Organization organization = new Organization(id, name, location, owner, phone_no);
                                oaList.add(organization);
                            //}
                            loadOrganization();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadOrganization()
    {
        final OrganizationAdapter adapter = new OrganizationAdapter(this, R.layout.fragment_organization, oaList);
        listViewOrganization.setAdapter(adapter);
        Toast.makeText(this, "Count :" + oaList.size(), Toast.LENGTH_LONG).show();
    }
}


