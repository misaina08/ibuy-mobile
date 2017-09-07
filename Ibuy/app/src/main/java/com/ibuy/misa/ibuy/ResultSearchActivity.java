package com.ibuy.misa.ibuy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import async.Recherche.ProduitsAproximiteAsync;
import async.Recherche.RechercherAsync;
import modele.RechercheAvancee;
import modele.produit.ProduitView;

public class ResultSearchActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener, View.OnClickListener {
    MaterialSearchView searchView;
    private String queryFromPrevious;
    TextView tAucun;
    private RelativeLayout progressContent;
    FloatingActionButton fabAProximite;
    private List<ProduitView> resutlat;
    ResultSearchActivity thisActivity;
    RechercheAvancee currentRechercheAvancee;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        thisActivity = this;
        Bundle bundle = getIntent().getExtras();
        queryFromPrevious = bundle.getString("query");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (bundle.getString("recherche") != null) {
            Gson gson = new Gson();
            currentRechercheAvancee = gson.fromJson(bundle.getString("recherche"), RechercheAvancee.class);
            rechercherAvancee(currentRechercheAvancee);
        } else {
            rechercher(queryFromPrevious);
        }

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        progressContent = (RelativeLayout) findViewById(R.id.progressContent);
        recyclerView = (RecyclerView) findViewById(R.id.recResult);

        searchView.setOnQueryTextListener(this);

        tAucun = (TextView) findViewById(R.id.tAucun);
        fabAProximite = (FloatingActionButton) findViewById(R.id.fabAProximite);
        fabAProximite.setOnClickListener(this);
    }

    public void rechercher(String query) {
        System.out.println("__________"+query);

        RechercheAvancee rechercheAvancee = new RechercheAvancee();
        rechercheAvancee.setCategorie(null);
        rechercheAvancee.setMagasin(null);
        rechercheAvancee.setMotCle(query);
        rechercheAvancee.setPrixMax(null);
        rechercheAvancee.setPrixMin(null);

        RechercheAvancee[] params = {rechercheAvancee};

        RechercherAsync rechercherAsync = new RechercherAsync();
        rechercherAsync.setAffichage(0);
        rechercherAsync.setResultSearchActivity(this);
        rechercherAsync.execute(params);
        getSupportActionBar().setTitle("Resultats : " + query);
        queryFromPrevious = query;
    }

    public void rechercherAvancee(RechercheAvancee ra) {
        RechercheAvancee rechercheAvancee = ra;
        RechercheAvancee[] params = {rechercheAvancee};
        RechercherAsync rechercherAsync = new RechercherAsync();
        rechercherAsync.setAffichage(0);
        rechercherAsync.setResultSearchActivity(this);
        rechercherAsync.execute(params);
        this.setCurrentRechercheAvancee(ra);
        getSupportActionBar().setTitle("Resultats : " + ra.getMotCle());

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        rechercher(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem rechercheAvancee = menu.findItem(R.id.action_recherche_avancee);
        rechercheAvancee.setVisible(true);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            this.finish();
        }
        if (id == R.id.action_recherche_avancee) {
            CriteresRechercheFragment amPopFragment = new CriteresRechercheFragment();
            System.out.println("currentrechercheavancee " + currentRechercheAvancee);
            amPopFragment.setCurrentRechercheAvancee(currentRechercheAvancee);
            amPopFragment.setResultSearchActivity(this);
            amPopFragment.show(this.getFragmentManager(), "Recherche avancée popup");
        }
        return super.onOptionsItemSelected(item);
    }

    public String getQueryFromPrevious() {
        return queryFromPrevious;
    }

    public void setQueryFromPrevious(String queryFromPrevious) {
        this.queryFromPrevious = queryFromPrevious;
    }

    public TextView gettAucun() {
        return tAucun;
    }

    public void settAucun(TextView tAucun) {
        this.tAucun = tAucun;
    }

    public List<ProduitView> getResutlat() {
        return resutlat;
    }

    public void setResutlat(List<ProduitView> resutlat) {
        this.resutlat = resutlat;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fabAProximite) {
            progressContent.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            FusedLocationProviderClient mFusedLocationClient;
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("no permissions");
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
//                                    Coordonnees de la position actuelle
                                Double[] rayonParam = {new Double(5000)};
                                ProduitsAproximiteAsync produitsAproximiteAsync = new ProduitsAproximiteAsync();
                                produitsAproximiteAsync.setProduits(resutlat);
                                produitsAproximiteAsync.setResultSearchActivity(thisActivity);
                                produitsAproximiteAsync.setCurrentPos(new LatLng(location.getLatitude(), location.getLongitude()));
                                produitsAproximiteAsync.execute(rayonParam);
                            }
                        }

                    });
        }
    }

    public RechercheAvancee getCurrentRechercheAvancee() {
        return currentRechercheAvancee;
    }

    public void setCurrentRechercheAvancee(RechercheAvancee currentRechercheAvancee) {
        this.currentRechercheAvancee = currentRechercheAvancee;
    }

    public RelativeLayout getProgressContent() {
        return progressContent;
    }

    public void setProgressContent(RelativeLayout progressContent) {
        this.progressContent = progressContent;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
