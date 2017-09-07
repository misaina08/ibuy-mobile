package async.produit;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/2/2017.
 */

public class RetirerSouhaitAsync extends AsyncTask<Integer, Void, String> {
    private Activity activity;
    @Override
    protected String doInBackground(Integer... params) {
        try {

            String url = WSUtil.getUrlServer() + "/produits/souhaits/" + SessionManager.getClientConnected().getId() + "/retirer/"+params[0];
            WSRequestModele requestModele = new WSRequestModele();
            String res = requestModele.getContent(url);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Toast.makeText(activity, "Retrait de la liste des souhaits en cours...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            Toast.makeText(activity, "Retrait de la liste des souhaits fait", Toast.LENGTH_SHORT).show();
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
