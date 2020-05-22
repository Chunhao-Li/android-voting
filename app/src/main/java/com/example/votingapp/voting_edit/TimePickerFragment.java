package com.example.votingapp.voting_edit;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.votingapp.VotingEditActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        VotingEditActivity activity = (VotingEditActivity) getActivity();
        if (activity != null) {
            dismiss();
            activity.processTimePicker(hourOfDay, minute);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }
}
