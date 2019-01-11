package my.edu.tarc.goodboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventDetailsAdapter extends ArrayAdapter<Event> {
    public EventDetailsAdapter(Activity context, int resource, List<Event> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.event_record, parent, false);

        TextView textViewID, textViewName, textViewDesc, textViewDateTime, textViewLocation, textOrganiser;

        textViewID = rowView.findViewById(R.id.textViewID);
        textViewName = rowView.findViewById(R.id.textViewName);
        textViewDesc = rowView.findViewById(R.id.textViewDescription);
        textViewDateTime = rowView.findViewById(R.id.textViewDateTime);
        textViewLocation = rowView.findViewById(R.id.textViewLocation);
        textOrganiser = rowView.findViewById(R.id.textViewOrganizer);

        textViewID.setText(String.format("%s : %s", getContext().getString(R.string.eventID), event.getEventID()));
        textViewName.setText(String.format("%s : %s", getContext().getString(R.string.eventName), event.getEventName()));
        textViewDesc.setText(String.format("%s : %s", getContext().getString(R.string.eventDesc), event.getEventDesc()));
        textViewDateTime.setText(String.format("%s : %s", getContext().getString(R.string.eventDateTime), event.getEventDateTime()));
        textViewLocation.setText(String.format("%s : %s", getContext().getString(R.string.eventLocation), event.getEventLocation()));
        textOrganiser.setText(String.format("%s : %s", getContext().getString(R.string.eventOrganizer), event.getEventOrganiser()));

        return rowView;
    }
}
