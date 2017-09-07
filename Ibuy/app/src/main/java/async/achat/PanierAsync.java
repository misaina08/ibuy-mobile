package async.achat;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.PanierFragment;
import com.ibuy.misa.ibuy.R;

import java.text.DecimalFormat;
import java.util.List;

import adapter.ListProduitPanierAdapter;
import modele.produit.ProduitView;
import modele.sqlite.ProduitPanier;
import sqlite.PanierDao;

/**
 * Created by misa on 9/3/2017.
 */

public class PanierAsync extends AsyncTask<Void, Void, List<ProduitPanier>> {
    private PanierFragment panierFragment;

    @Override
    protected List<ProduitPanier> doInBackground(Void... params) {
        try {
            PanierDao panierDao = new PanierDao(panierFragment.getActivity());
            return panierDao.getProduitDansPanier();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ProduitPanier> produitViews) {
        if (produitViews != null) {
            RecyclerView recPanier = (RecyclerView) panierFragment.getActivity().findViewById(R.id.recPanier);
            recPanier.setLayoutManager(new LinearLayoutManager(panierFragment.getActivity().getBaseContext()));
            ListProduitPanierAdapter listProduitAdapter = new ListProduitPanierAdapter();
            listProduitAdapter.setPanierFragment(panierFragment);
            listProduitAdapter.setListeProduitPanier(produitViews);
            recPanier.setAdapter(listProduitAdapter);

            Double total = new Double(0);
            Gson gson = new Gson();
            for (ProduitPanier produitPanier : produitViews) {
                ProduitView produit = gson.fromJson(produitPanier.getProduitJson(), ProduitView.class);
                total += produit.getPrix() * produitPanier.getQuantite();
            }
            panierFragment.gettTotal().setText(new DecimalFormat("#,##0.00").format(total) + " Ar");
        }

    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
