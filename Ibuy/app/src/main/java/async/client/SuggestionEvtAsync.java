package async.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListProduitAdapter;
import modele.produit.ProduitView;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/27/2017.
 */

public class SuggestionEvtAsync extends AsyncTask<Integer, Void, List<ProduitView>> {
    Activity activity;
    @Override
    protected List<ProduitView> doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/clients/" + SessionManager.getClientConnected().getId() + "/evenements/" + params[0] + "/suggestions";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ProduitView>) (List<?>) requestModele.get(url, new ProduitView());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> produitViews) {
        if (produitViews != null) {
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListSuggestion);
            recyclerView.setLayoutManager(new GridLayoutManager(activity.getBaseContext(), 3));
            ListProduitAdapter listProduitAdapter = new ListProduitAdapter();
            listProduitAdapter.setActivity(activity);
            listProduitAdapter.setListeProduit(produitViews);
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
