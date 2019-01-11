package my.edu.tarc.goodboy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEventFragment extends Fragment {

    EditText editTextName, editTextDescription, editTextYear, editTextMonth, editTextDate,
    editTextMM, editTextHH, editTextLocation, editTextOrganizer;
    TextView textView2;
    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_event, container, false);

        editTextName = v.findViewById(R.id.editTextName);
        editTextDescription = v.findViewById(R.id.editTextDescription);
        editTextYear = v.findViewById(R.id.editTextYear);
        editTextMonth = v.findViewById(R.id.editTextMonth);
        editTextDate = v.findViewById(R.id.editTextDate);
        editTextMM = v.findViewById(R.id.editTextMM);
        editTextHH = v.findViewById(R.id.editTextHH);
        editTextLocation = v.findViewById(R.id.editTextLocation);
        editTextOrganizer = v.findViewById(R.id.editTextOrganizer);
        Button buttonAddEvent = v.findViewById(R.id.buttonAddEvent);
        textView2 = v.findViewById(R.id.textView2);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                saveRecord(v);

            }}
        );

        return v;
    }

    public void reset(View v)
    {
        editTextName.setText("");
        editTextDescription.setText("");
        editTextYear.setText("");
        editTextMonth.setText("");
        editTextDate.setText("");
        editTextMM.setText("");
        editTextLocation.setText("");
        editTextHH.setText("");
        editTextOrganizer.setText("");
    }

    public void saveRecord(View v)
    {
        Event event = new Event();

        String dateTime;

        dateTime = editTextYear.getText().toString() + "-" + editTextMonth.getText().toString()
        + "-" + editTextDate.getText().toString() + " " + editTextHH.getText().toString() + ":"
        + editTextMM.getText().toString() + ":00";

        event.setEventName(editTextName.getText().toString());
        event.setEventDesc(editTextDescription.getText().toString());
        event.setEventDateTime(dateTime);
        event.setEventLocation(editTextLocation.getText().toString());
        event.setEventOrganiser(editTextOrganizer.getText().toString());

        textView2.setText(dateTime);

        try
        {
            //TODO: Please update the URL to point to your own server
            makeServiceCall(getContext(), "https://khorwe.000webhostapp.com/insert_event.php", event);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final Event event )
    {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==0) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    //getActivity().finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", event.getEventName());
                    params.put("desc", event.getEventDesc());
                    params.put("date_time", event.getEventDateTime());
                    params.put("location", event.getEventLocation());
                    params.put("organizer", event.getEventOrganiser());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
