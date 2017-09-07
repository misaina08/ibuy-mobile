package async.client;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ibuy.misa.ibuy.MainActivity;

import async.RegistryTokenAsync;
import fcm.FirebaseInitializer;
import modele.Client;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class ConnexionAsync extends AsyncTask<String, Void, Client> {
    Activity activity;

    @Override
    protected Client doInBackground(String... params) {
        try {
            String url = WSUtil.getUrlServer() + "/clients/connexion/" + params[0] + "/" + params[1];
            WSRequestModele requestModele = new WSRequestModele();
            System.out.println(params[0]);
            return (Client) requestModele.getOne(url, new Client());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Client client) {
        System.out.println(client.getNom());
        try {
            if (client != null) {

                SessionManager sessionManager = new SessionManager(activity.getBaseContext());
                sessionManager.createUserSession(client);
                Toast.makeText(activity, "Vous êtes connectés", Toast.LENGTH_SHORT).show();
                FirebaseInitializer firebaseInitializer = new FirebaseInitializer();
                firebaseInitializer.initFcm();

                RegistryTokenAsync registryTokenAsync = new RegistryTokenAsync();
                String[] paramsToken = {FirebaseInitializer.getCurrentToken()};
                registryTokenAsync.execute(paramsToken);

                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);


            } else {
                Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
