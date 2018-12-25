package my.edu.tarc.goodboy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private static RequestQueue queue;
    private ProgressDialog pDialog;
    List<Event> eaList;
    ListView listViewEvent;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String EVENT_URL = "https://khorwe.000webhostapp.com/select_event.php";

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        pDialog = new ProgressDialog(getActivity());

        eaList = new ArrayList<>();

        listViewEvent = (ListView) view.findViewById(R.id.listViewEvent);

        downloadEvent(getContext(), EVENT_URL);

        return view;
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
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject courseResponse = (JSONObject) response.get(i);
                                String id = courseResponse.getString("id");
                                String name = courseResponse.getString("name");
                                String desc = courseResponse.getString("desc");
                                String date_time = courseResponse.getString("date_time");
                                String location = courseResponse.getString("location");
                                String organizer = courseResponse.getString("organizer");
                                Event event = new Event(id, name, desc, date_time, location, organizer);
                                eaList.add(event);
                            }
                            loadEvent();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
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
        final EventAdapter adapter = new EventAdapter(getActivity(), R.layout.fragment_event, eaList);
        listViewEvent.setAdapter(adapter);
        Toast.makeText(getActivity().getApplicationContext(), "Count :" + eaList.size(), Toast.LENGTH_LONG).show();
    }
}