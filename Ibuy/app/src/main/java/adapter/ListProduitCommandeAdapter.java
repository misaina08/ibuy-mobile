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
import com.ibuy.misa.ibuy.FicheCommandeActivity;
import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.ibuy.misa.ibuy.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import modele.achat.ProduitCommandeView;
import modele.produit.ProduitView;
import utilitaire.WSUtil;

/**
 * Created by misa on 8/22/2017.
 */

public class ListProduitCommandeAdapter extends RecyclerView.Adapter<ListProduitCommandeAdapter.ViewHolder> {
    private FicheCommandeActivity activity;
    private List<ProduitCommandeView> produits;

    @Override
    public ListProduitCommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_produit_panier, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListProduitCommandeAdapter.ViewHolder holder, final int position) {
        Gson gson = new Gson();
        holder.tDesignation.setText(produits.get(position).getDesignation());
        holder.tPrix.setText(new DecimalFormat("#,##0.00").format(produits.get(position).getQuantite() * produits.get(position).getPrix()) + " Ar");
        holder.tQte.setText("Qte : " + produits.get(position).getQuantite() + " " + produits.get(position).getQuantite());
        Picasso.with(holder.photo.getContext()).load(WSUtil.getUrlPhotoProduit() + produits.get(position).getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return produits.size();
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
            bMoins.setVisibility(View.INVISIBLE);
            bPlus.setVisibility(View.INVISIBLE);
            tMagasin.setVisibility(View.INVISIBLE);
//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    ProduitView p = new ProduitView();
                    p.setId(produits.get(getAdapterPosition()).getId());
                    Intent intent = new Intent(activity, FicheProduitActivity.class);
                    intent.putExtra("produitJson", gson.toJson(p, ProduitView.class));
                    activity.startActivity(intent);
                }
            });
        }
    }


    public FicheCommandeActivity getActivity() {
        return activity;
    }

    public void setActivity(FicheCommandeActivity activity) {
        this.activity = activity;
    }

    public List<ProduitCommandeView> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitCommandeView> produits) {
        this.produits = produits;
    }
}
