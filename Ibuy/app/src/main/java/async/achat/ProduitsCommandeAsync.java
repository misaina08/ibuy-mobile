package async.achat;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.FicheCommandeActivity;
import com.ibuy.misa.ibuy.R;

import java.text.DecimalFormat;
import java.util.List;

import adapter.ListProduitCommandeAdapter;
import modele.achat.ProduitCommandeView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/5/2017.
 */

public class ProduitsCommandeAsync extends AsyncTask<Integer, Void, List<ProduitCommandeView>> {
    private FicheCommandeActivity ficheCommandeActivity;
    @Override
    protected List<ProduitCommandeView> doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/achat/commandes/" + params[0] + "/produits";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<ProduitCommandeView>) (List<?>) requestModele.get(url, new ProduitCommandeView());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitCommandeView> produitCommandeViews) {
        if (produitCommandeViews != null) {
            RecyclerView recProduitsCommande = (RecyclerView) ficheCommandeActivity.findViewById(R.id.recProduitsCommande);
            recProduitsCommande.setLayoutManager(new LinearLayoutManager(ficheCommandeActivity));
            ListProduitCommandeAdapter listProduitCommandeAdapter = new ListProduitCommandeAdapter();
            listProduitCommandeAdapter.setActivity(ficheCommandeActivity);
            listProduitCommandeAdapter.setProduits(produitCommandeViews);
            recProduitsCommande.setAdapter(listProduitCommandeAdapter);

            Double tot = new Double(0);
            for (ProduitCommandeView p : produitCommandeViews) {
                tot += p.getPrix() * p.getQuantite();
            }
            ficheCommandeActivity.gettTotal().setText(new DecimalFormat("#,##0.00").format(tot) + " Ar");
        }
    }

    public FicheCommandeActivity getFicheCommandeActivity() {
        return ficheCommandeActivity;
    }

    public void setFicheCommandeActivity(FicheCommandeActivity ficheCommandeActivity) {
        this.ficheCommandeActivity = ficheCommandeActivity;
    }
}
