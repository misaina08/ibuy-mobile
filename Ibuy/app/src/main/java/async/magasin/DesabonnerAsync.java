package async.magasin;

import android.os.AsyncTask;
import android.widget.Toast;

import com.ibuy.misa.ibuy.FicheMagasinActivity;

import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class DesabonnerAsync extends AsyncTask<Integer, Void, String> {
    FicheMagasinActivity activity;
    @Override
    protected String doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer()+"/magasins/desabonner/"+SessionManager.getClientConnected().getId()+"/"+params[0];
            WSRequestModele requestModele  = new WSRequestModele();
            String res = requestModele.getContent(url);
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected  void onPostExecute(String resultWS){
        if(resultWS != null){
            Toast.makeText(activity.getApplicationContext(), "Vous êtes désabonnés à cet magasin", Toast.LENGTH_SHORT).show();
            activity.checkAbonne();
        } else{
            Toast.makeText(activity.getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    public FicheMagasinActivity getActivity() {
        return activity;
    }

    public void setActivity(FicheMagasinActivity activity) {
        this.activity = activity;
    }
}
