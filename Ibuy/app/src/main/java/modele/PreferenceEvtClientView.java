package modele;

/**
 * Created by misa on 8/27/2017.
 */

public class PreferenceEvtClientView extends BaseModele {
    private Integer id;

    private String motcle;

    private String magasin;

    private String categorie;

    private Float prixmin;

    private Float prixmax;

    private Integer idEvenementClient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotcle() {
        return motcle;
    }

    public void setMotcle(String motcle) {
        this.motcle = motcle;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Float getPrixmin() {
        return prixmin;
    }

    public void setPrixmin(Float prixmin) {
        this.prixmin = prixmin;
    }

    public Float getPrixmax() {
        return prixmax;
    }

    public void setPrixmax(Float prixmax) {
        this.prixmax = prixmax;
    }

    public Integer getIdEvenementClient() {
        return idEvenementClient;
    }

    public void setIdEvenementClient(Integer idEvenementClient) {
        this.idEvenementClient = idEvenementClient;
    }

}
