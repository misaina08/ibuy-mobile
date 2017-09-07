package async;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.AccueilFragment;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.FilActusAdapter;
import modele.ActusMagasin;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/30/2017.
 */

public class FilActusAsync extends AsyncTask<Void, Void, List<ActusMagasin>> {
    private AccueilFragment accueilFragment;

    @Override
    protected List<ActusMagasin> doInBackground(Void... params) {
        try {
            String url = WSUtil.getUrlServer() + "/actus/fil";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ActusMagasin>) (List<?>) requestModele.get(url, new ActusMagasin());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        accueilFragment.swipeRefresh.setRefreshing(true);
    }
    @Override
    protected void onPostExecute(List<ActusMagasin> actusMagasins) {
        if (actusMagasins != null) {
            final RecyclerView recyclerView = (RecyclerView) accueilFragment.getView().findViewById(R.id.recAccueil);
            recyclerView.setLayoutManager(new LinearLayoutManager(accueilFragment.getActivity().getBaseContext()));
            FilActusAdapter listProduitAdapter = new FilActusAdapter();
            listProduitAdapter.setActivity(accueilFragment.getActivity());
            listProduitAdapter.setActusMagasins(actusMagasins);
            recyclerView.setAdapter(listProduitAdapter);
            accueilFragment.swipeRefresh.setRefreshing(false);
        }
    }

    public AccueilFragment getAccueilFragment() {
        return accueilFragment;
    }

    public void setAccueilFragment(AccueilFragment accueilFragment) {
        this.accueilFragment = accueilFragment;
    }
}
