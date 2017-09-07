package com.ibuy.misa.ibuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import async.evenement.FicheEvenementAsync;

public class FicheEvenementActivity extends AppCompatActivity {
    Integer idevent;
    private TextView tTitre;
    private TextView tPeriode;
    private TextView tDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_evenement);
        Bundle bundle = getIntent().getExtras();
        idevent = new Integer(bundle.getString("idEvenement"));
        tTitre = (TextView) findViewById(R.id.tTitre);
        tPeriode = (TextView) findViewById(R.id.tPeriode);
        tDescription = (TextView) findViewById(R.id.tDescription);

        getSupportActionBar().setTitle("Evènement");

        Integer[] param = {idevent};
        FicheEvenementAsync evenementAsync = new FicheEvenementAsync();
        evenementAsync.setFicheEvenementActivity(this);
        evenementAsync.execute(param);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_standard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_close) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public TextView gettTitre() {
        return tTitre;
    }

    public void settTitre(TextView tTitre) {
        this.tTitre = tTitre;
    }

    public TextView gettPeriode() {
        return tPeriode;
    }

    public void settPeriode(TextView tPeriode) {
        this.tPeriode = tPeriode;
    }

    public TextView gettDescription() {
        return tDescription;
    }

    public void settDescription(TextView tDescription) {
        this.tDescription = tDescription;
    }
}
