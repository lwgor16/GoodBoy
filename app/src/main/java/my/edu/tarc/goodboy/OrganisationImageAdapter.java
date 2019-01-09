package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class OrganisationImageAdapter extends PagerAdapter {
    Activity activity;
    private Context mContext;
    private int[] mImageIds = new int[]{R.drawable.bye,R.drawable.gg,R.drawable.hi};

    OrganisationImageAdapter(Context context)
    {
        this.mContext = context;
    }
    @Override
    public int getCount()
    {
        return mImageIds.length;
    }
    @Override
    public boolean isViewFromObject(View view,  Object o)
    {
        return view == o;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.fragment_home,null);

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent organisationDetails = new Intent(view.getContext(), DisplayOrganisationDetails.class);
                organisationDetails.putExtra("position",position);
                v.getContext().startActivity(organisationDetails);
            }
        });
        container.addView(imageView,0);
        return imageView;

    }
    @Override
    public void destroyItem(ViewGroup container, int position,  Object object) {
        container.removeView((ImageView) object);
    }
}
