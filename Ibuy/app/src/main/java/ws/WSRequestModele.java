package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import modele.BaseModele;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utilitaire.WSUtil;

/**
 * Created by Misaina on 11/03/2017.
 */

public class WSRequestModele extends WebServiceModele {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    public List<BaseModele> get(String url, BaseModele baseModele) throws Exception {
        List<BaseModele> result=null;
        try{
            result=new ArrayList<BaseModele>();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            response = client.newCall(request).execute();
            String jsonStr=response.body().string();
            WSUtil wsUtil=new WSUtil();
            result=wsUtil.parseJsonArrayToList(jsonStr, baseModele);
            return result;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public BaseModele getOne(String url, BaseModele baseModele) throws Exception {
        try{
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            response = client.newCall(request).execute();
            String jsonStr=response.body().string();
            WSUtil wsUtil=new WSUtil();
            return wsUtil.parseJsonObjectStringToObject(jsonStr, baseModele);
        }
        catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public String post(String url, BaseModele baseModele)  throws Exception{
        WSUtil wsUtil=new WSUtil();
        try{
            String json=wsUtil.parseObjectToJson(baseModele);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();
            System.out.println("json"+json);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String result=response.body().string();
            int code=response.code();
            System.out.println("result = "+result);
            System.out.println("code = "+code);
            if(code<200 && code>299){
                throw new Exception("Erreur d'insertion");
            }
            return result;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public String postList(String url, List<BaseModele> listeBaseModele) throws Exception {
        WSUtil wsUtil=new WSUtil();
        try{
            String json=wsUtil.listToArrayJson(listeBaseModele);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();
            System.out.println("json"+json);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String result=response.body().string();
            int code=response.code();
            System.out.println("result = "+result);
            System.out.println("code = "+code);
            if(code!=200){
                throw new Exception("Erreur d'insertion");
            }
            return result;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void put(String url, BaseModele baseModele) throws Exception {

    }

    @Override
    public void delete(String url, BaseModele baseModele)  throws Exception{

    }

    @Override
    public String getContent(String url) throws Exception {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            response = client.newCall(request).execute();
            String jsonStr=response.body().string();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
