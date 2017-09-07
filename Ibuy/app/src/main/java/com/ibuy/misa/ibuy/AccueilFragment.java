package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import async.AccueilAsync;
import async.CheckNotificationEvtProcheAsync;
import async.FilActusAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilFragment extends Fragment {
    public SwipeRefreshLayout swipeRefresh;
    public MainActivity mainActivity;
    public AccueilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);

        swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh);
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initFilActus();
            }
        });
//        initData();
        initFilActus();
//        checkNotification();
    }

    public void initData(){
        AccueilAsync accueilAsync = new AccueilAsync();
        accueilAsync.setAccueilFragment(this);
        accueilAsync.execute();
        swipeRefresh.setRefreshing(false);
    }

    public void initFilActus(){
        FilActusAsync async = new FilActusAsync();
        async.setAccueilFragment(this);
        async.execute();
    }
    public void checkNotification(){
        CheckNotificationEvtProcheAsync notificationAsync = new CheckNotificationEvtProcheAsync();
        notificationAsync.execute();
    }


}
