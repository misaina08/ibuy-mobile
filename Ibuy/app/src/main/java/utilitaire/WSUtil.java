package utilitaire;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import modele.BaseModele;

/**
 * Created by Misaina on 08/03/2017.
 */

public class WSUtil {
    private static String urlServer = "http://192.168.10.3:9090/Transversal-war/rest";
    private static String urlPhotoProduit = "http://192.168.10.3:9090/Transversal-war/resources/bo/img/";

    public String parseObjectToJson(BaseModele baseModele) throws Exception {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            return gson.toJson(baseModele);
        } catch (Exception e) {
            throw e;
        }
    }

    /*
    * [{"nom":test1}, {"nom":test2}]
    * => List<Modele>
     */
    public List<BaseModele> parseJsonArrayToList(String jsonArrayStr, BaseModele baseModele) throws Exception {
        List<BaseModele> result = null;
        try {
            result = new ArrayList<BaseModele>();
            System.out.println("--json-- "+jsonArrayStr);
            JSONArray jsonArray = new JSONArray(jsonArrayStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(parseJsonObjectToObject(jsonObject, baseModele));
            }
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /*
    * {"nom":test}
    * => Modele
     */
    public BaseModele parseJsonObjectToObject(JSONObject jsonObject, BaseModele baseModele) throws Exception {
        Field[] fieldsBaseModele = null;
        BaseModele result = baseModele.getClass().newInstance();
        Util util = null;
        try {
            util = new Util();
            fieldsBaseModele = baseModele.getClass().getDeclaredFields();
            //Log.i("taille ", fieldsBaseModele.length+"");
            for (int i = 0; i < fieldsBaseModele.length; i++) {
                if (fieldsBaseModele[i].getName().compareToIgnoreCase("serialVersionUID") == 0
                        || fieldsBaseModele[i].getName().compareToIgnoreCase("$change") == 0) {
                    continue;
                }
                Class[] type = new Class[1];
                type[0] = fieldsBaseModele[i].getType();

                Method method = result.getClass().getMethod("set" + util.premierMaj(fieldsBaseModele[i].getName()), type);
                Object param = util.setValueInString(fieldsBaseModele[i], jsonObject.getString(fieldsBaseModele[i].getName()));
                Object objet = method.invoke(result, param);
            }

            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public BaseModele parseJsonObjectStringToObject(String jsonStr, BaseModele baseModele) throws Exception {
        Field[] fieldsBaseModele = null;
        BaseModele result = baseModele.getClass().newInstance();
        Util util = null;
        try {
            util = new Util();
            fieldsBaseModele = baseModele.getClass().getDeclaredFields();
            JSONObject jsonObject = new JSONObject(jsonStr);
            for (int i = 0; i < fieldsBaseModele.length; i++) {

                if (fieldsBaseModele[i].getName().compareToIgnoreCase("serialVersionUID") == 0
                        || fieldsBaseModele[i].getName().compareToIgnoreCase("$change") == 0) {
                    continue;
                }
                if (fieldsBaseModele[i].getType().getName().compareToIgnoreCase("java.util.List") == 0) {
                    continue;
                }

                Class[] type = new Class[1];
                type[0] = fieldsBaseModele[i].getType();

                Method method = result.getClass().getMethod("set" + util.premierMaj(fieldsBaseModele[i].getName()), type);
                Object param = util.setValueInString(fieldsBaseModele[i], jsonObject.getString(fieldsBaseModele[i].getName()));
                Object objet = method.invoke(result, param);
            }

            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String listToArrayJson(List<BaseModele> liste) throws Exception {
        String res = "[";
        int i = 1;
        for (BaseModele m : liste) {
            res += parseObjectToJson(m);
            if (i < liste.size()) {
                res += ",";
            }
            i++;
        }
        return res + "]";
    }

    public static String getUrlPhotoProduit() {
        return urlPhotoProduit;
    }

    public static void setUrlPhotoProduit(String urlPhotoProduit) {
        WSUtil.urlPhotoProduit = urlPhotoProduit;
    }

    public static String getUrlServer() {
        return urlServer;
    }

    public static void setUrlServer(String urlServer) {
        WSUtil.urlServer = urlServer;
    }
}
