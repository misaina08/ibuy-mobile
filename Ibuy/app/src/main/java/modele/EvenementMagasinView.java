package modele;

import java.util.Date;

/**
 * Created by misa on 8/29/2017.
 */

public class EvenementMagasinView extends BaseModele {
    private Integer id;
    private Date datydebut;
    private Date datyfin;
    private String libelle;
    private String description;
    private String idMagasin;
    private String magasin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatydebut() {
        return datydebut;
    }

    public void setDatydebut(Date datydebut) {
        this.datydebut = datydebut;
    }

    public Date getDatyfin() {
        return datyfin;
    }

    public void setDatyfin(Date datyfin) {
        this.datyfin = datyfin;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(String idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }
}
