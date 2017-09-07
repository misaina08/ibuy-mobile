package modele;

/**
 * Created by misa on 8/30/2017.
 */

public class SuggestProduitRecherche extends BaseModele {
    private String id;
    private String idRecherche;
    private String produitJson;

    public String getProduitJson() {
        return produitJson;
    }

    public void setProduitJson(String produitJson) {
        this.produitJson = produitJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRecherche() {
        return idRecherche;
    }

    public void setIdRecherche(String idRecherche) {
        this.idRecherche = idRecherche;
    }
}
