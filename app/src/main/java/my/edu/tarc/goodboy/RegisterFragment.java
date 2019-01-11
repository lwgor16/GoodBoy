package my.edu.tarc.goodboy;

import android.content.Context;
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

public class RegisterFragment extends Fragment {

    EditText editTextRusername, editTextRpassword, editTextRconfirmpassword, editTextRrealname, editTextRaddress, editTextRcontactno, editTextRicno, editTextRage;
    RadioGroup radioGroupGender, radioGroupAccountType;
    RadioButton radioButtonMale, radioButtonFemale, radiobuttonPersonal, radioButtonOrganization;

    public RegisterFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextRusername = view.findViewById(R.id.editTextRusername);
        editTextRpassword =  view.findViewById(R.id.editTextRpassword);
        editTextRconfirmpassword = view.findViewById(R.id.editTextRconfirmpassword);
        editTextRrealname = view.findViewById(R.id.editTextRrealname);
        editTextRaddress = view.findViewById(R.id.editTextRaddress);
        editTextRcontactno = view.findViewById(R.id.editTextRcontactno);
        editTextRicno = view.findViewById(R.id.editTextRicno);
        editTextRage = view.findViewById(R.id.editTextRage);
        radioGroupGender = view.findViewById(R.id.radioGroupDogGender);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);
        radioGroupAccountType = view.findViewById(R.id.radioGroupAccountType);
        radiobuttonPersonal= view.findViewById(R.id.radioButtonPersonal);
        radioButtonOrganization = view.findViewById(R.id.radioButtonOrganization);

        Button buttonregister = view.findViewById(R.id.buttonRegister);

        buttonregister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (editTextRpassword.getText().toString().equals(editTextRconfirmpassword.getText().toString()))
                {
                    saveRecord(v);
                    //Toast.makeText(getContext(), "Successfully created", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Error, Password does not match", Toast.LENGTH_LONG).show();
                    reset(v);
                }

            }
        }
        );


        return view;
    }

    public void reset(View v)
    {
        editTextRusername.setText("");
        editTextRpassword.setText("");
        editTextRconfirmpassword.setText("");
        editTextRaddress.setText("");
        editTextRrealname.setText("");
        editTextRicno.setText("");
        editTextRcontactno.setText("");
        editTextRage.setText("");
        radioGroupGender.setSelected(false);
        radioButtonFemale.setSelected(false);
        radioButtonMale.setSelected(false);
        radioGroupAccountType.setSelected(false);
        radiobuttonPersonal.setSelected(false);
        radioButtonOrganization.setSelected(false);
    }

    public void saveRecord(View v)
    {
        User user = new User();

        user.setUserName(editTextRusername.getText().toString());
        user.setUserPassword(editTextRpassword.getText().toString());
        user.setUserRealName(editTextRrealname.getText().toString());
        user.setUserAddress(editTextRaddress.getText().toString());
        user.setUserContactNumber(editTextRcontactno.getText().toString());
        user.setUserAge(Integer.parseInt(editTextRage.getText().toString()));

        int gender;
        gender = radioGroupGender.getCheckedRadioButtonId();
        if(gender == R.id.radioButtonMale)
        {
            user.setUserGender("Male");
        }
        else
        {
            user.setUserGender("Female");
        }

        int accounttype;
        accounttype = radioGroupAccountType.getCheckedRadioButtonId();
        if(accounttype == R.id.radioButtonPersonal)
        {
            user.setUserAccountType(0);
        }
        else
        {
            user.setUserAccountType(1);
        }

        user.setUserICNumber(editTextRicno.getText().toString());

        try
        {
            //TODO: Please update the URL to point to your own server
            makeServiceCall(getContext(), "https://khorwe.000webhostapp.com/insert_user.php", user);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final User user )
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
                    params.put("username", user.getUserName());
                    params.put("password", user.getUserPassword());
                    params.put("realname", user.getUserRealName());
                    params.put("address", user.getUserAddress());
                    params.put("contactno", user.getUserContactNumber());
                    params.put("age", Integer.toString(user.getUserAge()));
                    params.put("gender", user.getUserGender());
                    params.put("icno", user.getUserICNumber());
                    params.put("accounttype",Integer.toString(user.getUserAccountType()));
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
