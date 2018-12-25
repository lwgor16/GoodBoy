package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Activity context, int resource, List<User> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.user_record, parent, false);

        TextView textViewName, textViewPassword, textViewRealName, textViewAddress, textViewContactNo, textViewAge, textViewGender, textViewICNo;

        textViewName = rowView.findViewById(R.id.textViewName);
        textViewPassword = rowView.findViewById(R.id.textViewPassword);
        textViewRealName = rowView.findViewById(R.id.textViewRealName);
        textViewAddress = rowView.findViewById(R.id.textViewAddress);
        textViewContactNo = rowView.findViewById(R.id.textViewContactNo);
        textViewAge = rowView.findViewById(R.id.textViewAge);
        textViewGender = rowView.findViewById(R.id.textViewGender);
        textViewICNo = rowView.findViewById(R.id.textViewICNo);

        textViewName.setText(String.format("%s : %s", getContext().getString(R.string.userName), user.getUserName()));
        textViewPassword.setText(String.format("%s : %s", getContext().getString(R.string.userPassword), user.getUserPassword()));
        textViewRealName.setText(String.format("%s : %s", getContext().getString(R.string.userRealName), user.getUserRealName()));
        textViewAddress.setText(String.format("%s : %s", getContext().getString(R.string.userAddress), user.getUserAddress()));
        textViewContactNo.setText(String.format("%s : %s", getContext().getString(R.string.userContactNo), user.getUserContactNumber()));
        textViewAge.setText(String.format("%s : %s", getContext().getString(R.string.userAge), user.getUserAge()));
        textViewGender.setText(String.format("%s : %s", getContext().getString(R.string.userGender), user.getUserGender()));
        textViewICNo.setText(String.format("%s : %s", getContext().getString(R.string.userICNo), user.getUserICNumber()));

        return rowView;
    }
}
