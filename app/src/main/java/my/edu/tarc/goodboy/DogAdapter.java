package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DogAdapter extends ArrayAdapter<Dog> {

    public DogAdapter(Activity context, int resource, List<Dog> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dog dog = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.dog_record, parent, false);

        TextView textViewID, textViewBreed, textViewColor, textViewCondition, textViewOrganization, textViewGender, textViewAge, textViewSize;

        textViewBreed = rowView.findViewById(R.id.textViewBreed);
        textViewColor = rowView.findViewById(R.id.textViewColor);
        textViewOrganization = rowView.findViewById(R.id.textViewOrganization);
        textViewGender = rowView.findViewById(R.id.textViewGender);
        textViewAge = rowView.findViewById(R.id.textViewAge);

        textViewBreed.setText(String.format("%s : %s", getContext().getString(R.string.dogBreed), dog.getDogBreed()));
        textViewColor.setText(String.format("%s : %s", getContext().getString(R.string.dogColor), dog.getDogColor()));
        textViewOrganization.setText(String.format("%s : %s", getContext().getString(R.string.dogOrganization), dog.getDogOrganization()));
        textViewGender.setText(String.format("%s : %s", getContext().getString(R.string.dogGender), dog.getDogGender()));
        textViewAge.setText(String.format("%s : %s", getContext().getString(R.string.dogAge), dog.getDogAge()));

        return rowView;
    }
}
