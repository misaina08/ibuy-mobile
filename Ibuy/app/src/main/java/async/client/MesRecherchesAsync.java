package async.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListRecherchesAdapter;
import modele.Client;
import modele.RechercheAvancee;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/26/2017.
 */

public class MesRecherchesAsync extends AsyncTask<Void, Void, List<RechercheAvancee>> {
    Activity activity;
    @Override
    protected List<RechercheAvancee> doInBackground(Void... params) {
        try{
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer()+ "/clients/"+c.getId()+"/recherches";
            WSRequestModele request = new WSRequestModele();
            return (List<RechercheAvancee>)(List<?>)request.get(url, new RechercheAvancee());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RechercheAvancee> rechercheAvancees) {
        if(rechercheAvancees != null){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListRecherche);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
            ListRecherchesAdapter listRecherchesAdapter = new ListRecherchesAdapter();
            listRecherchesAdapter.setActivity(activity);
            listRecherchesAdapter.setRecherches(rechercheAvancees);
            recyclerView.setAdapter(listRecherchesAdapter);
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
