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
import com.ibuy.misa.ibuy.SuggestionEvtActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import modele.notification.Notification;

/**
 * Created by misa on 8/22/2017.
 */

public class ListNotifAdapter extends RecyclerView.Adapter<ListNotifAdapter.ViewHolder> {
    Activity activity;
    private List<Notification> listNotif;

    @Override
    public ListNotifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_notif, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListNotifAdapter.ViewHolder holder, int position) {
        Notification notif = listNotif.get(position);
        holder.tTitre.setText(notif.getTitre());
        holder.tContent.setText(notif.getContent());
        System.out.println("_________" + notif.getTargetAction());
        if (notif.getTargetAction().compareToIgnoreCase("ficheProduit") == 0) {
            Picasso.with(holder.imNotif.getContext()).load(R.drawable.ic_new).into(holder.imNotif);
        } else if (notif.getTargetAction().compareToIgnoreCase("ficheEvenement") == 0
                || notif.getTargetAction().compareToIgnoreCase("ficheEvenementClient") == 0) {
            Picasso.with(holder.imNotif.getContext()).load(R.drawable.ic_event).into(holder.imNotif);
        } else {
            Picasso.with(holder.imNotif.getContext()).load(R.drawable.ic_notification).into(holder.imNotif);
        }

    }

    @Override
    public int getItemCount() {
        return listNotif.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tTitre;
        public final TextView tContent;
        public final CircleImageView imNotif;

        public ViewHolder(View itemView) {
            super(itemView);

//            initialisation des composants graphiques
            tTitre = (TextView) itemView.findViewById(R.id.tTitre);
            tContent = (TextView) itemView.findViewById(R.id.tContent);
            imNotif = (CircleImageView) itemView.findViewById(R.id.imNotif);

//            events
//            ex : item onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notification notification = listNotif.get(getAdapterPosition());
                    if (notification.getTargetAction().compareToIgnoreCase("ficheProduit") == 0) {
                        Intent intent = new Intent(activity, FicheProduitActivity.class);
                        intent.putExtra("produitJson", notification.getBundles());
                        activity.startActivity(intent);
                    }
                    else if (notification.getTargetAction().compareToIgnoreCase("ficheEvenementClient") == 0) {
                        Intent intent = new Intent(activity, SuggestionEvtActivity.class);
                        intent.putExtra("evtJson", notification.getBundles());
                        activity.startActivity(intent);
                    }
                    else if (notification.getTargetAction().compareToIgnoreCase("ficheEvenement") == 0) {
                        Intent intent = new Intent(activity, FicheEvenementActivity.class);
                        intent.putExtra("idEvenement", notification.getBundles());
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

    public List<Notification> getListNotif() {
        return listNotif;
    }

    public void setListNotif(List<Notification> listNotif) {
        this.listNotif = listNotif;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
