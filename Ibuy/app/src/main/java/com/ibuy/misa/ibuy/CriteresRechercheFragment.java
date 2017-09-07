package com.ibuy.misa.ibuy;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import async.Recherche.RechercherAsync;
import async.Recherche.SauvegarderAsync;
import modele.Client;
import modele.RechercheAvancee;
import services.ObjetsStatiques;
import services.SessionManager;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class CriteresRechercheFragment extends DialogFragment {
    ResultSearchActivity resultSearchActivity;
    Spinner spinMagasin;
    Spinner spinCategorie;
    EditText ePmin;
    EditText ePmax;
    private RechercheAvancee currentRechercheAvancee;

    int affichage = 0;


    public CriteresRechercheFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_criteres_recherche, null);

        spinMagasin = (Spinner) view.findViewById(R.id.spinMagasin);
        spinCategorie = (Spinner) view.findViewById(R.id.spinCategorie);
        List<String> catList = new ArrayList<String>();
        catList.add("Tout");
        catList.addAll(ObjetsStatiques.getCategories());

        List<String> magList = new ArrayList<String>();
        magList.add("Tout");
        magList.addAll(ObjetsStatiques.getMagasins());

        ArrayAdapter<String> adapterSpinMag = new ArrayAdapter<String>(resultSearchActivity,
                android.R.layout.simple_spinner_dropdown_item, magList);
        ArrayAdapter<String> adapterSpinCat = new ArrayAdapter<String>(resultSearchActivity,
                android.R.layout.simple_spinner_dropdown_item, catList);

        spinMagasin.setAdapter(adapterSpinMag);
        spinCategorie.setAdapter(adapterSpinCat);

        ePmin = (EditText) view.findViewById(R.id.ePmin);
        ePmax = (EditText) view.findViewById(R.id.ePmax);

        // initialisation des champs de la recherche
        if (currentRechercheAvancee != null) {
            if (currentRechercheAvancee.getPrixMax() != null) {
                ePmax.setText(currentRechercheAvancee.getPrixMax().toString());
            }
            if (currentRechercheAvancee.getPrixMin() != null) {
                ePmin.setText(currentRechercheAvancee.getPrixMin().toString());
            }
            int i = 0;
            if (currentRechercheAvancee.getCategorie() != null) {
                for (String s : catList) {
                    if (currentRechercheAvancee.getCategorie().compareToIgnoreCase(s) == 0) {
                        spinCategorie.setSelection(i);
                    }
                    i++;
                }
            }
            if (currentRechercheAvancee.getMagasin() != null) {
                i = 0;
                for (String s : magList) {
                    if (currentRechercheAvancee.getMagasin().compareToIgnoreCase(s) == 0) {
                        spinMagasin.setSelection(i);
                    }
                    i++;
                }
            }

        }

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
                .setNeutralButton("Sauvegarder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            sauvegarderRecherche();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CriteresRechercheFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public RechercheAvancee getValuesRecherche() {
        RechercheAvancee rechercheAvancee = new RechercheAvancee();
        rechercheAvancee.setCategorie(null);
        rechercheAvancee.setMagasin(null);
        if (spinCategorie.getSelectedItem().toString().compareTo("Tout") != 0) {
            rechercheAvancee.setCategorie(spinCategorie.getSelectedItem().toString());
        }
        if (spinMagasin.getSelectedItem().toString().compareTo("Tout") != 0) {
            rechercheAvancee.setMagasin(spinMagasin.getSelectedItem().toString());
        }

        rechercheAvancee.setMotCle(resultSearchActivity.getQueryFromPrevious());

        if (ePmax.getText().toString().isEmpty()) {
            rechercheAvancee.setPrixMax(null);
        } else {
            rechercheAvancee.setPrixMax(new Double(ePmax.getText().toString()));
        }
        if (ePmin.getText().toString().isEmpty()) {
            rechercheAvancee.setPrixMin(null);
        } else {
            rechercheAvancee.setPrixMin(new Double(ePmin.getText().toString()));
        }
        return rechercheAvancee;
    }

    public void onValid() {
        RechercheAvancee rechercheAvancee = getValuesRecherche();
        RechercheAvancee[] params = {rechercheAvancee};
        RechercherAsync rechercherAsync = new RechercherAsync();
        rechercherAsync.setAffichage(0);
        rechercherAsync.setResultSearchActivity(resultSearchActivity);
        rechercherAsync.execute(params);
        resultSearchActivity.setCurrentRechercheAvancee(getValuesRecherche());
    }

    public void sauvegarderRecherche() {
        try {
            SessionManager sessionManager = new SessionManager(getResultSearchActivity());
            Client c = SessionManager.getClientConnected();
            RechercheAvancee rechercheAvancee = getValuesRecherche();
            rechercheAvancee.setClient(c.getId());
            RechercheAvancee[] params = {rechercheAvancee};
            SauvegarderAsync sauvegarderAsync = new SauvegarderAsync();
            sauvegarderAsync.setResultSearchActivity(resultSearchActivity);
            sauvegarderAsync.execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSearchActivity getResultSearchActivity() {
        return resultSearchActivity;
    }

    public void setResultSearchActivity(ResultSearchActivity resultSearchActivity) {
        this.resultSearchActivity = resultSearchActivity;
    }

    public RechercheAvancee getCurrentRechercheAvancee() {
        return currentRechercheAvancee;
    }

    public void setCurrentRechercheAvancee(RechercheAvancee currentRechercheAvancee) {
        this.currentRechercheAvancee = currentRechercheAvancee;
    }
}
