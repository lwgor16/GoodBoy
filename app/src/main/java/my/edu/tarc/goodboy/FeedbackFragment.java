package my.edu.tarc.goodboy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    EditText editTextSubject;
    EditText editTextDesc;
    Button buttonSend;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        editTextSubject = view.findViewById(R.id.editTextSubject);
        editTextDesc = view.findViewById(R.id.editTextDesc);
        buttonSend = view.findViewById(R.id.buttonFeedbackSend);

        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String emailSubject = editTextSubject.getText().toString();
                String emailDesc = editTextDesc.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto" + "ogrestudio123@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailDesc);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                {
                    startActivity(Intent.createChooser(intent, "Send email with...?"));
                }
                else
                {
                    Toast.makeText(getContext(),"No app to handle this", Toast.LENGTH_SHORT).show();
                }

                editTextSubject.setText("");
                editTextDesc.setText("");
            }
        }
        );

        return view;
    }
}
