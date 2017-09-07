package modele.notification;

import modele.BaseModele;

/**
 * Created by misa on 8/25/2017.
 */

public class ClientDevice extends BaseModele {
    private String id;
    private Integer idClient;
    private String token;

    public ClientDevice() {
    }

    public ClientDevice(String id, Integer idClient, String token) {
        this.id = id;
        this.idClient = idClient;
        this.token = token;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
