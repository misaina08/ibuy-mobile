package async.Recherche;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ibuy.misa.ibuy.R;
import com.ibuy.misa.ibuy.ResultSearchActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.ListProduitAdapter;
import adapter.ListProduitGeoAdapter;
import modele.RechercheAvancee;
import modele.produit.ProduitGeo;
import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/24/2017.
 */

public class RechercherAsync extends AsyncTask<RechercheAvancee, Void, List<ProduitView>> {
    ResultSearchActivity resultSearchActivity;
    private int affichage;

    @Override
    protected List<ProduitView> doInBackground(RechercheAvancee... params) {
        try {
            String url = WSUtil.getUrlServer() + "/recherche/avancee";
            WSRequestModele requestModele = new WSRequestModele();
            WSUtil wsUtil = new WSUtil();
            List<ProduitView> resultat = (List<ProduitView>) (List<?>) wsUtil.parseJsonArrayToList(requestModele.post(url, params[0]), new ProduitView());

            return resultat;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitView> resultats) {
        if (resultats != null) {
            resultSearchActivity.setResutlat(resultats);



            RecyclerView recyclerView = (RecyclerView) resultSearchActivity.findViewById(R.id.recResult);
            recyclerView.setLayoutManager(new GridLayoutManager(resultSearchActivity.getBaseContext(), 3));
            recyclerView.removeAllViews();
            if(affichage == 0){
                ListProduitAdapter listProduitAdapter = new ListProduitAdapter();
                listProduitAdapter.setActivity(resultSearchActivity);
                listProduitAdapter.setListeProduit(resultats);
                recyclerView.setAdapter(listProduitAdapter);
                resultSearchActivity.gettAucun().setVisibility(View.INVISIBLE);
            } else{
                recyclerView.setLayoutManager(new GridLayoutManager(resultSearchActivity.getBaseContext(), 3));
                List<ProduitGeo> listProduitGeo = new ArrayList<ProduitGeo>(resultats.size());
                for (ProduitView pv : resultats) {
                    ProduitGeo produitGeo = (ProduitGeo)pv;
                    produitGeo.setDuration("");
                    produitGeo.setDistance(new Double(0));
                    listProduitGeo.add(produitGeo);
                }
                ListProduitGeoAdapter listProduitAdapter = new ListProduitGeoAdapter();
                listProduitAdapter.setActivity(resultSearchActivity);
                listProduitAdapter.setListeProduit(listProduitGeo);
                recyclerView.setAdapter(listProduitAdapter);
                resultSearchActivity.gettAucun().setVisibility(View.INVISIBLE);
            }

            if (resultats.size() == 0) {
                resultSearchActivity.gettAucun().setVisibility(View.VISIBLE);
            }
        } else {
            if (resultats.size() == 0) {
                resultSearchActivity.gettAucun().setVisibility(View.VISIBLE);
            }
        }
    }

    public ResultSearchActivity getResultSearchActivity() {
        return resultSearchActivity;
    }

    public void setResultSearchActivity(ResultSearchActivity resultSearchActivity) {
        this.resultSearchActivity = resultSearchActivity;
    }

    public int getAffichage() {
        return affichage;
    }

    public void setAffichage(int affichage) {
        this.affichage = affichage;
    }
}
