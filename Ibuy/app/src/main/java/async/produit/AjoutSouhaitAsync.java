package async.produit;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import modele.produit.ProduitView;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/2/2017.
 */

public class AjoutSouhaitAsync extends AsyncTask<ProduitView, Void, String> {
    private Activity activity;
    @Override
    protected String doInBackground(ProduitView... params) {
        try {

            String url = WSUtil.getUrlServer() + "/produits/souhaits/" + SessionManager.getClientConnected().getId() + "/ajouter";
            WSRequestModele requestModele = new WSRequestModele();
            String res = requestModele.post(url, params[0]);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Toast.makeText(activity, "Ajout à la liste des souhaits fait en cours", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            Toast.makeText(activity, "Ajout à la liste des souhaits fait", Toast.LENGTH_SHORT).show();
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
