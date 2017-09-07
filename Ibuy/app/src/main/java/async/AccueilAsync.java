package async;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.AccueilFragment;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListProduitAdapter;
import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/22/2017.
 */

public class AccueilAsync extends AsyncTask<Void, Void, List<ProduitView>>{
    private AccueilFragment accueilFragment;
    @Override
    protected List<ProduitView> doInBackground(Void... params) {
        try{
            String url = WSUtil.getUrlServer()+"/produits/accueil";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ProduitView>)(List<?>)requestModele.get(url, new ProduitView());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        accueilFragment.swipeRefresh.setRefreshing(true);
    }

    @Override
    protected void onPostExecute(List<ProduitView> liste){
        if (liste != null) {
            final RecyclerView recyclerView = (RecyclerView) accueilFragment.getView().findViewById(R.id.recAccueil);
            recyclerView.setLayoutManager(new LinearLayoutManager(accueilFragment.getActivity().getBaseContext()));
            ListProduitAdapter listProduitAdapter = new ListProduitAdapter();
            listProduitAdapter.setActivity(accueilFragment.getActivity());
            listProduitAdapter.setListeProduit(liste);
            recyclerView.setAdapter(listProduitAdapter);
        }
    }

    public AccueilFragment getAccueilFragment() {
        return accueilFragment;
    }

    public void setAccueilFragment(AccueilFragment accueilFragment) {
        this.accueilFragment = accueilFragment;
    }
}
