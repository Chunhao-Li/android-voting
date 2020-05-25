package com.example.votingapp.edit_voting;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    /**
     * A Dialog for picking the time of the deadline.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        VotingEditActivity activity = (VotingEditActivity) getActivity();
        if (activity != null) {
            dismiss();
            activity.processTimePickerAndSave(hourOfDay, minute);
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
