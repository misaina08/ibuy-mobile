package modele;

import java.util.Date;

/**
 * Created by misa on 8/26/2017.
 */

public class EvenementClient extends BaseModele {
    private Integer id;
    private Date daty;
    private String libelle;
    private Integer clientId;
    private String magasin;
    private String motcle;
    private String categorie;

    private Float prixmin;

    private Float prixmax;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
