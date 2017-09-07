package com.ibuy.misa.ibuy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import async.magasin.AbonnerAsync;
import async.magasin.ActusAsync;
import async.magasin.CategoriesMagasinAsync;
import async.magasin.CheckAbonneMagasinAsync;
import async.magasin.DesabonnerAsync;
import async.magasin.InfoMagasinAsync;
import async.magasin.MeilleursProduitsAsync;
import modele.Magasin;
import utilitaire.Coordonnee;

public class FicheMagasinActivity extends AppCompatActivity implements View.OnClickListener {
    FicheMagasinActivity thisActivity;
    Integer idmagasin;
    private Magasin magasin;
    public SwipeRefreshLayout swipeRefresh;

    private ImageView iPhoto;

    private TextView tAdresse;
    private TextView tContact;
    private TextView tDescription;
    private LinearLayout contentCategories;
    private TextView tNbSuivi;
    private Button bItineraire;
    private Button bAppeler;
    private Button bAbonner;
    private Button bSendMail;
    private ImageView logoMagasin;

    public boolean isAbonne = false;

    Coordonnee currPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fiche_magasin);
        currPos = new Coordonnee();
        Bundle bundle = getIntent().getExtras();
        idmagasin = bundle.getInt("idMagasin");
        thisActivity = this;
        initComponents();
        swipeRefresh.setRefreshing(true);

        initAllData();
    }

    public void initComponents() {
        iPhoto = (ImageView) findViewById(R.id.iPhoto);
        tAdresse = (TextView) findViewById(R.id.tAdresse);
        tContact = (TextView) findViewById(R.id.tContact);
        tDescription = (TextView) findViewById(R.id.tDescription);
        contentCategories = (LinearLayout) findViewById(R.id.contentCategories);
        tNbSuivi = (TextView) findViewById(R.id.tNbSuivi);
        bItineraire = (Button) findViewById(R.id.bItineraire);
        bAppeler = (Button) findViewById(R.id.bAppeler);
        bAbonner = (Button) findViewById(R.id.bAbonner);
        bSendMail = (Button) findViewById(R.id.bSendMail);
        logoMagasin = (ImageView) findViewById(R.id.logoMagasin);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initAllData();
            }
        });

        bItineraire.setOnClickListener(this);
        bAppeler.setOnClickListener(this);
        bAbonner.setOnClickListener(this);
        bSendMail.setOnClickListener(this);
    }

    public void initAllData() {
        initInfo();
//        initMeilleursProduits();
        initActus();
        initCategories();
        checkAbonne();
        swipeRefresh.setRefreshing(false);
    }

    public void checkAbonne() {
        Integer[] params = new Integer[1];
        params[0] = idmagasin;
        CheckAbonneMagasinAsync checkAbonneMagasinAsync = new CheckAbonneMagasinAsync();
        checkAbonneMagasinAsync.setFicheMagasinActivity(this);
        checkAbonneMagasinAsync.execute(params);
    }


    public void initInfo() {
        Integer[] params = new Integer[1];
        params[0] = idmagasin;
        InfoMagasinAsync infoMagasinAsync = new InfoMagasinAsync();
        infoMagasinAsync.setFicheMagasinActivity(this);
        infoMagasinAsync.execute(params);
    }

    public void initMeilleursProduits() {
        MeilleursProduitsAsync meilleursProduitsAsync = new MeilleursProduitsAsync();
        meilleursProduitsAsync.setActivity(this);
        Integer[] params = new Integer[1];
        params[0] = idmagasin;
        meilleursProduitsAsync.execute(params);
    }

    public void initActus() {
        ActusAsync meilleursProduitsAsync = new ActusAsync();
        meilleursProduitsAsync.setActivity(this);
        Integer[] params = new Integer[1];
        params[0] = idmagasin;
        meilleursProduitsAsync.execute(params);
    }

    public void initCategories() {
        CategoriesMagasinAsync categoriesMagasinAsync = new CategoriesMagasinAsync();
        categoriesMagasinAsync.setFicheMagasinActivity(this);
        Integer[] params = new Integer[1];
        params[0] = idmagasin;
        categoriesMagasinAsync.execute(params);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Integer[] idmagasinParam = {idmagasin};
        if (id == R.id.bAbonner) {
            if(isAbonne) {
                DesabonnerAsync desabonnerAsync = new DesabonnerAsync();
                desabonnerAsync.setActivity(this);
                desabonnerAsync.execute(idmagasinParam);
            } else{
                AbonnerAsync abonnerAsync = new AbonnerAsync();
                abonnerAsync.setActivity(this);
                abonnerAsync.execute(idmagasinParam);

            }

        } else if (id == R.id.bAppeler) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + magasin.getNumero()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            this.startActivity(callIntent);
        } else if (id == R.id.bItineraire) {
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
//                                    Coordonnees de la position actuelle
                                currPos.setLatitude(location.getLatitude());
                                currPos.setLongitude(location.getLongitude());
                                Intent intent = new Intent(thisActivity, MapActivity.class);
                                Gson gson = new Gson();
                                intent.putExtra("currPos", gson.toJson(currPos));
                                intent.putExtra("magasin", gson.toJson(magasin));
                                thisActivity.startActivity(intent);
//                                Geocoder geocoder = new Geocoder(thisActivity, Locale.getDefault());
//                                List<Address> addresses = null;
//                                try {
//                                    addresses = geocoder.getFromLocation(
//                                            location.getLatitude(),
//                                            location.getLongitude(),
//                                            // In this sample, get just a single address.
//                                            1);
////                                         nom du lieu : Rue, Ville, Pays
//                                    String cityName = addresses.get(0).getAddressLine(0);
//                                    eLieu.setText(cityName);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }
                    });

        } else if (id == R.id.bSendMail) {

        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("back");
        finish();
    }

    public Integer getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Integer idmagasin) {
        this.idmagasin = idmagasin;
    }

    public ImageView getiPhoto() {
        return iPhoto;
    }

    public void setiPhoto(ImageView iPhoto) {
        this.iPhoto = iPhoto;
    }

    public TextView gettAdresse() {
        return tAdresse;
    }

    public void settAdresse(TextView tAdresse) {
        this.tAdresse = tAdresse;
    }

    public TextView gettContact() {
        return tContact;
    }

    public void settContact(TextView tContact) {
        this.tContact = tContact;
    }

    public TextView gettDescription() {
        return tDescription;
    }

    public void settDescription(TextView tDescription) {
        this.tDescription = tDescription;
    }

    public TextView gettNbSuivi() {
        return tNbSuivi;
    }

    public void settNbSuivi(TextView tNbSuivi) {
        this.tNbSuivi = tNbSuivi;
    }

    public Button getbItineraire() {
        return bItineraire;
    }

    public void setbItineraire(Button bItineraire) {
        this.bItineraire = bItineraire;
    }

    public Button getbAppeler() {
        return bAppeler;
    }

    public void setbAppeler(Button bAppeler) {
        this.bAppeler = bAppeler;
    }

    public Button getbAbonner() {
        return bAbonner;
    }

    public void setbAbonner(Button bAbonner) {
        this.bAbonner = bAbonner;
    }

    public Button getbSendMail() {
        return bSendMail;
    }

    public void setbSendMail(Button bSendMail) {
        this.bSendMail = bSendMail;
    }

    public LinearLayout getContentCategories() {
        return contentCategories;
    }

    public void setContentCategories(LinearLayout contentCategories) {
        this.contentCategories = contentCategories;
    }

    public ImageView getLogoMagasin() {
        return logoMagasin;
    }

    public void setLogoMagasin(ImageView logoMagasin) {
        this.logoMagasin = logoMagasin;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
}
