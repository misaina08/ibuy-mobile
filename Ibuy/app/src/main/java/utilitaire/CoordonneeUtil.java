package utilitaire;


/**
 * Created by misa on 8/14/2017.
 */

public class CoordonneeUtil {
    public double distance(Coordonnee c1, Coordonnee c2) {
        return Math.sqrt(Math.pow((c2.getLatitude() - c1.getLatitude()), 2) + Math.pow((c2.getLongitude() - c1.getLongitude()), 2));
    }
}
