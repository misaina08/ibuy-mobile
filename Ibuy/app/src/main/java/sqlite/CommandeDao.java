package sqlite;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.PanierFragment;

import java.util.ArrayList;
import java.util.List;

import async.achat.PasserCommandeAsync;
import modele.achat.ProduitCommandeView;
import modele.produit.ProduitView;
import modele.sqlite.ProduitPanier;

/**
 * Created by misa on 9/4/2017.
 */

public class CommandeDao extends BaseDao {
    private PanierFragment panierFragment;

    public void passerCommande() throws Exception {
        try {
            PanierDao panierDao = new PanierDao(getPanierFragment().getActivity());
            List<ProduitCommandeView> produits = new ArrayList<ProduitCommandeView>();
            List<ProduitPanier> produitsDansPanier = panierDao.getProduitDansPanier();
            Gson gson = new Gson();
            for (ProduitPanier pp : produitsDansPanier) {
                ProduitCommandeView pc = new ProduitCommandeView();
                pc.setQuantite(pp.getQuantite());
                ProduitView p = gson.fromJson(pp.getProduitJson(), ProduitView.class);
                pc.setIdProduit(p.getId());

                produits.add(pc);
            }
            List<ProduitCommandeView>[] params = new List[1];
            params[0] = produits;
            PasserCommandeAsync passerCommandeAsync = new PasserCommandeAsync();
            passerCommandeAsync.setPanierFragment(panierFragment);
            passerCommandeAsync.execute(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
