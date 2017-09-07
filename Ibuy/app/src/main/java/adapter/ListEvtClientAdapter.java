package adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibuy.misa.ibuy.R;
import com.ibuy.misa.ibuy.SuggestionEvtActivity;

import java.util.List;

import modele.EvenementClient;
import utilitaire.Util;

/**
 * Created by misa on 8/26/2017.
 */

public class ListEvtClientAdapter extends RecyclerView.Adapter<ListEvtClientAdapter.ViewHolder> {
    Activity activity;
    private List<EvenementClient> evenementClients;

    @Override
    public ListEvtClientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_evt_client, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListEvtClientAdapter.ViewHolder holder, int position) {
        holder.tLibelle.setText(Util.dateToString(evenementClients.get(position).getDaty()));
        holder.tDate.setText(evenementClients.get(position).getLibelle());
    }

    @Override
    public int getItemCount() {
        return evenementClients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tLibelle;
        public final TextView tDate;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tLibelle = ((TextView) itemView.findViewById(R.id.tLibelle));
            tDate = ((TextView) itemView.findViewById(R.id.tDate));

//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SuggestionEvtActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("evtJson", gson.toJson(evenementClients.get(getAdapterPosition()), EvenementClient.class));
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

    public List<EvenementClient> getEvenementClients() {
        return evenementClients;
    }

    public void setEvenementClients(List<EvenementClient> evenementClients) {
        this.evenementClients = evenementClients;
    }
}
