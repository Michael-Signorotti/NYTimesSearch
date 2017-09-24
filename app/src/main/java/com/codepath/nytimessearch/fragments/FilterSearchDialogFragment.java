package com.codepath.nytimessearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.nytimessearch.interfaces.FilterSearchDialogListener;
import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.SearchFilter;

import java.util.Calendar;

/**
 * Created by michaelsignorotti on 9/23/17.
 */

public class FilterSearchDialogFragment extends DialogFragment  {

    private EditText etBeginDate;
    private Spinner sSortOrder;
    private CheckBox cbArt;
    private CheckBox cbFashionAndStyle;
    private CheckBox cbSports;
    private Button btnSave;

    private Calendar calendar;

    public FilterSearchDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterSearchDialogFragment newInstance(String title) {
        FilterSearchDialogFragment frag = new FilterSearchDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_search, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etBeginDate = (EditText) view.findViewById(R.id.etBeginDate);
        sSortOrder = (Spinner) view.findViewById(R.id.sSortOrder);
        cbArt = (CheckBox) view.findViewById(R.id.cbArt);
        cbFashionAndStyle = (CheckBox) view.findViewById(R.id.cbFashionStyle);
        cbSports = (CheckBox) view.findViewById(R.id.cbSports);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        SearchFilter searchFilter = (SearchFilter) getArguments().getSerializable("searchFilter");

        if (searchFilter != null) {
            //check if a begin date filter has been set
            if (searchFilter.getBeginDate() != null) {
                etBeginDate.setText(searchFilter.getBeginDateString());
                calendar = searchFilter.getBeginDate();
            }
            //check the sort order selection
            if (searchFilter.getSortOrder().equals("Newest")) {
                sSortOrder.setSelection(1);
            }
            cbArt.setChecked(searchFilter.isArt());
            cbFashionAndStyle.setChecked(searchFilter.isFashionAndStyle());
            cbSports.setChecked(searchFilter.isSports());

        }

        etBeginDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String sortOrder = sSortOrder.getSelectedItem().toString();
                boolean art = cbArt.isChecked();
                boolean fashionAndStyle = cbFashionAndStyle.isChecked();
                boolean sports = cbSports.isChecked();

                SearchFilter sf = new SearchFilter(calendar, sortOrder, art, fashionAndStyle, sports);
                // Return input text back to activity through the implemented listener
                FilterSearchDialogListener listener = (FilterSearchDialogListener) getActivity();
                listener.onFinishFilterSearchDialog(sf);
                // Close the dialog and return back to the parent activity
                getDialog().dismiss();
            }
        });

    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(FilterSearchDialogFragment.this, 300);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void setBeginDate(Calendar c) {
        this.calendar = c;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String dateString = month + "/" + day + "/" + year;
        etBeginDate.setText(dateString);
    }



}