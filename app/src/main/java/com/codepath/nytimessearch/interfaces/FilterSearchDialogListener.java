package com.codepath.nytimessearch.interfaces;

import com.codepath.nytimessearch.models.SearchFilter;

/**
 * Created by michaelsignorotti on 9/24/17.
 */

public interface FilterSearchDialogListener {
    void onFinishFilterSearchDialog(SearchFilter filter);
}
