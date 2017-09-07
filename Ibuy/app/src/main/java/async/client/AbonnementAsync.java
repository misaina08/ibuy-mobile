package async.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListProduitAdapter;
import modele.Client;
import modele.produit.ProduitView;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class AbonnementAsync extends AsyncTask<Void, Void, List<ProduitView>> {
    Activity activity;

    @Override
    protected List<ProduitView> doInBackground(Void... params) {
        try {
            SessionManager sessionManager = new SessionManager(activity);
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer() + "/clients/" + c.getId() + "/abonnements";
            WSRequestModele request = new WSRequestModele();
            return (List<ProduitView>) (List<?>) request.get(url, new ProduitView());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> produits) {
        if (produits != null) {
            DisplayMetrics displayMetrics = activity.getBaseContext().getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListPdtFavori);
            recyclerView.setLayoutManager(new GridLayoutManager(activity.getBaseContext(), 3));
            ListProduitAdapter listProduitAdapter = new ListProduitAdapter();
            listProduitAdapter.setActivity(activity);
            listProduitAdapter.setListeProduit(produits);
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
