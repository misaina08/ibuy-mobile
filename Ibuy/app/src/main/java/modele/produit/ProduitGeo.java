package modele.produit;

/**
 * Created by misa on 8/29/2017.
 */

public class ProduitGeo extends ProduitView {
    private String duration;
    private Double distance;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
