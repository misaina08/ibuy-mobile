package com.ibuy.misa.ibuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import async.Recherche.FicheAsync;
import modele.RechercheAvancee;

public class FicheRechercheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_recherche);

        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        RechercheAvancee rechercheAvancee = gson.fromJson(bundle.getString("rechercheJson"), RechercheAvancee.class);

        getSupportActionBar().setTitle("Suggestions pour " + rechercheAvancee.getMotCle());

        String[] idRecherche = {rechercheAvancee.getId()};
        FicheAsync ficheAsync = new FicheAsync();
        ficheAsync.setActivity(this);
        ficheAsync.execute(idRecherche);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_standard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_close) {
            this.finish();
        }
        return true;
    }
}
