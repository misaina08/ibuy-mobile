package services;

import android.content.Context;
import android.content.SharedPreferences;

import modele.Client;
import utilitaire.WSUtil;

/**
 * Created by misa on 8/23/2017.
 */

public class SessionManager {
    public Context context;
    public static String PREF_USER = "UserSharedPref";
    public static String KEY_USER = "user";

    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        setContext(context);
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences(PREF_USER, 0);
        editor = sharedPreferences.edit();
    }

    public void createUserSession(Client client) throws Exception {
        WSUtil wsUtil = new WSUtil();
        try {
            editor.putString(KEY_USER, wsUtil.parseObjectToJson(client));
            editor.commit();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Client getClientConnected() throws Exception {
        WSUtil wsUtil = new WSUtil();
        Client res = (Client) wsUtil.parseJsonObjectStringToObject(getSharedPreferences().getString(KEY_USER, "{}"), new Client());
        return res;

    }

    public void destroyUserSession() throws Exception {
        try {
            editor.remove(KEY_USER);
            editor.commit();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean estConnecte() {
        if (SessionManager.getSharedPreferences().getString(SessionManager.KEY_USER, null) != null) {
            return true;
        }
        return false;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        SessionManager.sharedPreferences = sharedPreferences;
    }


    public SharedPreferences.Editor getEditor() {
        return editor;
    }


}
