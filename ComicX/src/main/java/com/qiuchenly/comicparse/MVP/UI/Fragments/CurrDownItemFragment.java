package com.qiuchenly.comicparse.MVP.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiuchenly.comicparse.MVP.UI.Fragments.dummy.DummyContent;
import com.qiuchenly.comicparse.R;

/**
 * 当前下载任务的Fragment
 * 父Activity必须实现当前接口: {@link OnListTaskInfo}
 */
public class CurrDownItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    private static CurrDownItemFragment mCurrDownItemFragment = null;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListTaskInfo mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CurrDownItemFragment() {
    }

    public static CurrDownItemFragment newInstance(int columnCount) {
        if (mCurrDownItemFragment == null) {
            mCurrDownItemFragment = new CurrDownItemFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_COLUMN_COUNT, columnCount);
            mCurrDownItemFragment.setArguments(args);
        }
        return mCurrDownItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currdownitem_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyCurrDownItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListTaskInfo) {
            mListener = (OnListTaskInfo) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " 必须实现这个接口: " + OnListTaskInfo.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCurrDownItemFragment = null;
    }

    /**
     * 必须在父Activity下实现此接口
     */
    public interface OnListTaskInfo {
        // TODO: Update argument type and name
        void onSuspendTask();

        void onResumeTask();

        void onDeleteTask();
    }
}
