package async.produit;


import android.os.AsyncTask;

import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import modele.produit.ProduitView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/22/2017.
 */

public class FicheProduitAsync extends AsyncTask<Integer, Void, ProduitView> {
    private FicheProduitActivity ficheProduitActivity;

    @Override
    protected ProduitView doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/produits/" + params[0];
            WSRequestModele requestModele = new WSRequestModele();
            return (ProduitView) requestModele.getOne(url, new ProduitView());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ProduitView produit) {
        if (produit != null) {
//            ficheProduitActivity.gettDesignation().setText(produit.getDesignation());
//            ficheProduitActivity.gettCategorie().setText(produit.getCategorie());
            ficheProduitActivity.gettPrix().setText(new DecimalFormat("#,##0.00").format(produit.getPrix()) + " Ariary");
            ficheProduitActivity.gettMagasin().setText(produit.getMagasin());
            Picasso.with(ficheProduitActivity.getiPhoto().getContext()).load(WSUtil.getUrlPhotoProduit()+produit.getPhoto()).into(ficheProduitActivity.getiPhoto());
            ficheProduitActivity.gettDescription().setText(produit.getTag());
            ficheProduitActivity.setProduit(produit);
        }
    }

    public FicheProduitActivity getFicheProduitActivity() {
        return ficheProduitActivity;
    }

    public void setFicheProduitActivity(FicheProduitActivity ficheProduitActivity) {
        this.ficheProduitActivity = ficheProduitActivity;
    }
}
