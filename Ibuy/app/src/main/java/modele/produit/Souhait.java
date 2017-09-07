package modele.produit;

import modele.BaseModele;

/**
 * Created by misa on 9/2/2017.
 */

public class Souhait extends BaseModele {
    private String id;
    private Integer idProduit;
    private Integer idClient;
    private String produitJson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getProduitJson() {
        return produitJson;
    }

    public void setProduitJson(String produitJson) {
        this.produitJson = produitJson;
    }

}
