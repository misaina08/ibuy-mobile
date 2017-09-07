package com.ibuy.misa.ibuy;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import async.client.AjoutEvtAsync;
import modele.EvenementClient;
import services.ObjetsStatiques;
import services.SessionManager;
import utilitaire.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutEvtDialog extends DialogFragment {

    private EditText tDate;
    private EditText eLibelle;
    private EditText pMin;
    private EditText pMax;
    private EditText eMotCle;
    private Spinner spinMag;
    private Spinner spinCat;
    EvtClientFragment evtClientFragment;

    public AjoutEvtDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_ajout_evt_dialog, null);

        eLibelle = (EditText) view.findViewById(R.id.eLibelle);
        tDate = (EditText) view.findViewById(R.id.eDate);
        pMin = (EditText) view.findViewById(R.id.pMin);
        pMax = (EditText) view.findViewById(R.id.pMax);
        eMotCle = (EditText) view.findViewById(R.id.eMotCle);
        spinMag = (Spinner) view.findViewById(R.id.spinMag);
        spinCat = (Spinner) view.findViewById(R.id.spinCat);

        List<String> catList = new ArrayList<String>();
        catList.add("Tout");
        catList.addAll(ObjetsStatiques.getCategories());

        List<String> magList = new ArrayList<String>();
        magList.add("Tout");
        magList.addAll(ObjetsStatiques.getMagasins());

        ArrayAdapter<String> adapterSpinMag = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, magList);
        ArrayAdapter<String> adapterSpinCat = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, catList);

        spinMag.setAdapter(adapterSpinMag);
        spinCat.setAdapter(adapterSpinCat);

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
                        AjoutEvtDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void onValid() {
        try {
            EvenementClient evenementClient = new EvenementClient();
            evenementClient.setClientId(SessionManager.getClientConnected().getId());
            evenementClient.setDaty(Util.stringToDate(tDate.getText().toString()));
            evenementClient.setLibelle(eLibelle.getText().toString());
            evenementClient.setMotcle(eMotCle.getText().toString());
            if (spinCat.getSelectedItem().toString().compareTo("Tout") != 0) {
                evenementClient.setCategorie(spinCat.getSelectedItem().toString());
            }
            if (spinMag.getSelectedItem().toString().compareTo("Tout") != 0) {
                evenementClient.setMagasin(spinMag.getSelectedItem().toString());
            }
            if (pMax.getText().toString().isEmpty()) {
                evenementClient.setPrixmax(null);
            } else {
                evenementClient.setPrixmax(new Float(pMax.getText().toString()));
            }
            if (pMin.getText().toString().isEmpty()) {
                evenementClient.setPrixmin(null);
            } else {
                evenementClient.setPrixmin(new Float(pMin.getText().toString()));
            }

            EvenementClient[] params = new EvenementClient[1];
            params[0] = evenementClient;
            AjoutEvtAsync async = new AjoutEvtAsync();
            async.setEvtClientFragment(getEvtClientFragment());
            async.execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EvtClientFragment getEvtClientFragment() {
        return evtClientFragment;
    }

    public void setEvtClientFragment(EvtClientFragment evtClientFragment) {
        this.evtClientFragment = evtClientFragment;
    }
}
