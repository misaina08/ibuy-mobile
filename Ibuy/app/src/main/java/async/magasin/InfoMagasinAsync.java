package async.magasin;

import android.os.AsyncTask;

import com.ibuy.misa.ibuy.FicheMagasinActivity;
import com.squareup.picasso.Picasso;

import modele.Magasin;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class InfoMagasinAsync extends AsyncTask<Integer, Void, Magasin> {
    FicheMagasinActivity ficheMagasinActivity;
    @Override
    protected Magasin doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/magasins/" + params[0];
            WSRequestModele requestModele = new WSRequestModele();
            return (Magasin) requestModele.getOne(url, new Magasin());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Magasin magasin) {
        if (magasin != null) {
            ficheMagasinActivity.getSupportActionBar().setTitle(magasin.getNom());
//            ficheMagasinActivity.gettNom().setText(magasin.getNom());
            ficheMagasinActivity.gettAdresse().setText(magasin.getAdresse());
            ficheMagasinActivity.gettContact().setText(magasin.getNumero()+" - "+magasin.getEmail());
            Picasso.with(ficheMagasinActivity).load(WSUtil.getUrlPhotoProduit() + "/magasins/" + magasin.getPhoto()).into(ficheMagasinActivity.getLogoMagasin());
            ficheMagasinActivity.setMagasin(magasin);
        }
    }

    public FicheMagasinActivity getFicheMagasinActivity() {
        return ficheMagasinActivity;
    }

    public void setFicheMagasinActivity(FicheMagasinActivity ficheMagasinActivity) {
        this.ficheMagasinActivity = ficheMagasinActivity;
    }
}
