package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


/**
 * A simple {@link Fragment} subclass
 */
public class OrganizationFragment extends Fragment {

    private static RequestQueue queue;
    private ProgressDialog pDialog;
    List<Organization> oaList;
    ListView listViewOrganization;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String ORGANIZATION_URL = "https://khorwe.000webhostapp.com/select_organization.php";

    public OrganizationFragment() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        pDialog = new ProgressDialog(getActivity());

        oaList = new ArrayList<>();

        listViewOrganization = (ListView) view.findViewById(R.id.listViewOrganization);

        listViewOrganization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.organisationDetails),Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("position",position);

                editor.apply();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                OrganisationDetailsFragment importFragment = new OrganisationDetailsFragment();

                fragmentTransaction.replace(R.id.fragment_content,importFragment);

                fragmentTransaction.commit();

            }
        });

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
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject courseResponse = (JSONObject) response.get(i);
                                String name = courseResponse.getString("name");
                                Organization organization = new Organization(name);
                                oaList.add(organization);
                            }
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
        final OrganisationListAdapter adapter = new OrganisationListAdapter(getActivity(), R.layout.fragment_organization, oaList);
        listViewOrganization.setAdapter(adapter);
        Toast.makeText(getContext(), "Count :" + oaList.size(), Toast.LENGTH_LONG).show();
    }
}