package com.ibuy.misa.ibuy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import async.produit.ListSouhaitAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListeSouhaitsFragment extends Fragment {


    public ListeSouhaitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste_souhaits, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListSouhaitAsync listSouhaitAsync = new ListSouhaitAsync();
        listSouhaitAsync.setActivity(getActivity());
        listSouhaitAsync.execute();
    }
}
