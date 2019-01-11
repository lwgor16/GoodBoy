package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrganisationListAdapter extends ArrayAdapter<Organization> {
    public OrganisationListAdapter(Activity context, int resource, List<Organization> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Organization organization = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.organisation_list, parent, false);

        TextView textViewName;

        textViewName = rowView.findViewById(R.id.textViewName);

        textViewName.setText(String.format("%s : %s", getContext().getString(R.string.organizationName), organization.getOrganizationName()));

        return rowView;
    }
}
