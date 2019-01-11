package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

public class DogDetailsAndAdoption extends Fragment {

    private static RequestQueue queue;
    private static RequestQueue queue2;
    private ProgressDialog pDialog;
    private static String USER_URL = "https://khorwe.000webhostapp.com/select_dog.php";
    private final String deleteURL = "http://khorwe.000webhostapp.com/delete_dog.php?id=";
    private static final String TAG = "my.edu.tarc.lab44";
    private List<Dog> daList;
    private TextView textViewBreed;
    private TextView textViewGender;
    private TextView textViewAge;
    private TextView textViewColor;
    private TextView textViewOrganization;
    private TextView textViewCondition;
    private TextView textViewSize;
    private Button buttonAdopt;
    private Button buttonCancel;
    String dogId = "";

    public DogDetailsAndAdoption() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dog_details_and_adoption, container, false);

        pDialog = new ProgressDialog(getActivity());

        daList = new ArrayList<>();

        textViewBreed = view.findViewById(R.id.textViewDogBreed);
        textViewGender = view.findViewById(R.id.textViewGender);
        textViewAge = view.findViewById(R.id.textViewAge);
        textViewColor = view.findViewById(R.id.textViewColor);
        textViewOrganization = view.findViewById(R.id.textViewOrganization);
        textViewCondition = view.findViewById(R.id.textViewCondition);
        textViewSize = view.findViewById(R.id.textViewSize);

        buttonAdopt = view.findViewById(R.id.buttonAdoptionAdopt);
        buttonCancel = view.findViewById(R.id.buttonAdoptionCancel);


        loadDogDetails();


        buttonAdopt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteDog();
                
                Toast.makeText(getActivity().getApplicationContext(), "Information sent to Organization! Please contact them for more details for the adoption.", Toast.LENGTH_LONG).show();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

                AdoptionFragment importFragment = new AdoptionFragment();

                fragmentTransaction.replace(R.id.fragment_content,importFragment);

                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void loadDogDetails()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
        dogId = sharedPreferences.getString("dogDetailsID", "");

        downloadAllDog(getContext(), USER_URL);
    }

    private void downloadAllDog(Context context, String url)
    {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            daList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject courseResponse = (JSONObject) response.get(i);
                                String id = courseResponse.getString("id");
                                String breed = courseResponse.getString("breed");
                                String color = courseResponse.getString("color");
                                String condition = courseResponse.getString("condition");
                                String organization = courseResponse.getString("organization");
                                String gender = courseResponse.getString("gender");
                                int age = courseResponse.getInt("age");
                                String size = courseResponse.getString("size");
                                Dog dog = new Dog(id, breed, color, condition, organization, gender, age, size);
                                daList.add(dog);
                            }

                            int dogIndex = -1;

                            for(int i = 0; i < daList.size(); i++)
                            {
                                if(daList.get(i).getDogId().equals(dogId))
                                {
                                    dogIndex = i;
                                }
                            }

                            textViewBreed.setText("Breed : " + daList.get(dogIndex).getDogBreed());
                            textViewGender.setText("Gender : " + daList.get(dogIndex).getDogGender());
                            textViewAge.setText("Age : " + String.valueOf(daList.get(dogIndex).getDogAge()));
                            textViewColor.setText("Color : " + daList.get(dogIndex).getDogColor());
                            textViewOrganization.setText("Organization : " + daList.get(dogIndex).getDogOrganization());
                            textViewCondition.setText("Condition : " + daList.get(dogIndex).getDogCondition());
                            textViewSize.setText("Size : " + daList.get(dogIndex).getDogSize());

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

    private void deleteDog() {
        try
        {
            //TODO: Please update the URL to point to your own server
            makeServiceCall(getContext(), deleteURL);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url)
    {
        //mPostCommentResponse.requestStarted();
        queue2 = Volley.newRequestQueue(context);

        String finalUrl = url + dogId;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Set the tag on the request.
        stringRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue2.add(stringRequest);


    }
}
