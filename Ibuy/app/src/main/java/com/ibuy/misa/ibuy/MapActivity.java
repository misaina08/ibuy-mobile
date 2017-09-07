package com.ibuy.misa.ibuy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

import modele.Magasin;
import utilitaire.Coordonnee;

public class MapActivity extends AppCompatActivity  implements OnMapReadyCallback, DirectionCallback {
    private GoogleMap mMap;
    LatLng latLngCurrentPos;
    LatLng latLngDest;
    Magasin magasin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        magasin = gson.fromJson(bundle.getString("magasin"), Magasin.class);
        Coordonnee curPos = gson.fromJson(bundle.getString("currPos"), Coordonnee.class);
        latLngCurrentPos = new LatLng(curPos.getLatitude(), curPos.getLongitude());
        latLngDest = new LatLng(magasin.getLatitude(), magasin.getLongitude());
        getSupportActionBar().setTitle("Itinéraire vers "+magasin.getNom());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        try {
//            Toast.makeText(this, "Success with status : " + direction.getStatus(), Toast.LENGTH_SHORT).show();
            if (direction.isOK()) {
                mMap.addMarker(new MarkerOptions().position(latLngCurrentPos).title("Moi"));
                mMap.addMarker(new MarkerOptions().position(latLngDest).title(magasin.getNom()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(30.0f));

                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 2, Color.RED));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngDest));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        try {
            Toast.makeText(this, "Faillure with status : " + t.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestDirection();
    }

    public void requestDirection() {
        try {
            Toast.makeText(this, "Itinéraire en cours...", Toast.LENGTH_SHORT).show();
            GoogleDirection.withServerKey("AIzaSyDKD4SS2pCLwr-bD1Xz_Ru0_7elsmHHMzU")
                    .from(latLngCurrentPos)
                    .to(latLngDest)
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
