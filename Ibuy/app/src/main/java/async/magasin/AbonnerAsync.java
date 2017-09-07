package async.magasin;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheMagasinActivity;

import modele.FavoriView;
import modele.ResultWS;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class AbonnerAsync extends AsyncTask<Integer, Void, ResultWS> {
    FicheMagasinActivity activity;
    @Override
    protected ResultWS doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer()+"/magasins/abonner";
            WSRequestModele requestModele  = new WSRequestModele();
            SessionManager sessionManager = new SessionManager(activity);
            FavoriView favori = new FavoriView();
            favori.setIdMagasin(params[0]);
            favori.setIdClient(SessionManager.getClientConnected().getId());
            String res = requestModele.post(url , favori);
            Gson gson = new Gson();
            ResultWS result = gson.fromJson(res, ResultWS.class);
            if(result.getRes() == 1){
                return result;
            } else{
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected  void onPostExecute(ResultWS resultWS){
        if(resultWS != null){
            Toast.makeText(activity.getApplicationContext(), "Vous êtes maintenant abonnés à cet magasin", Toast.LENGTH_SHORT).show();
            activity.checkAbonne();
        } else{
            Toast.makeText(activity.getApplicationContext(), "Vous êtes déjà abonnés à cet magasin", Toast.LENGTH_SHORT).show();
        }
    }

    public FicheMagasinActivity getActivity() {
        return activity;
    }

    public void setActivity(FicheMagasinActivity activity) {
        this.activity = activity;
    }
}
