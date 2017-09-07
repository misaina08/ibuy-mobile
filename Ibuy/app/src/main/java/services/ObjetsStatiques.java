package services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by misa on 8/24/2017.
 */

public class ObjetsStatiques {
    private static List<String> categories;
    private static List<String> magasins;

    public void init(){
        categories = new ArrayList<String>();
        magasins = new ArrayList<String>();

        categories.add("Hightech");
        categories.add("Vetement");
        categories.add("Cosmetique");

        magasins.add("Samsung");
        magasins.add("Shopstyle");
    }

    public static List<String> getCategories() {
        return categories;
    }

    public static void setCategories(List<String> categories) {
        ObjetsStatiques.categories = categories;
    }

    public static List<String> getMagasins() {
        return magasins;
    }

    public static void setMagasins(List<String> magasins) {
        ObjetsStatiques.magasins = magasins;
    }
}
