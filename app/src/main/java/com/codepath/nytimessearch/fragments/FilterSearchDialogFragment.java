package com.codepath.nytimessearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.nytimessearch.R;

/**
 * Created by michaelsignorotti on 9/23/17.
 */

public class FilterSearchDialogFragment extends DialogFragment {

    private EditText etBeginDate;
    private Spinner sSortOrder;
    private CheckBox cbArt;
    private CheckBox cbFashionAndStyle;
    private CheckBox cbSports;
    private Button btnSave;

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

    }
}