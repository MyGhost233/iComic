package com.qiuchenly.comicparse.MVP.UI.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiuchenly.comicparse.MVP.UI.Fragments.dummy.DummyContent.DummyItem;
import com.qiuchenly.comicparse.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display getApi {@link DummyItem} and makes getApi call to the
 * specified {@link com.qiuchenly.comicparse.MVP.UI.Fragments.CurrDownItemFragment.OnListTaskInfo}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDownSuccItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDownSuccItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final CurrDownItemFragment.OnListTaskInfo mListener;

    public MyDownSuccItemRecyclerViewAdapter(List<DummyItem> items, CurrDownItemFragment.OnListTaskInfo listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_downsuccitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
