package com.example.markrogalski.homeaway;

import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class DetailsActivity extends AppCompatActivity {

    private SeatEvent mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView titleView = findViewById(R.id.title);
        TextView locationView = findViewById(R.id.location);
        TextView dateView = findViewById(R.id.date);
        ImageView imageView = findViewById(R.id.imageView);
        TextView pricesTitleView = findViewById(R.id.pricesTitle);
        TableLayout pricesTable = findViewById(R.id.pricesTable);
        TextView lowPriceView = findViewById(R.id.low_price);
        TextView averagePriceView = findViewById(R.id.average_price);
        TextView highPriceView = findViewById(R.id.high_price);

        Intent intent = getIntent();
        if (intent != null) {
            mEvent = new SeatEvent(intent.getBundleExtra(MainActivity.KEY_SEAT_EVENT));

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mEvent.mShortTitle);
            }
            titleView.setText(mEvent.mTitle);
            locationView.setText(mEvent.mLocation);
            dateView.setText(mEvent.mDate);

            if (mEvent.mLowPrice > 0) {
                pricesTitleView.setVisibility(View.VISIBLE);
                pricesTable.setVisibility(View.VISIBLE);
                DecimalFormat formatter = new DecimalFormat("$#.00");
                lowPriceView.setText(formatter.format(mEvent.mLowPrice));
                averagePriceView.setText(formatter.format(mEvent.mAveragePrice));
                highPriceView.setText(formatter.format(mEvent.mHighPrice));
            }
            else {
                pricesTitleView.setVisibility(View.GONE);
                pricesTable.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(mEvent.mImageUrl)) {
                Picasso.get().load(mEvent.mImageUrl).into(imageView);
            }
        }

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(mEvent.mIsFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle this event's favorite status
                mEvent.mIsFavorite = !mEvent.mIsFavorite;
                // Persist favorite status
                FavoriteRepo.getSingleton(null).setFavoriteStatus(mEvent);
                // Toggle FAB
                fab.setImageResource(mEvent.mIsFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
