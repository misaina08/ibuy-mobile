package utilitaire;

/**
 * Created by misa on 8/12/2017.
 */

public class Coordonnee {
    private Double latitude;
    private Double longitude;

    public Coordonnee(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Coordonnee(){

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
