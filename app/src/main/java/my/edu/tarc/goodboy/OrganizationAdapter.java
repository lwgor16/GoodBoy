package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrganizationAdapter extends ArrayAdapter<Organization> {
    public OrganizationAdapter(Activity context, int resource, List<Organization> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Organization organization = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.organization_record, parent, false);

        TextView textViewID, textViewName, textViewOwner, textViewPhoneNumber, textViewLocation;

        textViewID = rowView.findViewById(R.id.textViewID);
        textViewName = rowView.findViewById(R.id.textViewName);
        textViewPhoneNumber = rowView.findViewById(R.id.textViewPhoneNo);
        textViewLocation = rowView.findViewById(R.id.textViewLocation);
        textViewOwner = rowView.findViewById(R.id.textViewOwner);

        textViewID.setText(String.format("%s : %s", getContext().getString(R.string.organizationId), organization.getOrganizationID()));
        textViewName.setText(String.format("%s : %s", getContext().getString(R.string.organizationName), organization.getOrganizationName()));
        textViewPhoneNumber.setText(String.format("%s : %s", getContext().getString(R.string.organizationPhoneNo), organization.getOrganizationPhoneNumber()));
        textViewOwner.setText(String.format("%s : %s", getContext().getString(R.string.organizationOwner), organization.getOrganizationOwner()));
        textViewLocation.setText(String.format("%s : %s", getContext().getString(R.string.organizationLocation), organization.getOrganizationLocation()));

        return rowView;
    }
}
