package com.example.markrogalski.homeaway;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.List;

class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private List<SeatEvent> mDataset;
    final private OnClickListener mOnClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircularImageView mThumbnail;
        private final TextView mDescription;
        private final TextView mLocation;
        private final TextView mDate;
        private final ImageView mFavoriteIndicator;

        private ViewHolder(final View view) {
            super(view);
            mThumbnail = view.findViewById(R.id.thumbnail);
            mDescription = view.findViewById(R.id.description);
            mLocation = view.findViewById(R.id.location);
            mDate = view.findViewById(R.id.date);
            mFavoriteIndicator = view.findViewById(R.id.favorite_indicator);
        }
    }

    SearchResultsAdapter(final OnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    public void setData(final List<SeatEvent> data) {
        mDataset = data;
        notifyDataSetChanged();
    }

    @Override
    public @NonNull SearchResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Return a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind to existing view
        SeatEvent event = get(position);
        holder.mDescription.setText(event.mShortTitle);
        holder.mLocation.setText(event.mLocation);
        holder.mDate.setText(event.mDate);
        holder.mFavoriteIndicator.setVisibility(event.mIsFavorite ? View.VISIBLE : View.INVISIBLE);

        if (!TextUtils.isEmpty(event.mImageUrl)) {
            Picasso.get().load(event.mImageUrl).into(holder.mThumbnail);
        }
        else {
            holder.mThumbnail.setImageResource(R.drawable.concert_thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    SeatEvent get(final int position) {
        return mDataset.get(position);
    }
}
