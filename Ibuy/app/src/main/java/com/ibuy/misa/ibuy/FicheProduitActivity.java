package com.ibuy.misa.ibuy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import async.produit.FicheProduitAsync;
import async.produit.ProduitsSimilairesAsync;
import modele.Magasin;
import modele.produit.ProduitView;
import sqlite.PanierDao;
import utilitaire.Coordonnee;


public class FicheProduitActivity extends AppCompatActivity implements View.OnClickListener {
    private ProduitView produit;
    private ImageView iPhoto;
    //    private TextView tDesignation;
    private TextView tMagasin;
    //    private TextView tCategorie;
    private TextView tPrix;
    private TextView tDescription;

    boolean isFabOpen = false;
    FloatingActionButton optionsFab;
    FloatingActionButton magasinFab;
    FloatingActionButton commanderFab;
    TextView textMagasinFab;
    TextView textCommandFab;
    public Animation fab_open, fab_close, rotate_forward, rotate_backward;

    FicheProduitActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_produit);

        thisActivity = this;

        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        produit = gson.fromJson(bundle.getString("produitJson"), ProduitView.class);

        iPhoto = (ImageView) findViewById(R.id.iPhoto);
//        tDesignation = (TextView) findViewById(R.id.tDesignation);
        tMagasin = (TextView) findViewById(R.id.tMagasin);
        tMagasin.setOnClickListener(this);
        tDescription = (TextView) findViewById(R.id.tDescription);
//        tCategorie = (TextView) findViewById(R.id.tCategorie);
        tPrix = (TextView) findViewById(R.id.tPrix);
        getSupportActionBar().setTitle(produit.getDesignation());
        initData();
        initFabs();
    }

    public void initData() {
        Integer[] params = new Integer[1];
        params[0] = produit.getId();
        FicheProduitAsync async = new FicheProduitAsync();
        async.setFicheProduitActivity(this);
        async.execute(params);

        ProduitsSimilairesAsync asyncSimilaires = new ProduitsSimilairesAsync();
        asyncSimilaires.setActivity(this);
        Integer[] paramsSimilaires = new Integer[1];
        paramsSimilaires[0] = produit.getCategorieId();
        asyncSimilaires.execute(paramsSimilaires);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fiche_produit, menu);

        return true;
    }

    public void initFabs() {
        optionsFab = (FloatingActionButton) findViewById(R.id.options_fab);
        textMagasinFab = (TextView) findViewById(R.id.textMagasinFab);
        textCommandFab = (TextView) findViewById(R.id.textFabCommand);
        magasinFab = (FloatingActionButton) findViewById(R.id.magasin_fab);
        commanderFab = (FloatingActionButton) findViewById(R.id.commander_fab);

        fab_open = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.rotate_backward);
        optionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        magasinFab.setOnClickListener(this);
        commanderFab.setOnClickListener(this);
    }

    public void animateFAB() {

        if (isFabOpen) {

            optionsFab.startAnimation(rotate_backward);
            magasinFab.startAnimation(fab_close);
            commanderFab.startAnimation(fab_close);
            textMagasinFab.startAnimation(fab_close);
            textCommandFab.startAnimation(fab_close);

            magasinFab.setClickable(false);
            commanderFab.setClickable(false);

            isFabOpen = false;

        } else {

            optionsFab.startAnimation(rotate_forward);
            magasinFab.startAnimation(fab_open);
            commanderFab.startAnimation(fab_open);
            textMagasinFab.startAnimation(fab_open);
            textCommandFab.startAnimation(fab_open);
            magasinFab.setClickable(true);
            commanderFab.setClickable(true);
            isFabOpen = true;
        }
    }


    /**
     * Evenement à appeler lorsqu'on clique les menus en haut
     *
     * @param item
     * @return
     */
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        System.out.println(id);
        if (id == R.id.magasin_fab) {
            FusedLocationProviderClient mFusedLocationClient;
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("no permissions");
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Coordonnee currPos = new Coordonnee();
//                                    Coordonnees de la position actuelle
                                currPos.setLatitude(location.getLatitude());
                                currPos.setLongitude(location.getLongitude());
                                Intent intent = new Intent(thisActivity, MapActivity.class);
                                Gson gson = new Gson();
                                intent.putExtra("currPos", gson.toJson(currPos));
                                Magasin magasin = new Magasin();
                                magasin.setId(produit.getMagasinId());
                                magasin.setNom(produit.getMagasin());
                                System.out.println("longitude "+produit.getLatitude()+" long "+produit.getLongitude());
                                magasin.setLatitude(produit.getLatitude());
                                magasin.setLongitude(produit.getLongitude());
                                intent.putExtra("magasin", gson.toJson(magasin));
                                thisActivity.startActivity(intent);
                            }
                        }
                    });
        } else if (id == R.id.commander_fab) {
            try {
                PanierDao panierDao = new PanierDao(this);
                panierDao.ajout(produit);
                Toast.makeText(this, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Une erreur a survenue", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.tMagasin) {
            Intent intent = new Intent(this, FicheMagasinActivity.class);
            intent.putExtra("idMagasin", produit.getMagasinId());
            startActivity(intent);
        }
    }

    public ImageView getiPhoto() {
        return iPhoto;
    }

    public void setiPhoto(ImageView iPhoto) {
        this.iPhoto = iPhoto;
    }

//    public TextView gettDesignation() {
//        return tDesignation;
//    }
//
//    public void settDesignation(TextView tDesignation) {
//        this.tDesignation = tDesignation;
//    }

    public TextView gettMagasin() {
        return tMagasin;
    }

    public void settMagasin(TextView tMagasin) {
        this.tMagasin = tMagasin;
    }

//    public TextView gettCategorie() {
//        return tCategorie;
//    }
//
//    public void settCategorie(TextView tCategorie) {
//        this.tCategorie = tCategorie;
//    }

    public TextView gettPrix() {
        return tPrix;
    }

    public void settPrix(TextView tPrix) {
        this.tPrix = tPrix;
    }

    public TextView gettDescription() {
        return tDescription;
    }

    public void settDescription(TextView tDescription) {
        this.tDescription = tDescription;
    }

    public ProduitView getProduit() {
        return produit;
    }

    public void setProduit(ProduitView produit) {
        this.produit = produit;
    }
}
