package utilitaire;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by misa on 8/12/2017.
 */

public class LocationUtilitaire {
    private Coordonnee location;
    public Coordonnee getLastLocation(Activity activity) {
        location = new Coordonnee();
        final Coordonnee res = new Coordonnee();
        FusedLocationProviderClient mFusedLocationClient;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            res.setLatitude(location.getLatitude());
                            res.setLongitude(location.getLongitude());
                            location.setLatitude(location.getLatitude());
                            location.setLongitude(location.getLongitude());

                        }
                    }
                });
        System.out.println("lat long = " + res.getLatitude() + " - " + res.getLongitude());
        return res;
    }

    public Coordonnee getLocation() {
        return location;
    }

    public void setLocation(Coordonnee location) {
        this.location = location;
    }
}
