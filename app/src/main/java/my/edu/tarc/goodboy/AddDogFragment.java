package my.edu.tarc.goodboy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddDogFragment extends Fragment
{
    EditText editTextDogBreed, editTextDogAge, editTextDogColor, editTextDogOrganization;
    RadioGroup radioGroupDogGender, radioGroupDogSize, radioGroupDogCondition;
    RadioButton radioButtonDogMale, radioButtonDogFemale, radiobuttonDogSmall, radioButtonDogMedium, radioButtonDogLarge, radioButtonDogBad, radioButtonDogAverage, radioButtonDogGood;


    public AddDogFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dog, container, false);

        editTextDogBreed = view.findViewById(R.id.editTextDogBreed);
        editTextDogAge =  view.findViewById(R.id.editTextDogAge);
        editTextDogColor = view.findViewById(R.id.editTextDogColor);
        editTextDogOrganization = view.findViewById(R.id.editTextDogOrganization);
        radioGroupDogGender = view.findViewById(R.id.radioGroupDogGender);
        radioGroupDogSize = view.findViewById(R.id.radioGroupDogSize);
        radioGroupDogCondition = view.findViewById(R.id.radioGroupDogCondition);
        radioButtonDogMale = view.findViewById(R.id.radioButtonDogMale);
        radioButtonDogFemale = view.findViewById(R.id.radioButtonDogFemale);
        radiobuttonDogSmall = view.findViewById(R.id.radioButtonDogSmall);
        radioButtonDogMedium = view.findViewById(R.id.radioButtonDogMedium);
        radioButtonDogLarge = view.findViewById(R.id.radioButtonDogLarge);
        radioButtonDogBad= view.findViewById(R.id.radioButtonDogBad);
        radioButtonDogAverage = view.findViewById(R.id.radioButtonDogAverage);
        radioButtonDogGood = view.findViewById(R.id.radioButtonDogGood);

        Button buttonregister = view.findViewById(R.id.buttonAddDog);

        buttonregister.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  saveRecord(v);
                                                  Toast.makeText(getContext(), "Successfully created", Toast.LENGTH_LONG).show();
                                              }
                                          }
        );
        return view;
    }

    public void reset(View v)
    {
        editTextDogBreed.setText("");
        editTextDogAge.setText("");
        editTextDogColor.setText("");
        editTextDogOrganization.setText("");
        radioGroupDogGender.setSelected(false);
        radioGroupDogSize.setSelected(false);
        radioGroupDogCondition.setSelected(false);
        radioButtonDogMale.setSelected(false);
        radioButtonDogFemale.setSelected(false);
        radiobuttonDogSmall.setSelected(false);
        radioButtonDogMedium.setSelected(false);
        radioButtonDogLarge.setSelected(false);
        radioButtonDogBad.setSelected(false);
        radioButtonDogAverage.setSelected(false);
        radioButtonDogGood.setSelected(false);
    }

    public void saveRecord(View v)
    {
        Dog dog = new Dog();

        dog.setDogBreed(editTextDogBreed.getText().toString());
        dog.setDogAge( Integer.parseInt(editTextDogAge.getText().toString()));
        dog.setDogColor(editTextDogColor.getText().toString());
        dog.setDogOrganization(editTextDogOrganization.getText().toString());

        int gender;
        gender = radioGroupDogGender.getCheckedRadioButtonId();
        if(gender == R.id.radioButtonDogMale)
        {
            dog.setDogGender("Male");
        }
        else
        {
            dog.setDogGender("Female");
        }

        int size;
        size = radioGroupDogSize.getCheckedRadioButtonId();
        if(size == R.id.radioButtonDogSmall)
        {
            dog.setDogSize("Small");
        }
        else if (size == R.id.radioButtonDogMedium)
        {
            dog.setDogSize("Medium");
        }
        else
            dog.setDogSize("Large");


        int condition;
        condition = radioGroupDogCondition.getCheckedRadioButtonId();
        if(condition == R.id.radioButtonDogBad)
        {
            dog.setDogCondition("Bad");
        }
        else if (size == R.id.radioButtonDogAverage)
        {
            dog.setDogCondition("Average");
        }
        else
            dog.setDogCondition("Good");

        try
        {
            //TODO: Please update the URL to point to your own server
            makeServiceCall(getContext(), "https://khorwe.000webhostapp.com/insert_dog.php", dog);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final Dog dog )
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
                    params.put("breed", dog.getDogBreed());
                    params.put("gender", dog.getDogGender());
                    params.put("color", dog.getDogColor());
                    params.put("size", dog.getDogSize());
                    params.put("condition", dog.getDogCondition());
                    params.put("organization", dog.getDogOrganization());
                    params.put("age", Integer.toString(dog.getDogAge()));
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
