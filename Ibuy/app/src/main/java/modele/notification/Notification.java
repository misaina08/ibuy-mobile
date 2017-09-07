package modele.notification;

import java.util.Date;

import modele.BaseModele;

/**
 * Created by misa on 8/25/2017.
 */

public class Notification extends BaseModele {
    private String id;
    private Date daty;
    private String heure;
    private String titre;
    private String content;
    private Integer idClient;
    private Integer vu;
    private String targetAction;
    private String bundles;

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getVu() {
        return vu;
    }

    public void setVu(Integer vu) {
        this.vu = vu;
    }

    public String getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(String targetAction) {
        this.targetAction = targetAction;
    }

    public String getBundles() {
        return bundles;
    }

    public void setBundles(String bundles) {
        this.bundles = bundles;
    }

}
