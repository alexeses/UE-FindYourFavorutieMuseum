package com.github.ue_museumfilter.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.github.ue_museumfilter.R;

public class DialogFilter extends DialogFragment {
    private Spinner filter_spinner;
    private OnFilterSelectedListener listener;

    public interface OnFilterSelectedListener {
        void onFilterSelected(String distrito);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnFilterSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnFilterSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);

        filter_spinner = view.findViewById(R.id.filter_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.distritos_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_spinner.setAdapter(adapter);

        // TODO: Style the dialog to match the app theme

        builder.setView(view)
                .setPositiveButton("Filter", (dialog, which) -> {
                    listener.onFilterSelected(filter_spinner.getSelectedItem().toString());
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });

        return builder.create();
    }

}