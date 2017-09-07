package async.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListEvtClientAdapter;
import modele.Client;
import modele.EvenementClient;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/26/2017.
 */

public class MesEvenementsAsync extends AsyncTask<Void, Void, List<EvenementClient>> {
    Activity activity;
    @Override
    protected List<EvenementClient> doInBackground(Void... params) {
        try{
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer()+ "/clients/"+c.getId()+"/evenements";
            WSRequestModele request = new WSRequestModele();
            return (List<EvenementClient>)(List<?>)request.get(url, new EvenementClient());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<EvenementClient> evenements) {
        if(evenements != null){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListEvt);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
            ListEvtClientAdapter listEvtClientAdapter = new ListEvtClientAdapter();
            listEvtClientAdapter.setActivity(activity);
            listEvtClientAdapter.setEvenementClients(evenements);
            recyclerView.setAdapter(listEvtClientAdapter);
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
