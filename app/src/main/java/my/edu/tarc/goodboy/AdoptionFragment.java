package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdoptionFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static RequestQueue queue;
    private ProgressDialog pDialog;
    private List<Dog> daList;
    private List<Dog> recommendedList;
    private List<Dog> searchList;
    private List<Dog> adoptedList;
    private ListView listViewDog;
    private static final String TAG = "my.edu.tarc.lab44";
    private static String USER_URL = "https://khorwe.000webhostapp.com/select_dog.php";
    private EditText editTextAdoptionSearch;
    private Button buttonAdoptionSearch;
    private int spinnerIndex;
    SharedPreferences sharedPreferences;

    public AdoptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adoption, container, false);

        //sendMessage = (SendMessage) getActivity();

        pDialog = new ProgressDialog(getActivity());

        daList = new ArrayList<>();
        recommendedList = new ArrayList<>();
        searchList = new ArrayList<>();
        adoptedList = new ArrayList<>();
        spinnerIndex = 0;

        listViewDog = (ListView) view.findViewById(R.id.listViewDog);

        final Spinner spinner = view.findViewById(R.id.spinnerSearchCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.searchCategory, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextAdoptionSearch = view.findViewById(R.id.editTextAdoptionSearch);
        buttonAdoptionSearch = view.findViewById(R.id.buttonAdoptionSearch);

        buttonAdoptionSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchDog(spinnerIndex);
            }
        }
        );

        listViewDog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dogID = ((Dog) listViewDog.getItemAtPosition(position)).getDogId();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("dogDetailsID", dogID);

                editor.apply();

                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

                DogDetailsAndAdoption importFragment = new DogDetailsAndAdoption();

                fragmentTransaction.replace(R.id.fragment_content,importFragment);

                fragmentTransaction.commit();
            }
        });

        downloadAllDog(getContext(), USER_URL);

        return view;
    }

    private void downloadAllDog(Context context, String url)
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);
        final String adoptedDogId = sharedPreferences.getString("dogDetailsID", "");

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

                            for(int i = 0; i < daList.size(); i++)
                            {
                                if(daList.get(i).getDogOrganization().equals("SPCA Selangor"))
                                {
                                    daList.get(i).setDogOrganization("SPCA");
                                }
                                else if (daList.get(i).getDogOrganization().equals("HOPE (Homeless & Orphan Pet Exist)"))
                                {
                                    daList.get(i).setDogOrganization("HOPE");
                                }
                                else if (daList.get(i).getDogOrganization().equals("PAWS Animal Welfare Society"))
                                {
                                    daList.get(i).setDogOrganization("PAWS");
                                }
                                else if (daList.get(i).getDogOrganization().equals("Second Chance Animal Society"))
                                {
                                    daList.get(i).setDogOrganization("Second Chance");
                                }
                            }

                            loadRecommendedDogs();

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

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("dogDetailsID", "");

        editor.apply();

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadRecommendedDogs()
    {
        recommendedList.clear();
        for(int i = 0; i<daList.size(); i++)
        {
            if(daList.get(i).getDogAge() >= 5)
            {
                recommendedList.add(daList.get(i));
            }
        }

        final DogAdapter adapter = new DogAdapter(getActivity(), R.layout.fragment_organization, recommendedList);
        listViewDog.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String breedExample = "Eg: Shiba Inu, huskey, golden retriever";
        String colorExample = "Eg: white, black, brown";
        String ageExample = "Eg: 0-10, 2, 1-3";
        String organizationExample = "Eg: paws, HOPE";
        String genderExample = "Eg: M, f, female";

        spinnerIndex = position;

        if(position == 0)
        {
            editTextAdoptionSearch.setHint(breedExample);
        }
        else if(position == 1)
        {
            editTextAdoptionSearch.setHint(colorExample);
        }
        else if(position == 2)
        {
            editTextAdoptionSearch.setHint(ageExample);
        }
        else if(position == 3)
        {
            editTextAdoptionSearch.setHint(organizationExample);
        }
        else if(position == 4)
        {
            editTextAdoptionSearch.setHint(genderExample);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void searchDog(int index)
    {
        String search = editTextAdoptionSearch.getText().toString();
        searchList.clear();

        if(search.length() == 0)
        {
            loadRecommendedDogs();
        }
        else
        {
            if(index == 0)
            {
                //search by breed
                search = search.toLowerCase();

                for(int i = 0; i < daList.size(); i++)
                {
                    if(daList.get(i).getDogBreed().toLowerCase().equals(search))
                    {
                        searchList.add(daList.get(i));
                    }
                }
            }
            else if (index == 1)
            {
                //search by color
                search = search.toLowerCase();

                for(int i = 0; i < daList.size(); i++)
                {
                    if(daList.get(i).getDogColor().toLowerCase().equals(search))
                    {
                        searchList.add(daList.get(i));
                    }
                }
            }
            else if (index == 2)
            {
                //search by age
                int rangeSign = search.indexOf('-');
                int endSign = search.length();
                int minRange = 0;
                int maxRange = 0;
                int age = 0;

                if(search.indexOf('-') == -1)
                {
                    age = Integer.parseInt(search);

                    for(int i = 0; i < daList.size(); i++)
                    {
                        if(daList.get(i).getDogAge() == age)
                        {
                            searchList.add(daList.get(i));
                        }
                    }
                }
                else
                {
                    minRange = Integer.parseInt(search.substring(0, rangeSign));
                    maxRange = Integer.parseInt(search.substring(rangeSign + 1, endSign));

                    for(int i = 0; i < daList.size(); i++)
                    {
                        if(daList.get(i).getDogAge() >= minRange && daList.get(i).getDogAge() <= maxRange)
                        {
                            searchList.add(daList.get(i));
                        }
                    }
                }
            }
            else if (index == 3)
            {
                //search by organization
                search = search.toLowerCase();

                if(search.contains("paw"))
                {
                    search = "paws";
                }
                else if(search.contains("hope"))
                {
                    search = "hope";
                }
                else if (search.contains("second") || search.contains("chance") || search.contains("animal") || search.contains("society"))
                {
                    search = "second chance";
                }
                else if (search.contains("spca") || search.contains("selangor"))
                {
                    search = "spca";
                }
                else if (search.contains("kl") || search.contains("resort")|| search.contains("rescue")|| search.contains("pooch"))
                {
                    search = "kl pooch resort and rescue";
                }

                for(int i = 0; i < daList.size(); i++)
                {
                    if(daList.get(i).getDogOrganization().toLowerCase().equals(search))
                    {
                        searchList.add(daList.get(i));
                    }
                }
            }
            else if (index == 4)
            {
                //search by gender
                search = search.toLowerCase();
                if(search.equals("f"))
                {
                    search = "female";
                }
                else if (search.equals("m"))
                {
                    search = "male";
                }

                for(int i = 0; i < daList.size(); i++)
                {
                    if(daList.get(i).getDogGender().toLowerCase().equals(search))
                    {
                        searchList.add(daList.get(i));
                    }
                }
            }

            if(searchList.size() == 0)
            {
                Toast.makeText(getActivity().getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
            }
            else if (searchList.size() == 1)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Result found!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Results found!", Toast.LENGTH_LONG).show();
            }

            final DogAdapter adapter = new DogAdapter(getActivity(), R.layout.fragment_organization, searchList);
            listViewDog.setAdapter(adapter);
        }
    }

}
