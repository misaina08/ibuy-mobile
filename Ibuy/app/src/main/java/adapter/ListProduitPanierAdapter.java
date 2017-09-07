package adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.ibuy.misa.ibuy.PanierFragment;
import com.ibuy.misa.ibuy.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import async.achat.QuantiteUpdateAsync;
import modele.produit.ProduitView;
import modele.sqlite.ProduitPanier;
import utilitaire.WSUtil;

/**
 * Created by misa on 8/22/2017.
 */

public class ListProduitPanierAdapter extends RecyclerView.Adapter<ListProduitPanierAdapter.ViewHolder> {
    private PanierFragment panierFragment;
    private List<ProduitPanier> listeProduitPanier;

    @Override
    public ListProduitPanierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_produit_panier, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListProduitPanierAdapter.ViewHolder holder, final int position) {
        Gson gson = new Gson();
        ProduitView produit = gson.fromJson(listeProduitPanier.get(position).getProduitJson(), ProduitView.class);
        holder.tDesignation.setText(produit.getDesignation());
        holder.tPrix.setText(new DecimalFormat("#,##0.00").format(listeProduitPanier.get(position).getQuantite() * produit.getPrix()) + " Ar");
        holder.tMagasin.setText(produit.getMagasin());
        holder.tQte.setText("Qte : " + listeProduitPanier.get(position).getQuantite() + " " + produit.getUnite());
        Picasso.with(holder.photo.getContext()).load(WSUtil.getUrlPhotoProduit() + produit.getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return listeProduitPanier.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tDesignation;
        public final Button bMoins;
        public final Button bPlus;
        public final TextView tPrix;
        public final TextView tMagasin;
        public final TextView tQte;
        public final ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tDesignation = ((TextView) itemView.findViewById(R.id.tDesignation));
            tPrix = ((TextView) itemView.findViewById(R.id.tPrix));
            tMagasin = ((TextView) itemView.findViewById(R.id.tMagasin));
            tQte = ((TextView) itemView.findViewById(R.id.tQte));
            bMoins = ((Button) itemView.findViewById(R.id.bMoins));
            bPlus = ((Button) itemView.findViewById(R.id.bPlus));
            photo = ((ImageView) itemView.findViewById(R.id.photo));


            bMoins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ProduitPanier produitPanier = listeProduitPanier.get(getAdapterPosition());
                        Gson gson = new Gson();
                        ProduitView produitView = gson.fromJson(produitPanier.getProduitJson(), ProduitView.class);
                        Integer[] params = {produitView.getId(), 0};
                        QuantiteUpdateAsync quantiteUpdateAsync = new QuantiteUpdateAsync();
                        quantiteUpdateAsync.setPanierFragment(panierFragment);
                        quantiteUpdateAsync.execute(params);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            bPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ProduitPanier produitPanier = listeProduitPanier.get(getAdapterPosition());
                        Gson gson = new Gson();
                        ProduitView produitView = gson.fromJson(produitPanier.getProduitJson(), ProduitView.class);
                        Integer[] params = {produitView.getId(), 1};
                        QuantiteUpdateAsync quantiteUpdateAsync = new QuantiteUpdateAsync();
                        quantiteUpdateAsync.setPanierFragment(panierFragment);
                        quantiteUpdateAsync.execute(params);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(panierFragment.getActivity(), FicheProduitActivity.class);
                    Gson gson = new Gson();

                    intent.putExtra("produitJson", listeProduitPanier.get(getAdapterPosition()).getProduitJson());
                    panierFragment.getActivity().startActivity(intent);
                }
            });
        }
    }

    public List<ProduitPanier> getListeProduitPanier() {
        return listeProduitPanier;
    }

    public void setListeProduitPanier(List<ProduitPanier> listeProduitPanier) {
        this.listeProduitPanier = listeProduitPanier;
    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
