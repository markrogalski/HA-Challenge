package com.example.markrogalski.homeaway;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;


public class MainActivity extends AppCompatActivity implements TextWatcher {

    static final String TAG = "HA";
    private static final String KEY_QUERY_STRING = "QueryString";
    static final String KEY_SEAT_EVENT = "SeatEvent";

    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mAdapter;
    private String mCurFilter;
    private EventViewModel mModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurFilter = savedInstanceState.getString(KEY_QUERY_STRING);
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText searchText = findViewById(R.id.search_text);
        searchText.addTextChangedListener(this);
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        mRecyclerView = findViewById(R.id.search_results);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SearchResultsAdapter(new ResultsOnClickListener());
        mRecyclerView.setAdapter(mAdapter);

        // Get ViewModel and set observer to update adapter
        mModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mModel.getData().observe(this, new Observer<List<SeatEvent>>() {
            @Override
            public void onChanged(@Nullable List<SeatEvent> data) {
                mAdapter.setData(data);
            }
        });

        // Initialize favorite repo
        FavoriteRepo.getSingleton(this.getApplicationContext()).setLiveData((EventLiveData) mModel.getData());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUERY_STRING, mCurFilter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String newFilter = s.toString().trim();

        // Don't do anything if the filter hasn't changed.
        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return;
        }

        mCurFilter = newFilter;
        ((EventLiveData) mModel.getData()).doQuery(mCurFilter);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void showDetails(SeatEvent event) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(KEY_SEAT_EVENT, event.toBundle());
        startActivity(intent);
    }


    private class ResultsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            SeatEvent event = mAdapter.get(itemPosition);
            showDetails(event);
        }
    }
}
