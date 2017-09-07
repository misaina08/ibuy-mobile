package com.ibuy.misa.ibuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import async.client.SuggestionEvtAsync;
import modele.EvenementClient;

public class SuggestionEvtActivity extends AppCompatActivity {
    private EvenementClient evenementClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_evt);

        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        evenementClient = gson.fromJson(bundle.getString("evtJson"), EvenementClient.class);
        getSupportActionBar().setTitle("Suggestions pour l'évènement ");
        getSupportActionBar().setSubtitle(evenementClient.getLibelle());
        Integer[] params = {evenementClient.getId()};
        SuggestionEvtAsync async = new SuggestionEvtAsync();
        async.setActivity(this);
        async.execute(params);
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
}
