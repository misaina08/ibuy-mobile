package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibuy.misa.ibuy.FicheEvenementActivity;
import com.ibuy.misa.ibuy.FicheProduitActivity;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import modele.ActusMagasin;
import utilitaire.Util;

/**
 * Created by misa on 8/22/2017.
 */

public class ListActusMagasinAdapter extends RecyclerView.Adapter<ListActusMagasinAdapter.ViewHolder> {
    Activity activity;
    private List<ActusMagasin> actusMagasins;

    @Override
    public ListActusMagasinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_actus_magasin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListActusMagasinAdapter.ViewHolder holder, int position) {
        holder.tDate.setText(Util.dateToString(actusMagasins.get(position).getDaty()) + " - " + actusMagasins.get(position).getHeure());
        holder.tContent.setText(actusMagasins.get(position).getContenu());
        holder.tTitre.setText(actusMagasins.get(position).getTitre());


    }

    @Override
    public int getItemCount() {
        return actusMagasins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tContent;
        public final TextView tTitre;
        public final TextView tDate;



        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tDate = (TextView) itemView.findViewById(R.id.tDate);
            tContent = (TextView) itemView.findViewById(R.id.tContent);
            tTitre = (TextView) itemView.findViewById(R.id.tTitre);


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

    public List<ActusMagasin> getActusMagasins() {
        return actusMagasins;
    }

    public void setActusMagasins(List<ActusMagasin> actusMagasins) {
        this.actusMagasins = actusMagasins;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
