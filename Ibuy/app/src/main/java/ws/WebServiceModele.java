package ws;

import java.util.List;

import modele.BaseModele;

/**
 * Created by Misaina on 11/03/2017.
 */

public abstract class WebServiceModele {
    public abstract List<BaseModele> get(String url, BaseModele baseModele) throws Exception;

    public abstract BaseModele getOne(String url, BaseModele baseModele) throws Exception;

    public abstract String post(String url, BaseModele baseModele) throws Exception;

    public abstract String postList(String url, List<BaseModele> listeBaseModele) throws Exception;

    public abstract void put(String url, BaseModele baseModele) throws Exception;

    public abstract void delete(String url, BaseModele baseModele) throws Exception;

    public abstract String getContent(String url) throws Exception;
}
