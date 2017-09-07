package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibuy.misa.ibuy.FicheCommandeActivity;
import com.ibuy.misa.ibuy.R;

import java.text.DecimalFormat;
import java.util.List;

import modele.achat.CommandeView;
import utilitaire.Util;

/**
 * Created by misa on 8/22/2017.
 */

public class ListCommandeAdapter extends RecyclerView.Adapter<ListCommandeAdapter.ViewHolder> {
    private Activity activity;
    private List<CommandeView> listeCommandes;

    @Override
    public ListCommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_commandes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListCommandeAdapter.ViewHolder holder, final int position) {
        holder.tDate.setText(Util.dateToString(listeCommandes.get(position).getDaty()));
        holder.tMt.setText(new DecimalFormat("#,##0.00").format(listeCommandes.get(position).getTotal()) + " Ar");
    }

    @Override
    public int getItemCount() {
        return listeCommandes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tDate;
        public final TextView tMt;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tDate = ((TextView) itemView.findViewById(R.id.tDate));
            tMt = ((TextView) itemView.findViewById(R.id.tMt));


//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FicheCommandeActivity.class);
                    intent.putExtra("idcommande", listeCommandes.get(getAdapterPosition()).getId());
                    activity.startActivity(intent);
                }
            });
        }
    }

    public List<CommandeView> getListeCommandes() {
        return listeCommandes;
    }

    public void setListeCommandes(List<CommandeView> listeCommandes) {
        this.listeCommandes = listeCommandes;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
