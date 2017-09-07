package async.evenement;

import android.os.AsyncTask;

import com.ibuy.misa.ibuy.FicheEvenementActivity;

import modele.EvenementMagasinView;
import utilitaire.Util;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/29/2017.
 */

public class FicheEvenementAsync extends AsyncTask<Integer, Void, EvenementMagasinView> {
    FicheEvenementActivity ficheEvenementActivity;

    @Override
    protected EvenementMagasinView doInBackground(Integer... params) {
        try {
            String url = WSUtil.getUrlServer() + "/magasins/evenements/" + params[0];
            WSRequestModele req = new WSRequestModele();
            return (EvenementMagasinView) req.getOne(url, new EvenementMagasinView());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(EvenementMagasinView evenementMagasinView) {
        if (evenementMagasinView != null) {
            ficheEvenementActivity.gettTitre().setText(evenementMagasinView.getLibelle());
            ficheEvenementActivity.gettPeriode().setText(Util.dateToString(evenementMagasinView.getDatydebut()) + " à " + Util.dateToString(evenementMagasinView.getDatyfin()));
            ficheEvenementActivity.gettDescription().setText(evenementMagasinView.getDescription());
        }
    }

    public FicheEvenementActivity getFicheEvenementActivity() {
        return ficheEvenementActivity;
    }

    public void setFicheEvenementActivity(FicheEvenementActivity ficheEvenementActivity) {
        this.ficheEvenementActivity = ficheEvenementActivity;
    }
}
