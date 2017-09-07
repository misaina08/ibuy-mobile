package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.FicheRechercheActivity;
import com.ibuy.misa.ibuy.R;
import com.ibuy.misa.ibuy.ResultSearchActivity;

import java.text.DecimalFormat;
import java.util.List;

import modele.RechercheAvancee;

/**
 * Created by misa on 8/26/2017.
 */

public class ListRecherchesAdapter extends RecyclerView.Adapter<ListRecherchesAdapter.ViewHolder> {
    Activity activity;
    private List<RechercheAvancee> recherches;

    @Override
    public ListRecherchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_recherches, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListRecherchesAdapter.ViewHolder holder, int position) {
        String cat = recherches.get(position).getMotCle();
        String mag = recherches.get(position).getMagasin();
        String p = "";
        if (recherches.get(position).getCategorie().isEmpty()) {
            cat = "Tout";
        }
        int min = 0;
        if (recherches.get(position).getPrixMin() != 0) {
            p = new DecimalFormat("#,##0.00").format(recherches.get(position).getPrixMin()) + " < prix ";
            min = 1;
        }
        if (recherches.get(position).getPrixMax() != 0) {
            if (min == 0){
                p += " prix ";
            }
            p += " < " + new DecimalFormat("#,##0.00").format(recherches.get(position).getPrixMax());
        }

        holder.tMotCle.setText(recherches.get(position).getMotCle());
        holder.tMagasin.setText(recherches.get(position).getMagasin());
        holder.tCat.setText(recherches.get(position).getCategorie());
        holder.tPrix.setText(p);
    }

    @Override
    public int getItemCount() {
        return recherches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tMotCle;
        public final TextView tMagasin;
        public final TextView tCat;
        public final TextView tPrix;
        public final Button bLancer;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tMotCle = ((TextView) itemView.findViewById(R.id.tMotCle));
            tMagasin = ((TextView) itemView.findViewById(R.id.tMagasin));
            tCat = ((TextView) itemView.findViewById(R.id.tCat));
            tPrix = ((TextView) itemView.findViewById(R.id.tPrix));
            bLancer = (Button) itemView.findViewById(R.id.bLancer);
            bLancer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gso = new Gson();
                    RechercheAvancee recherche = recherches.get(getAdapterPosition());
                    Intent intent = new Intent(activity, ResultSearchActivity.class);
                    intent.putExtra("query", recherche.getMotCle());
                    intent.putExtra("recherche", gso.toJson(recherche));
                    activity.startActivity(intent);
                }
            });

//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FicheRechercheActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("rechercheJson", gson.toJson(recherches.get(getAdapterPosition()), RechercheAvancee.class));
                    activity.startActivity(intent);
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

    public List<RechercheAvancee> getRecherches() {
        return recherches;
    }

    public void setRecherches(List<RechercheAvancee> recherches) {
        this.recherches = recherches;
    }
}
