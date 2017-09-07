package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheEvenementActivity;
import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.ibuy.misa.ibuy.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import modele.ActusMagasin;
import modele.produit.ProduitView;
import utilitaire.Util;
import utilitaire.WSUtil;

/**
 * Created by misa on 8/30/2017.
 */

public class FilActusAdapter extends RecyclerView.Adapter<FilActusAdapter.ViewHolder> {
    Activity activity;
    private List<ActusMagasin> actusMagasins;

    @Override
    public FilActusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_fil_actus, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilActusAdapter.ViewHolder holder, int position) {
        holder.tDate.setText(Util.dateToString(actusMagasins.get(position).getDaty()) + " - " + actusMagasins.get(position).getHeure());
        String resume = "";
        if (actusMagasins.get(position).getActionTarget().compareToIgnoreCase("ficheEvenement") == 0) {
            resume = "Evènement : " + actusMagasins.get(position).getContenu();
            holder.pActus.setVisibility(View.INVISIBLE);
        }
        if (actusMagasins.get(position).getActionTarget().compareToIgnoreCase("ficheProduit") == 0) {
            Gson gson = new Gson();
            ProduitView produit = gson.fromJson(actusMagasins.get(position).getBundles(), ProduitView.class);
            resume = "Produit : " + actusMagasins.get(position).getContenu() + " à " +
                    new DecimalFormat("#,##0.00").format(produit.getPrix()) + " Ariary";

            Picasso.with(holder.pActus.getContext()).load(WSUtil.getUrlPhotoProduit()+produit.getPhoto()).into(holder.pActus);
        }
        Picasso.with(holder.imIc.getContext()).load(WSUtil.getUrlPhotoProduit()+"/magasins/"+actusMagasins.get(position).getLogoMagasin()).into(holder.imIc);
        holder.tResume.setText(resume);
        holder.tNomMag.setText(actusMagasins.get(position).getMagasin());
    }

    @Override
    public int getItemCount() {
        return actusMagasins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tNomMag;
        public final TextView tResume;
        public final TextView tDate;
        public final CircleImageView imIc;
        public final ImageView pActus;


        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tDate = (TextView) itemView.findViewById(R.id.tDate);
            tNomMag = (TextView) itemView.findViewById(R.id.tNomMag);
            tResume = (TextView) itemView.findViewById(R.id.tResume);
            imIc = (CircleImageView) itemView.findViewById(R.id.imIc);
            pActus = (ImageView) itemView.findViewById(R.id.pActus);

//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActusMagasin actus = actusMagasins.get(getAdapterPosition());
                    if (actus.getActionTarget().compareToIgnoreCase("ficheEvenement") == 0) {
                        Intent intent = new Intent(activity, FicheEvenementActivity.class);
                        intent.putExtra("idEvenement", actus.getBundles());
                        activity.startActivity(intent);
                    }
                    if (actus.getActionTarget().compareToIgnoreCase("ficheProduit") == 0) {
                        Intent intent = new Intent(activity, FicheProduitActivity.class);
                        intent.putExtra("produitJson", actus.getBundles());
                        activity.startActivity(intent);
                    }

                }
            });
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<ActusMagasin> getActusMagasins() {
        return actusMagasins;
    }

    public void setActusMagasins(List<ActusMagasin> actusMagasins) {
        this.actusMagasins = actusMagasins;
    }
}
