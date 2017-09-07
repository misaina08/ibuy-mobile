package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import async.client.MesEvenementsAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvtClientFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton fabAdd;

    public EvtClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evt_client, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAdd = (FloatingActionButton) getView().findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fabAdd) {
            System.out.println("huhu");
            AjoutEvtDialog ajoutEvtDialog = new AjoutEvtDialog();
            ajoutEvtDialog.setEvtClientFragment(this);
            ajoutEvtDialog.show(this.getActivity().getFragmentManager(), "popup");
        }
    }

    public void init() {
        MesEvenementsAsync async = new MesEvenementsAsync();
        async.setActivity(getActivity());
        async.execute();
    }
}
