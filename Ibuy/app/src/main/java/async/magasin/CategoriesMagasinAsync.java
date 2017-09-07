package async.magasin;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.ibuy.misa.ibuy.FicheMagasinActivity;
import com.ibuy.misa.ibuy.ProduitsMagasinActivity;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import modele.CategorieMagasinView;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/23/2017.
 */

public class CategoriesMagasinAsync extends AsyncTask<Integer, Void, List<CategorieMagasinView>> {
    Integer idmagasin;
    FicheMagasinActivity ficheMagasinActivity;
    @Override
    protected List<CategorieMagasinView> doInBackground(Integer... params) {
        try {
            idmagasin = params[0];
            String url = WSUtil.getUrlServer() + "/magasins/" + params[0]+"/categories";
            WSRequestModele requestModele = new WSRequestModele();
            return (List<CategorieMagasinView>)(List<?>) requestModele.get(url, new CategorieMagasinView());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<CategorieMagasinView> categories) {
        if (categories != null) {
            ficheMagasinActivity.getContentCategories().removeAllViews();
            for(final CategorieMagasinView cat : categories){
               final Button button = new Button(ficheMagasinActivity);
                button.setBackgroundResource(R.drawable.button_c_selector);
                button.setText(cat.getCategorie());
                button.setHeight(15);
                button.setTextAppearance(ficheMagasinActivity, android.R.style.TextAppearance_Material);
                button.setTextColor(ficheMagasinActivity.getResources().getColor(R.color.colorPrimary));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ficheMagasinActivity, ProduitsMagasinActivity.class);
                        intent.putExtra("idmagasin", idmagasin);
                        intent.putExtra("idcategorie", cat.getCategorieId());
                        ficheMagasinActivity.startActivity(intent);
                    }
                });
                button.setOnHoverListener(new View.OnHoverListener() {
                    @Override
                    public boolean onHover(View v, MotionEvent event) {
                        button.setTextColor(Color.WHITE);
                        return false;
                    }
                });

                ficheMagasinActivity.getContentCategories().addView(button);
            }
        }
    }

    public FicheMagasinActivity getFicheMagasinActivity() {
        return ficheMagasinActivity;
    }

    public void setFicheMagasinActivity(FicheMagasinActivity ficheMagasinActivity) {
        this.ficheMagasinActivity = ficheMagasinActivity;
    }
}
