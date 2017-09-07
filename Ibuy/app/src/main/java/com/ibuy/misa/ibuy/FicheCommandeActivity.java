package com.ibuy.misa.ibuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import async.achat.ProduitsCommandeAsync;

public class FicheCommandeActivity extends AppCompatActivity {
    private TextView tTotal;
    private Integer idcommande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_commande);

        getSupportActionBar().setTitle("Commande");

        Bundle bundle = getIntent().getExtras();
        idcommande = bundle.getInt("idcommande");

        tTotal = (TextView) findViewById(R.id.tTotal);

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fiche_produit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.close_fiche) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initData() {
        Integer[] params = {idcommande};
        ProduitsCommandeAsync produitsCommandeAsync = new ProduitsCommandeAsync();
        produitsCommandeAsync.setFicheCommandeActivity(this);
        produitsCommandeAsync.execute(params);
    }

    public TextView gettTotal() {
        return tTotal;
    }

    public void settTotal(TextView tTotal) {
        this.tTotal = tTotal;
    }
}
