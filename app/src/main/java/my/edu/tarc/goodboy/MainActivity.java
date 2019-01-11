package my.edu.tarc.goodboy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static boolean LoggedIn = false;
    private static String username = "";
    private static String realName = "";

    SharedPreferences sharedPreferences;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager1 = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

        HomeFragment importFragment = new HomeFragment();

        fragmentTransaction.replace(R.id.fragment_content,importFragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("GoodBoy");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            HomeFragment importFragment = new HomeFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_organization) {
            setTitle("Organization");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            OrganizationFragment importFragment = new OrganizationFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_events) {
            setTitle("Events");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            EventFragment importFragment = new EventFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_adoption) {
            setTitle("Adoption");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            AdoptionFragment importFragment = new AdoptionFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            setTitle("Settings");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            SettingsFragment importFragment = new SettingsFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_feedback) {
            setTitle("Feedback");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            FeedbackFragment importFragment = new FeedbackFragment();

            fragmentTransaction.replace(R.id.fragment_content,importFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_login) {
            if(LoggedIn == false)
            {
                setTitle("Login");

                FragmentManager fragmentManager1 = getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

                LoginFragment loginFragment = new LoginFragment();

                fragmentTransaction.replace(R.id.fragment_content,loginFragment);

                fragmentTransaction.commit();
            }
            else
            {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username", "");
                editor.putString("realName", "");
                editor.putBoolean("login", false);

                editor.apply();

                loadData();

                Toast.makeText(getBaseContext(), "Successfully logged out!" , Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_register) {
            setTitle("Register");

            FragmentManager fragmentManager1 = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

            RegisterFragment registerFragment = new RegisterFragment();

            fragmentTransaction.replace(R.id.fragment_content,registerFragment);

            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadData()
    {
        sharedPreferences = getSharedPreferences(getString(R.string.pref_file), MODE_PRIVATE);

        username = sharedPreferences.getString("username", "");
        realName = sharedPreferences.getString("realName", "");
        LoggedIn = sharedPreferences.getBoolean("login", false);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        final TextView textViewNavUsername = headerLayout.findViewById(R.id.textViewNavHeaderUsername);
        final TextView textViewNavRealName = headerLayout.findViewById(R.id.textViewNavHeaderRealName);
        final TextView textViewNotLoggedIn = headerLayout.findViewById(R.id.textViewNotLoggedIn);
        final Menu menu = navigationView.getMenu();
        final MenuItem loginItem = menu.findItem(R.id.nav_login);

        if(LoggedIn == true)
        {
            textViewNavUsername.setText(username);
            textViewNavRealName.setText(realName);
            textViewNotLoggedIn.setText("");
            loginItem.setTitle("Logout");
        }
        else
        {
            textViewNavUsername.setText("");
            textViewNavRealName.setText("");
            textViewNotLoggedIn.setText("Not logged in");
            loginItem.setTitle("Login");
        }
    }
}
