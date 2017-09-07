package fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by misa on 8/25/2017.
 */

public class FirebaseInitializer {
    public void initFcm(){
        if(getCurrentToken() == null){
            registryToken();
        }
    }

    /**
     * registry the new client device token
     */
    public void registryToken(){
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    /**
     * return the current token if the device is already registried
     * else return null
     * @return
     */
    public static String getCurrentToken(){
        return FirebaseInstanceId.getInstance().getToken();
    }
}
