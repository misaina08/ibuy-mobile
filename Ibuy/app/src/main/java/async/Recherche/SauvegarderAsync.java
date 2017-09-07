package async.Recherche;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.ResultSearchActivity;

import modele.RechercheAvancee;
import modele.ResultWS;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/24/2017.
 */

public class SauvegarderAsync extends AsyncTask<RechercheAvancee, Void, ResultWS> {
    ResultSearchActivity resultSearchActivity;
    @Override
    protected ResultWS doInBackground(RechercheAvancee... params) {
        try {
            String url = WSUtil.getUrlServer() + "/recherche/save";
            WSRequestModele requestModele = new WSRequestModele();
            String result = requestModele.post(url, params[0]);
            Gson gson = new Gson();
            ResultWS resultWS = gson.fromJson(result, ResultWS.class);
            return resultWS;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResultWS resultWS) {
        if (resultWS != null) {
            Toast.makeText(resultSearchActivity.getApplicationContext(), "Recherche sauvegardée", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(resultSearchActivity.getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }
    }

    public ResultSearchActivity getResultSearchActivity() {
        return resultSearchActivity;
    }

    public void setResultSearchActivity(ResultSearchActivity resultSearchActivity) {
        this.resultSearchActivity = resultSearchActivity;
    }
}
