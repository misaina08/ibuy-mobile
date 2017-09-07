package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import async.achat.ListeCommandeAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommandesFragment extends Fragment {
    public CommandesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commandes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    public void initData() {
        ListeCommandeAsync listeCommandeAsync = new ListeCommandeAsync();
        listeCommandeAsync.setCommandesFragment(this);
        listeCommandeAsync.execute();
    }
}
