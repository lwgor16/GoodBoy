package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class LoginFragment extends Fragment {

    private static RequestQueue queue;
    private ProgressDialog pDialog;
    List<User> uaList;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String USER_URL = "https://khorwe.000webhostapp.com/select_user.php";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        pDialog = new ProgressDialog(getActivity());

        uaList = new ArrayList<>();

        downloadUser(getContext(), USER_URL);

        final EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        final EditText editTextPassword = view.findViewById(R.id.editTextPassword);

        Button buttonSend = view.findViewById(R.id.buttonLogin);
        buttonSend.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              String username = String.valueOf(editTextUsername.getText());
                                              String password = String.valueOf(editTextPassword.getText());

                                              int userIndex = -1;
                                              boolean userFound = false;
                                              boolean passwordCorrect = false;

                                              for(int i = 0; i < uaList.size(); i++)
                                              {
                                                  if(uaList.get(i).getUserName().equals(username))
                                                  {
                                                      userIndex = i;
                                                      userFound = true;
                                                  }
                                              }

                                              if(userFound == true)
                                              {
                                                  if(uaList.get(userIndex).getUserPassword().equals(password))
                                                  {
                                                      passwordCorrect = true;
                                                  }
                                              }
                                              else
                                              {
                                                  //wrong username
                                                  Toast.makeText(getContext(), "Username not exist! Please try again." , Toast.LENGTH_LONG).show();
                                                  editTextPassword.setText("");
                                              }

                                              if(userFound == true && passwordCorrect == true)
                                              {
                                                  //success login
                                                  Toast.makeText(getContext(), "Logged in successfully.", Toast.LENGTH_LONG).show();
                                                  editTextPassword.setText("");
                                                  editTextUsername.setText("");

                                                  SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);

                                                  SharedPreferences.Editor editor = sharedPreferences.edit();

                                                  editor.putString("username", uaList.get(userIndex).getUserName());
                                                  editor.putString("realName", uaList.get(userIndex).getUserRealName());
                                                  editor.putBoolean("login", true);
                                                  if (uaList.get(userIndex).getUserAccountType() == 1) {
                                                      editor.putBoolean("organization", true);
                                                  }
                                                  else {
                                                      editor.putBoolean("organization", false);
                                                  }

                                                  editor.apply();

                                                  ((MainActivity)getActivity()).loadData();

                                                  getActivity().setTitle("GoodBoy");

                                                  FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();

                                                  FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

                                                  HomeFragment importFragment = new HomeFragment();

                                                  fragmentTransaction.replace(R.id.fragment_content,importFragment);

                                                  fragmentTransaction.commit();

                                              }
                                              else if (userFound == true && passwordCorrect == false)
                                              {
                                                  //correct username wrong pass
                                                  Toast.makeText(getContext(), "Incorrect password! Please try again." , Toast.LENGTH_LONG).show();
                                                  editTextPassword.setText("");
                                              }
                                          }
                                      }
        );

        return view;
    }

    private void downloadUser(Context context, String url) {
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
                            uaList.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject courseResponse = (JSONObject) response.get(i);
                                String userName = courseResponse.getString("name");
                                int userAge = Integer.parseInt(courseResponse.getString("age"));
                                String userPassword = courseResponse.getString("password");
                                String userRealName = courseResponse.getString("real_name");
                                String userAddress = courseResponse.getString("address");
                                String userContactNumber = courseResponse.getString("contact_no");
                                String userGender = courseResponse.getString("gender");
                                String userICNumber = courseResponse.getString("ic_no");
                                int userAccountType = Integer.parseInt(courseResponse.getString("acc_type"));
                                User user = new User(userName, userPassword, userRealName, userAddress, userContactNumber,userAge,userGender,userICNumber, userAccountType);
                                uaList.add(user);
                            }

                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
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
}
