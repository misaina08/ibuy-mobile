package async.magasin;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListActusMagasinAdapter;
import modele.ActusMagasin;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/29/2017.
 */

public class ActusAsync extends AsyncTask<Integer, Void, List<ActusMagasin>> {
    private Activity activity ;
    @Override
    protected List<ActusMagasin> doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/actus/magasin/" + params[0];
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ActusMagasin>) (List<?>) requestModele.get(url, new ActusMagasin());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ActusMagasin> actusMagasins) {

        if(actusMagasins!= null){
            final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recMeilleursPdt);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
            ListActusMagasinAdapter listProduitAdapter = new ListActusMagasinAdapter();
            listProduitAdapter.setActivity(activity);
            listProduitAdapter.setActusMagasins(actusMagasins);
            recyclerView.setAdapter(listProduitAdapter);
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
