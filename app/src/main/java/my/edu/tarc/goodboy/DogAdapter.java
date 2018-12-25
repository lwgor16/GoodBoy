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

        textViewID = rowView.findViewById(R.id.textViewID);
        textViewBreed = rowView.findViewById(R.id.textViewBreed);
        textViewColor = rowView.findViewById(R.id.textViewColor);
        textViewCondition = rowView.findViewById(R.id.textViewCondition);
        textViewOrganization = rowView.findViewById(R.id.textViewOrganization);
        textViewGender = rowView.findViewById(R.id.textViewGender);
        textViewAge = rowView.findViewById(R.id.textViewAge);
        textViewSize = rowView.findViewById(R.id.textViewSize);

        textViewID.setText(String.format("%s : %s", getContext().getString(R.string.dogId), dog.getDogId()));
        textViewBreed.setText(String.format("%s : %s", getContext().getString(R.string.dogBreed), dog.getDogBreed()));
        textViewColor.setText(String.format("%s : %s", getContext().getString(R.string.dogColor), dog.getDogColor()));
        textViewCondition.setText(String.format("%s : %s", getContext().getString(R.string.dogCondition), dog.getDogCondition()));
        textViewOrganization.setText(String.format("%s : %s", getContext().getString(R.string.dogOrganization), dog.getDogOrganization()));
        textViewGender.setText(String.format("%s : %s", getContext().getString(R.string.dogGender), dog.getDogGender()));
        textViewAge.setText(String.format("%s : %s", getContext().getString(R.string.dogAge), dog.getDogAge()));
        textViewSize.setText(String.format("%s : %s", getContext().getString(R.string.dogSize), dog.getDogSize()));

        return rowView;
    }
}
