package async;

import android.os.AsyncTask;

import modele.Client;
import modele.notification.ClientDevice;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/25/2017.
 */

public class RegistryTokenAsync extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            String url = WSUtil.getUrlServer() + "/notifications/device/save";
            WSRequestModele requestModele = new WSRequestModele();
            Client c = SessionManager.getClientConnected();
            ClientDevice clientDevice = new ClientDevice();
            clientDevice.setIdClient(c.getId());
            clientDevice.setToken(params[0]);
            requestModele.post(url, clientDevice);
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(s!= null){
            System.out.println("inserted");
        }else{
            System.out.println("not inserted");
        }
    }

}
