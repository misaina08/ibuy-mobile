package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.produit.ProduitView;
import modele.sqlite.ProduitPanier;
import utilitaire.Util;

/**
 * Created by misa on 9/3/2017.
 */

public class PanierDao extends BaseDao {
    public PanierDao(Context context) {
        dbHelper = DbHelper.init(context, true);
    }

    public void ajout(ProduitView produit) throws Exception {
        try {
            ProduitPanier produitPanier = checkSiProduitEst(produit.getId());
//            si le produit n'existe pas encore, on l'ajoute à nouveau
            if (produitPanier == null) {
                Gson gson = new Gson();
                ContentValues values = new ContentValues();
                Date datenow = new Date();
                values.put("daty", Util.dateToString(datenow));
                values.put("produitjson", gson.toJson(produit));
                values.put("quantite", 1);

                SQLiteDatabase db = this.dbHelper.getWritableDatabase();

                db.insert("panier", null, values);
            }
//            sinon, on met à jour la quantité
            else {
                updateQuantiteProduit(produit.getId(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<ProduitPanier> getProduitDansPanier() throws Exception {
        try {
            List<ProduitPanier> result = new ArrayList<ProduitPanier>();
            Cursor cursor = this.dbHelper.getReadableDatabase().rawQuery("select * from panier", null);
            Gson gson = new Gson();
            while (cursor.moveToNext()) {
                ProduitPanier produitPanier = new ProduitPanier();
                produitPanier.setId(cursor.getInt(0));
                produitPanier.setDaty(Util.stringToDate(cursor.getString(1)));
                produitPanier.setProduitJson(cursor.getString(2));
                produitPanier.setQuantite(cursor.getInt(3));
                result.add(produitPanier);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public ProduitPanier checkSiProduitEst(int idproduit) throws Exception {
        try {
            List<ProduitPanier> liste = getProduitDansPanier();
            Gson gson = new Gson();
            for (ProduitPanier p : liste) {
                ProduitView produitView = gson.fromJson(p.getProduitJson(), ProduitView.class);
                if (idproduit == produitView.getId()) {
                    return p;
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void updateQuantiteProduit(int idproduit, int choix) throws Exception {
        try {
            ProduitPanier produitPanier = checkSiProduitEst(idproduit);
            int quantiteUpdate = 0;
            if (choix == 0) quantiteUpdate = -1;
            else quantiteUpdate = 1;

            int currentQte = produitPanier.getQuantite() + quantiteUpdate;
            if (currentQte <= 0) {
//                    supprimer pdt
                dbHelper.getWritableDatabase().delete("panier", "id = ?", new String[]{String.valueOf(produitPanier.getId())});
                return;
            } else {
                ContentValues value = new ContentValues();
                value.put("quantite", produitPanier.getQuantite() + quantiteUpdate);
                dbHelper.getWritableDatabase().update("panier", value, "id" + " = ?", new String[]{produitPanier.getId().toString()});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void vider() throws Exception {
        try {
            dbHelper.getWritableDatabase().delete("panier", null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
