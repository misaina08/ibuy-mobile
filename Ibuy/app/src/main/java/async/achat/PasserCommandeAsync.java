package async.achat;

import android.app.AlertDialog;
import android.os.AsyncTask;

import com.ibuy.misa.ibuy.PanierFragment;

import java.util.List;

import modele.BaseModele;
import modele.achat.ProduitCommandeView;
import services.SessionManager;
import sqlite.PanierDao;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/4/2017.
 */

public class PasserCommandeAsync extends AsyncTask<List<ProduitCommandeView>, Void, String> {
    private PanierFragment panierFragment;
    public AlertDialog alertDialog;
    @Override
    protected String doInBackground(List<ProduitCommandeView>... params) {
        try {
            String url = WSUtil.getUrlServer() + "/achat/passercommande/" + SessionManager.getClientConnected().getId()
                    + "/" + panierFragment.getAdresseLivraison() + "/" + panierFragment.getLongitude() + "/" + panierFragment.getLatitude();
            WSRequestModele requestModele = new WSRequestModele();
            requestModele.postList(url, (List<BaseModele>) (List<?>) params[0]);
            return "ok";

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(String s) {
        try {
            if (s != null) {
                // on vide le panier
                PanierDao panierDao = new PanierDao(panierFragment.getActivity());
                panierDao.vider();
                panierFragment.progressDialog.dismiss();
                panierFragment.initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
