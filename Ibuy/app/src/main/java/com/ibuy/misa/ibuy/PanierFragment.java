package com.ibuy.misa.ibuy;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import async.achat.PanierAsync;
import sqlite.CommandeDao;


/**
 * A simple {@link Fragment} subclass.
 */
public class PanierFragment extends Fragment {

    private String adresseLivraison;
    private Double longitude;
    private Double latitude;

    private TextView tTotal;
    private FloatingActionButton fabSend;
    private FloatingActionButton fabEditAdresse;
    private FloatingActionButton fabOption;

    public ProgressDialog progressDialog;
    public PanierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panier, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tTotal = (TextView) getView().findViewById(R.id.tTotal);
        fabSend = (FloatingActionButton) getView().findViewById(R.id.fabSend);
        fabOption = (FloatingActionButton) getView().findViewById(R.id.fabOption);
        fabEditAdresse = (FloatingActionButton) getView().findViewById(R.id.fabEditAdresse);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCommand();
            }
        });
        fabEditAdresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAdresse();
            }
        });

        initData();
    }

    public void initData() {
        PanierAsync panierAsync = new PanierAsync();
        panierAsync.setPanierFragment(this);
        panierAsync.execute();
    }

    public void confirmCommand() {
        if (adresseLivraison != null) {
            new AlertDialog.Builder(this.getActivity())
                    .setMessage("Confirmer la commande ?")
                    .setCancelable(false)
                    .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sendCommand();
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        } else {
            new AlertDialog.Builder(this.getActivity())
                    .setMessage("Veuillez saisir une adresse de livraison")
                    .setPositiveButton("Saisir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            editAdresse();
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        }

    }

    public void sendCommand() {
        try {
            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Chargement...", true);
            CommandeDao commandeDao = new CommandeDao();
            commandeDao.setPanierFragment(this);
            commandeDao.passerCommande();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editAdresse() {
        AdresseLivraisonFragment adresseDialog = new AdresseLivraisonFragment();
        adresseDialog.setPanierFragment(this);
        adresseDialog.show(this.getActivity().getFragmentManager(), "popup");
    }

    public TextView gettTotal() {
        return tTotal;
    }

    public void settTotal(TextView tTotal) {
        this.tTotal = tTotal;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
