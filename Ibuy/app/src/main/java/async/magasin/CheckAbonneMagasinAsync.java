package async.magasin;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.ibuy.misa.ibuy.FicheMagasinActivity;
import com.ibuy.misa.ibuy.R;

import modele.Client;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class CheckAbonneMagasinAsync extends AsyncTask<Integer, Void, String> {
    FicheMagasinActivity ficheMagasinActivity;

    @Override
    protected String doInBackground(Integer... params) {
        try {
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer() + "/clients/" + c.getId() + "/estabonne/" + params[0];
            WSRequestModele requestModele = new WSRequestModele();
            String res = requestModele.getContent(url);
            System.out.println("______res "+res);
            if (res.compareTo("0")==0) {
                return null;
            }else{
                return "ok";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String res) {
        if (res != null) {
            ficheMagasinActivity.getbAbonner().setTextColor(ficheMagasinActivity.getResources().getColor(R.color.color_orange));
            ficheMagasinActivity.getbAbonner().setText("Abonné");
            Drawable iconAbonne = ficheMagasinActivity.getResources().getDrawable(R.drawable.ic_abonne);
            ficheMagasinActivity.getbAbonner().setCompoundDrawablesWithIntrinsicBounds(null, iconAbonne, null, null);
            ficheMagasinActivity.isAbonne = true;
        } else{
            Drawable iconAbonne = ficheMagasinActivity.getResources().getDrawable(R.drawable.ic_favorite_filled);
            ficheMagasinActivity.getbAbonner().setCompoundDrawablesWithIntrinsicBounds(null, iconAbonne, null, null);
            ficheMagasinActivity.isAbonne = false;
            ficheMagasinActivity.getbAbonner().setText("S'abonner");
            ficheMagasinActivity.getbAbonner().setTextColor(ficheMagasinActivity.getResources().getColor(R.color.cardview_dark_background));
        }
    }

    public FicheMagasinActivity getFicheMagasinActivity() {
        return ficheMagasinActivity;
    }

    public void setFicheMagasinActivity(FicheMagasinActivity ficheMagasinActivity) {
        this.ficheMagasinActivity = ficheMagasinActivity;
    }
}
