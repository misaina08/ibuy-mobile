package async.achat;

import android.os.AsyncTask;

import com.ibuy.misa.ibuy.PanierFragment;

import sqlite.PanierDao;

/**
 * Created by misa on 9/4/2017.
 */

public class QuantiteUpdateAsync extends AsyncTask<Integer, Void, String> {
    private PanierFragment panierFragment;
    @Override
    protected String doInBackground(Integer... params) {
        try {
            PanierDao panierDao = new PanierDao(panierFragment.getActivity());
            panierDao.updateQuantiteProduit(params[0], params[1]);
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s!= null) {
            System.out.println("ok");
            panierFragment.initData();
        }
    }

    public PanierFragment getPanierFragment() {
        return panierFragment;
    }

    public void setPanierFragment(PanierFragment panierFragment) {
        this.panierFragment = panierFragment;
    }
}
