package com.ibuy.misa.ibuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import async.magasin.ProduitsCategorieAsync;

public class ProduitsMagasinActivity extends AppCompatActivity {
    Integer idmagasin, idcategorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits_magasin);

        Bundle bundle = getIntent().getExtras();
        idmagasin = bundle.getInt("idmagasin");
        idcategorie = bundle.getInt("idcategorie");

        initDataProduits();
    }

    public void initDataProduits(){
        Integer[] params = {idmagasin, idcategorie};
        ProduitsCategorieAsync async = new ProduitsCategorieAsync();
        async.setActivity(this);
        async.execute(params);
    }
}
