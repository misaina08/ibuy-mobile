package modele.sqlite;

import java.util.Date;

import modele.BaseModele;

/**
 * Created by misa on 9/4/2017.
 */

public class ProduitPanier extends BaseModele {
    private Integer id;
    private Date daty;
    private String produitJson;
    private Integer quantite;

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

    public String getProduitJson() {
        return produitJson;
    }

    public void setProduitJson(String produitJson) {
        this.produitJson = produitJson;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
