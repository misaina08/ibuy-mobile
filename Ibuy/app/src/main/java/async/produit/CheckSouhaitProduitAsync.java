package async.produit;

import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;

import com.ibuy.misa.ibuy.R;

import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/2/2017.
 */

public class CheckSouhaitProduitAsync extends AsyncTask<Integer, Void, String> {
    private PopupMenu popupMenu;
    @Override
    protected String doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/produits/souhaits/" + SessionManager.getClientConnected().getId() + "/estsouhaite/" + params[0];
            WSRequestModele requestModele = new WSRequestModele();
            String res = requestModele.getContent(url);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            System.out.println("ato am async "+s);
//            si déjà dans la liste des souhaits
            if (s.compareTo("1") == 0) {
                System.out.println("resultat1");
                popupMenu.getMenu().findItem(R.id.ajout_souhait).setVisible(false);

                popupMenu.getMenu().findItem(R.id.retirer_souhait).setVisible(true);

            }
//            sinon
            else {
                System.out.println("resultat2");
                popupMenu.getMenu().findItem(R.id.retirer_souhait).setVisible(false);

                popupMenu.getMenu().findItem(R.id.ajout_souhait).setVisible(true);

            }
            popupMenu.show();
        }
    }

    public PopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(PopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }
}
