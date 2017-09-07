
package async.magasin;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListProduitAdapter;
import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class MeilleursProduitsAsync extends AsyncTask<Integer, Void, List<ProduitView>> {
    private Activity activity ;
    @Override
    protected List<ProduitView> doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/magasins/" + params[0] + "/meilleurs";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ProduitView>) (List<?>) requestModele.get(url, new ProduitView());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> produits) {
        if(produits!= null){
            final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recMeilleursPdt);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
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
