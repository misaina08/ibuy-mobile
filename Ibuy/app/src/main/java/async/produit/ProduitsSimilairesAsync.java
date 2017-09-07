package async.produit;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListProduitAdapter;
import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/22/2017.
 */

public class ProduitsSimilairesAsync extends AsyncTask<Integer, Void, List<ProduitView>> {
    private Activity activity ;
    @Override
    protected List<ProduitView> doInBackground(Integer... params) {
        try{
            String url = WSUtil.getUrlServer()+"/produits/similaires/"+params[0];
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ProduitView>)(List<?>)requestModele.get(url, new ProduitView());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> produits){
        if(produits!= null){
            final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recProduitsSimilaires);
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
