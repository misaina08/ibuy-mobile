package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import async.client.ConnexionAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnexionFragment extends Fragment implements View.OnClickListener{
    private EditText eLogin;
    private EditText ePass;
    private Button bConnect;

    public ConnexionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connexion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);

        eLogin = (EditText)getView().findViewById(R.id.eLogin);
        ePass = (EditText)getView().findViewById(R.id.ePass);
        bConnect = (Button)getView().findViewById(R.id.bConnect);

        bConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bConnect){
            String[] params = {eLogin.getText().toString(), ePass.getText().toString()};
            ConnexionAsync async = new ConnexionAsync();
            async.setActivity(this.getActivity());
            async.execute(params);
        }
    }
}
