package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.ibuy.misa.ibuy.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import async.produit.AjoutSouhaitAsync;
import async.produit.CheckSouhaitProduitAsync;
import async.produit.RetirerSouhaitAsync;
import modele.produit.ProduitView;
import sqlite.PanierDao;
import utilitaire.WSUtil;

/**
 * Created by misa on 8/22/2017.
 */

public class ListProduitAdapter extends RecyclerView.Adapter<ListProduitAdapter.ViewHolder> {
    private Activity activity;
    private List<ProduitView> listeProduit;

    @Override
    public ListProduitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.liste_produit, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListProduitAdapter.ViewHolder holder, final int position) {
        holder.tNomProduit.setText(listeProduit.get(position).getDesignation());
        holder.tPrix.setText(new DecimalFormat("#,##0.00").format(listeProduit.get(position).getPrix()) + " Ar");
        Picasso.with(holder.pProduit.getContext()).load(WSUtil.getUrlPhotoProduit() + listeProduit.get(position).getPhoto()).into(holder.pProduit);

        holder.menuOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(activity, holder.menuOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_item_produit);

                // vérifie si le produit est déjà dans la liste des souhaits ou non
                CheckSouhaitProduitAsync checkSouhaitProduitAsync = new CheckSouhaitProduitAsync();
                checkSouhaitProduitAsync.setPopupMenu(popup);
                Integer[] idmagasinparam = {listeProduit.get(position).getId()};
                checkSouhaitProduitAsync.execute(idmagasinparam);

                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ajout_souhait:
                                ProduitView[] param = {listeProduit.get(position)};
                                AjoutSouhaitAsync ajoutSouhaitAsync = new AjoutSouhaitAsync();
                                ajoutSouhaitAsync.setActivity(getActivity());
                                ajoutSouhaitAsync.execute(param);
                                break;
                            case R.id.retirer_souhait:
                                Integer[] paramint = {listeProduit.get(position).getId()};
                                RetirerSouhaitAsync retirerSouhaitAsync = new RetirerSouhaitAsync();
                                retirerSouhaitAsync.setActivity(getActivity());
                                retirerSouhaitAsync.execute(paramint);

                                break;
                            case R.id.ajout_panier:
                                try {
                                    PanierDao panierDao = new PanierDao(activity);
                                    panierDao.ajout(listeProduit.get(position));
                                    Toast.makeText(activity, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(activity, "Une erreur a survenue", Toast.LENGTH_SHORT).show();
                                }
                        }
                        return false;
                    }
                });
                //displaying the popup

            }
        });
    }

    @Override
    public int getItemCount() {
        return listeProduit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tNomProduit;
        public final Button menuOptions;
        public final TextView tPrix;
        public final ImageView pProduit;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tNomProduit = ((TextView) itemView.findViewById(R.id.tNomProduit));
            tPrix = ((TextView) itemView.findViewById(R.id.tPrix));
            menuOptions = ((Button) itemView.findViewById(R.id.menuOptions));
            pProduit = ((ImageView) itemView.findViewById(R.id.pProduit));


//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FicheProduitActivity.class);
                    Gson gson = new Gson();

                    intent.putExtra("produitJson", gson.toJson(listeProduit.get(getAdapterPosition()), ProduitView.class));
                    activity.startActivity(intent);
                }
            });
        }
    }

    public List<ProduitView> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(List<ProduitView> listeProduit) {
        this.listeProduit = listeProduit;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
