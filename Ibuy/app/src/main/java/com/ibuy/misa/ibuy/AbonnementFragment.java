package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import async.client.AbonnementAsync;
import async.client.FavorisAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class AbonnementFragment extends Fragment {
    private LinearLayout contentFavoris;

    public AbonnementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abonnement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);

        contentFavoris = (LinearLayout) getView().findViewById(R.id.contentFavoris);

        AbonnementAsync abonnementAsync = new AbonnementAsync();
        abonnementAsync.setActivity(this.getActivity());
        abonnementAsync.execute();

        FavorisAsync favorisAsync = new FavorisAsync();
        favorisAsync.setAbonnementFragment(this);
        favorisAsync.execute();
    }

    public LinearLayout getContentFavoris() {
        return contentFavoris;
    }

    public void setContentFavoris(LinearLayout contentFavoris) {
        this.contentFavoris = contentFavoris;
    }
}
