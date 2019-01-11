package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class OrganisationDetailsFragment extends Fragment {
    private static RequestQueue queue;
    private ProgressDialog pDialog;

    private int position;

    List<Organization> oaList;
    ListView listViewOrganization;

    private static final String TAG = "my.edu.tarc.lab44";
    private static String ORGANIZATION_URL = "https://khorwe.000webhostapp.com/select_organization.php";

    public OrganisationDetailsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organisation_details, container, false);

        pDialog = new ProgressDialog(getActivity());

        oaList = new ArrayList<>();

        SharedPreferences sharedPreferences;

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.organisationDetails), Context.MODE_PRIVATE);

        position = sharedPreferences.getInt("position", 0);

        listViewOrganization = (ListView) view.findViewById(R.id.listViewOrganization);

        downloadOrganization(getContext(), ORGANIZATION_URL);

        return view;
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
                            JSONObject courseResponse = (JSONObject) response.get(position);
                            String id = courseResponse.getString("id");
                            String name = courseResponse.getString("name");
                            String location = courseResponse.getString("location");
                            String owner = courseResponse.getString("owner");
                            String phone_no = courseResponse.getString("phone_no");
                            Organization organization = new Organization(id, name, location, owner, phone_no);
                            oaList.add(organization);

                            loadOrganization();

                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e)
                        {
                            Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
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
        final OrganisationDetailsAdapter adapter = new OrganisationDetailsAdapter(getActivity(), R.layout.fragment_organisation_details, oaList);
        listViewOrganization.setAdapter(adapter);
        Toast.makeText(getContext(), "Count :" + oaList.size(), Toast.LENGTH_LONG).show();
    }

}
