package async.Recherche;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.ibuy.misa.ibuy.ResultSearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.ListProduitGeoAdapter;
import modele.produit.ProduitGeo;
import modele.produit.ProduitView;
import ws.WSRequestModele;

/**
 * Created by misa on 8/29/2017.
 * params[0] => rayon de x km
 */

public class ProduitsAproximiteAsync extends AsyncTask<Double, Void, List<ProduitGeo>> {
    private LatLng currentPos;
    private List<ProduitView> produits;
    private ResultSearchActivity resultSearchActivity;

    @Override
    protected List<ProduitGeo> doInBackground(Double... params) {
        try {
            String destinations = "";
            for (ProduitView p : produits) {
                destinations += p.getLatitude() + "," + p.getLongitude() + "|";
            }
            destinations.substring(destinations.length() - 1);
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + currentPos.latitude + "," + currentPos.longitude + "&destinations="+destinations+"&key=AIzaSyC7_qhckTBTyCGi86AY9JTnSsxvTZ4Tt5w";
            System.out.println("destinations : "+url);
//            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=-18.874184,47.505439&destinations=-18.875291,47.503143|-18.875458,47.507000&key=AIzaSyC7_qhckTBTyCGi86AY9JTnSsxvTZ4Tt5w";
            WSRequestModele requestModele = new WSRequestModele();
            String jsonResponse = requestModele.getContent(url);
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray rows = response.getJSONArray("rows");
            JSONObject element0 = rows.getJSONObject(0);
            JSONArray jsonArrayElements = element0.getJSONArray("elements");
            System.out.println("huhu"+jsonArrayElements);

            List<ProduitGeo> result = new ArrayList<ProduitGeo>();

            for (int i = 0; i<jsonArrayElements.length(); i++) {

                System.out.println(jsonArrayElements.get(i).toString());
                JSONObject element = new JSONObject(jsonArrayElements.get(i).toString());

                System.out.println("elementarray"+element);
                JSONObject distanceObject = element.getJSONObject("distance");
                JSONObject durationObject = element.getJSONObject("duration");
                System.out.println("distanceObject "+distanceObject);
                System.out.println("value "+distanceObject.getDouble("value"));
                if(distanceObject.getDouble("value") <= params[0]) {
                    ProduitGeo produitGeo = new ProduitGeo();
                    produitGeo.setCategorie(produits.get(i).getCategorie());
                    produitGeo.setMagasin(produits.get(i).getMagasin());
                    produitGeo.setAdresse(produits.get(i).getAdresse());
                    produitGeo.setCategorieId(produits.get(i).getCategorieId());
                    produitGeo.setDateajout(produits.get(i).getDateajout());
                    produitGeo.setDesignation(produits.get(i).getDesignation());
                    produitGeo.setId(produits.get(i).getId());
                    produitGeo.setLatitude(produits.get(i).getLatitude());
                    produitGeo.setLongitude(produits.get(i).getLongitude());
                    produitGeo.setMagasinId(produits.get(i).getMagasinId());
                    produitGeo.setCategorieId(produits.get(i).getCategorieId());
                    produitGeo.setNbvues(produits.get(i).getNbvues());
                    produitGeo.setPrix(produits.get(i).getPrix());
                    produitGeo.setPhoto(produits.get(i).getPhoto());
                    produitGeo.setQuantiteenstock(produits.get(i).getQuantiteenstock());
                    produitGeo.setUnite(produits.get(i).getUnite());
                    produitGeo.setDuration(durationObject.getString("text"));
                    produitGeo.setDistance(distanceObject.getDouble("value"));
                    result.add(produitGeo);
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitGeo> resultats) {
        try {
            if (resultats != null) {
                resultSearchActivity.getRecyclerView().setVisibility(View.VISIBLE);
                resultSearchActivity.getRecyclerView().setLayoutManager(new LinearLayoutManager(resultSearchActivity.getBaseContext()));
                resultSearchActivity.getRecyclerView().removeAllViews();

                ListProduitGeoAdapter listProduitAdapter = new ListProduitGeoAdapter();
                listProduitAdapter.setActivity(resultSearchActivity);
                listProduitAdapter.setListeProduit(resultats);
                resultSearchActivity.getRecyclerView().setAdapter(listProduitAdapter);

                resultSearchActivity.getProgressContent().setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LatLng getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(LatLng currentPos) {
        this.currentPos = currentPos;
    }

    public List<ProduitView> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitView> produits) {
        this.produits = produits;
    }

    public ResultSearchActivity getResultSearchActivity() {
        return resultSearchActivity;
    }

    public void setResultSearchActivity(ResultSearchActivity resultSearchActivity) {
        this.resultSearchActivity = resultSearchActivity;
    }
}
