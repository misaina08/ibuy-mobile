package async.produit;

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
 * Created by misa on 9/2/2017.
 */

public class ListSouhaitAsync extends AsyncTask<Void, Void, List<ProduitView>> {
    private Activity activity;
    @Override
    protected List<ProduitView> doInBackground(Void... params) {
        try {
            String url = WSUtil.getUrlServer() + "/produits/souhaits/" + SessionManager.getClientConnected().getId();
            WSRequestModele requestModele = new WSRequestModele();
            List<ProduitView> res = (List<ProduitView>) (List<?>) requestModele.get(url, new ProduitView());
            return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> produits) {
        if(produits!= null){
            final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListSouhait);
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
