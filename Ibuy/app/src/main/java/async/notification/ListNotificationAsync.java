package async.notification;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.NotificationFragment;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListNotifAdapter;
import modele.notification.Notification;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 8/27/2017.
 */

public class ListNotificationAsync extends AsyncTask<Void, Void, List<Notification>> {
    NotificationFragment notificationFragment;
    @Override
    protected List<Notification> doInBackground(Void... params) {
        try{
            String url = WSUtil.getUrlServer()+"/notifications/unchecked/"+ SessionManager.getClientConnected().getId();
            WSRequestModele r = new WSRequestModele();
            return (List<Notification>)(List<?>)r.get(url, new Notification());
        }catch(Exception ex){
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Notification> notifications) {
        if(notifications != null){
            final RecyclerView recyclerView = (RecyclerView) notificationFragment.getView().findViewById(R.id.recListNotif);
            recyclerView.setLayoutManager(new LinearLayoutManager(notificationFragment.getActivity().getBaseContext()));
            ListNotifAdapter listNotifAdapter = new ListNotifAdapter();
            listNotifAdapter.setActivity(notificationFragment.getActivity());
            listNotifAdapter.setListNotif(notifications);
            recyclerView.setAdapter(listNotifAdapter);
        }
    }

    public NotificationFragment getNotificationFragment() {
        return notificationFragment;
    }

    public void setNotificationFragment(NotificationFragment notificationFragment) {
        this.notificationFragment = notificationFragment;
    }
}
