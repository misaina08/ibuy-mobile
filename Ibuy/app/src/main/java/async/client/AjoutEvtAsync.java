package async.client;

import android.os.AsyncTask;

import com.ibuy.misa.ibuy.EvtClientFragment;

import async.CheckNotificationEvtProcheAsync;
import modele.Client;
import modele.EvenementClient;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/26/2017.
 */

public class AjoutEvtAsync extends AsyncTask<EvenementClient, Void, String> {
    EvtClientFragment evtClientFragment;

    @Override
    protected String doInBackground(EvenementClient... params) {
        try {
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer() + "/clients/" + c.getId() + "/evenements/ajouter";
            WSRequestModele r = new WSRequestModele();
            r.post(url, params[0]);
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String res) {
        if (res != null) {
            CheckNotificationEvtProcheAsync notificationAsync = new CheckNotificationEvtProcheAsync();
            notificationAsync.execute();
            evtClientFragment.init();
        }
    }

    public EvtClientFragment getEvtClientFragment() {
        return evtClientFragment;
    }

    public void setEvtClientFragment(EvtClientFragment evtClientFragment) {
        this.evtClientFragment = evtClientFragment;
    }
}
