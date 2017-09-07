package modele;

import java.util.Date;

/**
 * Created by misa on 8/29/2017.
 */

public class ActusMagasin extends BaseModele {
    private String id;
    private String magasin;
    private Integer idmagasin;
    private String titre;
    private String contenu;
    private String resumeFil;
    private Date daty;
    private String heure;
    private String actionTarget;
    private String bundles;
    private String logoMagasin;

    public String getLogoMagasin() {
        return logoMagasin;
    }

    public void setLogoMagasin(String logoMagasin) {
        this.logoMagasin = logoMagasin;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResumeFil() {
        return resumeFil;
    }

    public void setResumeFil(String resumeFil) {
        this.resumeFil = resumeFil;
    }


    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public Integer getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Integer idmagasin) {
        this.idmagasin = idmagasin;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(String actionTarget) {
        this.actionTarget = actionTarget;
    }

    public String getBundles() {
        return bundles;
    }

    public void setBundles(String bundles) {
        this.bundles = bundles;
    }
}
