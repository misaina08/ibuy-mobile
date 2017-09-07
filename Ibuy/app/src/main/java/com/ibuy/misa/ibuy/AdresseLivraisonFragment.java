package com.ibuy.misa.ibuy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import utilitaire.Coordonnee;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdresseLivraisonFragment extends DialogFragment {
    private EditText tAdresse;
    private CheckBox checkPosition;
    private PanierFragment panierFragment;
    public Coordonnee currPos;

    public AdresseLivraisonFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_adresse_livraison, null);

        currPos = new Coordonnee();

        tAdresse = (EditText) view.findViewById(R.id.tAdresse);
        checkPosition = (CheckBox) view.findViewById(R.id.checkPosition);
        checkPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCheckPotision();
            }
        });

        builder.setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            onValid();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdresseLivraisonFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void clickCheckPotision() {
        if (checkPosition.isChecked()) {
            FusedLocationProviderClient mFusedLocationClient;
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getPanierFragment().getActivity());
            if (ActivityCompat.checkSelfPermission(getPanierFragment().getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getPanierFragment().getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("no permissions");
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getPanierFragment().getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
//                                    Coordonnees de la position actuelle
                                currPos.setLatitude(location.getLatitude());
                                currPos.setLongitude(location.getLongitude());
                                panierFragment.setLongitude(location.getLongitude());
                                panierFragment.setLatitude(location.getLatitude());
                                Geocoder geocoder = new Geocoder(getPanierFragment().getActivity(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(
                                            location.getLatitude(),
                                            location.getLongitude(),
                                            // In this sample, get just a single address.
                                            1);
//                                         nom du lieu : Rue, Ville, Pays
                                    String cityName = addresses.get(0).getAddressLine(0);
                                    tAdresse.setText(cityName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }

    }

    public void onValid() {
        panierFragment.setAdresseLivraison(tAdresse.getText().toString());
        panierFragment.setLatitude(new Double(0));
        panierFragment.setLongitude(new Double(0));
        if (checkPosition.isChecked()) {
            panierFragment.setLatitude(currPos.getLatitude());
            panierFragment.setLongitude(currPos.getLongitude());
        }
    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
