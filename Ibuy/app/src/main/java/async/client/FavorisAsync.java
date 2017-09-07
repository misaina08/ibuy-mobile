package async.client;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.ibuy.misa.ibuy.AbonnementFragment;
import com.ibuy.misa.ibuy.FicheMagasinActivity;
import com.ibuy.misa.ibuy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import modele.Client;
import modele.Magasin;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class FavorisAsync extends AsyncTask<Void, Void, List<Magasin>> {
    AbonnementFragment abonnementFragment;

    @Override
    protected List<Magasin> doInBackground(Void... params) {
        try {
            SessionManager sessionManager = new SessionManager(abonnementFragment.getActivity());
            Client c = SessionManager.getClientConnected();
            String url = WSUtil.getUrlServer() + "/clients/" + c.getId() + "/abonnements/favoris";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<Magasin>) (List<?>) requestModele.get(url, new Magasin());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Magasin> magasins) {
        if (magasins != null) {
            for (final Magasin m : magasins) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 20, 0);
                CircleImageView imIc = new CircleImageView(abonnementFragment.getActivity());
                imIc.setBackground(abonnementFragment.getActivity().getResources().getDrawable(R.drawable.button_bg));
                imIc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(abonnementFragment.getActivity(), FicheMagasinActivity.class);
                        intent.putExtra("idMagasin", m.getId());
                        abonnementFragment.getActivity().startActivity(intent);
                    }
                });
                imIc.setLayoutParams(lp);
                imIc.setMaxWidth(120);
                imIc.setMaxHeight(120);
                imIc.setBorderColor(Color.GRAY);
                imIc.setBorderWidth(1);

                Picasso.with(imIc.getContext()).load(WSUtil.getUrlPhotoProduit() + "/magasins/" + m.getPhoto())
                        .resize(120, 120)
                        .into(imIc);
//                Button buttonMagasin = new Button(abonnementFragment.getActivity());
//                buttonMagasin.setText(m.getNom());
//                buttonMagasin.setBackground(abonnementFragment.getActivity().getResources().getDrawable(R.drawable.button_bg));
//                buttonMagasin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(abonnementFragment.getActivity(), FicheMagasinActivity.class);
//                        intent.putExtra("idMagasin", m.getId());
//                        abonnementFragment.getActivity().startActivity(intent);
//                    }
//                });
//                abonnementFragment.getContentFavoris().addView(buttonMagasin);
                abonnementFragment.getContentFavoris().addView(imIc);
//                LinearLayout layout = new LinearLayout(abonnementFragment.getActivity());
//                layout.setOrientation(LinearLayout.VERTICAL);
//                layout.addView(imIc);
//                layout.addView(buttonMagasin);
//                abonnementFragment.getContentFavoris().addView(layout);
            }
        }
    }

    public AbonnementFragment getAbonnementFragment() {
        return abonnementFragment;
    }

    public void setAbonnementFragment(AbonnementFragment abonnementFragment) {
        this.abonnementFragment = abonnementFragment;
    }
}
