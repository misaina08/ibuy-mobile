package async.Recherche;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheRechercheActivity;
import com.ibuy.misa.ibuy.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ListProduitAdapter;
import modele.SuggestProduitRecherche;
import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/30/2017.
 */

public class FicheAsync extends AsyncTask<String, Void, List<SuggestProduitRecherche>> {
    FicheRechercheActivity activity;

    @Override
    protected List<SuggestProduitRecherche> doInBackground(String... params) {
        try {
            String url = WSUtil.getUrlServer() + "/recherche/produits/" + params[0];
            WSRequestModele req = new WSRequestModele();
            return (List<SuggestProduitRecherche>) (List<?>) req.get(url, new SuggestProduitRecherche());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<SuggestProduitRecherche> suggestProduitRecherches) {
        if (suggestProduitRecherches != null) {
            List<ProduitView> produits = new ArrayList<ProduitView>();
            Gson gson = new Gson();
            for (SuggestProduitRecherche s : suggestProduitRecherches) {
                ProduitView p = gson.fromJson(s.getProduitJson(), ProduitView.class);
                produits.add(p);
            }
            final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recListPdtRech);
            recyclerView.setLayoutManager(new GridLayoutManager(activity.getBaseContext(), 3));
            ListProduitAdapter listProduitAdapter = new ListProduitAdapter();
            listProduitAdapter.setActivity(activity);
            listProduitAdapter.setListeProduit(produits);
            recyclerView.setAdapter(listProduitAdapter);
        }
    }

    public FicheRechercheActivity getActivity() {
        return activity;
    }

    public void setActivity(FicheRechercheActivity activity) {
        this.activity = activity;
    }
}
