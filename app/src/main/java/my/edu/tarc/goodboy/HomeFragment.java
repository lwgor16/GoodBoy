package my.edu.tarc.goodboy;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.acl.Group;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager, viewPager2;
    ImageAdapter imageAdapter, imageAdapter2;
    LinearLayout sliderDotspanel, sliderDotspanel2;
    private int dotscount,dotscount2;
    private ImageView[] dots, dots2;
    public HomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager2 = (ViewPager) view.findViewById(R.id.viewPager2);

        sliderDotspanel =  (LinearLayout) view.findViewById(R.id.SliderDots);
        sliderDotspanel2 = (LinearLayout) view.findViewById(R.id.SliderDots2);

        imageAdapter = new ImageAdapter(getActivity());
        imageAdapter2 = new ImageAdapter(getActivity());

        viewPager.setAdapter(imageAdapter);
        viewPager2.setAdapter(imageAdapter2);

        dotscount = imageAdapter.getCount();
        dotscount2 = imageAdapter2.getCount();

        dots = new ImageView[dotscount];
        dots2 = new ImageView[dotscount2];

        for(int i = 0;i < dotscount;i++)
        {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                for(int x = 0;x<dotscount;x++)
                {
                    dots[x].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.nonactive_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        for(int i = 0;i < dotscount2;i++)
        {
            dots2[i] = new ImageView(getActivity());
            dots2[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            sliderDotspanel2.addView(dots2[i],params);
        }

        dots2[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.active_dot));

        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                for(int x = 0;x<dotscount;x++)
                {
                    dots2[x].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.nonactive_dot));
                }
                dots2[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }


}
