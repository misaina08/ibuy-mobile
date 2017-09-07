package async;

import android.os.AsyncTask;

import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/27/2017.
 */

public class CheckNotificationEvtProcheAsync extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            if (SessionManager.getClientConnected() != null) {
                String url = WSUtil.getUrlServer() + "/clients/" + SessionManager.getClientConnected().getId() + "/evenements/proches";
                WSRequestModele request = new WSRequestModele();
                request.getContent(url);
            }
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            System.out.println("not null");
        } else {
            System.out.println("null");
        }
    }
}
