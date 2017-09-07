package async.achat;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ibuy.misa.ibuy.CommandesFragment;
import com.ibuy.misa.ibuy.R;

import java.util.List;

import adapter.ListCommandeAdapter;
import modele.achat.CommandeView;
import services.SessionManager;
import utilitaire.WSUtil;
import ws.WSRequestModele;

/**
 * Created by misa on 9/5/2017.
 */

public class ListeCommandeAsync extends AsyncTask<Void, Void, List<CommandeView>> {
    private CommandesFragment commandesFragment;

    @Override
    protected List<CommandeView> doInBackground(Void... params) {
        try {
            String url = WSUtil.getUrlServer() + "/achat/commandes/" + SessionManager.getClientConnected().getId();
            WSRequestModele requestModele = new WSRequestModele();
            return (List<CommandeView>) (List<?>) requestModele.get(url, new CommandeView());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<CommandeView> commandeViews) {
        if (commandeViews != null) {
            RecyclerView recyclerView = (RecyclerView) commandesFragment.getActivity().findViewById(R.id.recCommandes);
            recyclerView.setLayoutManager(new LinearLayoutManager(commandesFragment.getActivity()));
            ListCommandeAdapter listCommandeAdapter = new ListCommandeAdapter();
            listCommandeAdapter.setActivity(commandesFragment.getActivity());
            listCommandeAdapter.setListeCommandes(commandeViews);
            recyclerView.setAdapter(listCommandeAdapter);

        }
    }

    public CommandesFragment getCommandesFragment() {
        return commandesFragment;
    }

    public void setCommandesFragment(CommandesFragment commandesFragment) {
        this.commandesFragment = commandesFragment;
    }
}
