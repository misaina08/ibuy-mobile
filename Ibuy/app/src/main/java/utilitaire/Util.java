package utilitaire;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static Double meterToKm(Double metre){
        return new Double(metre * 0.001);
    }
    public static Date stringToDate(String dateString) throws Exception{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        date = df.parse(dateString);
        return date;
    }

    public String timeToString(Time time) throws Exception
    {
        try {
            return addPrefix(2, time.getHours()+"","0")+":"+addPrefix(2, time.getMinutes()+"","0");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public String escapeNullString(String st) {
        if (st == null) {
            return "";
        }
        return st;
    }

    public Time toTime(String s) throws Exception {

        try {
            String[] j = s.split(":");

            return new Time(new Integer(j[0]), new Integer(j[1]), 0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Integer timeToMinute(Time t) {
        return t.getHours() * 60 + t.getMinutes();
    }

    //myLengt : 4 , mot : 34, pref : 0 => 0034
    public String addPrefix(Integer myLength, String mot, String pref) {
        String res = mot;
        for (int i = 0; i < myLength - mot.length(); i++) {
            res = pref + "" + res;
        }
        return res;
    }

    public Field[] getFields(Class obj) {
        Field[] fieldObj = obj.getDeclaredFields();
        return fieldObj;
    }
    public Field[] getAllFields(Class obj) {
        Field[] res = null;
        Field[] fieldObj = obj.getDeclaredFields();
        Field[] fieldSuper = obj.getSuperclass().getDeclaredFields();

        res = new Field[fieldObj.length + fieldSuper.length];

        int indice = 0;
        int i = 0;
        for (i = 0; i < fieldSuper.length; i++) {
            res[i] = fieldSuper[i];
        }
        indice = i;
        for (int a = 0; a < fieldObj.length; a++) {
            res[indice] = fieldObj[a];
            indice++;
            }
        

        return res;
    }

//	date : entr�e string => mm/jj/aaaa
//	v�rification jj/mm/aaaa si valide, => mm/jj/aaaa
    public String checkDate(String date) throws IllegalArgumentException, Exception {
        String[] split = date.split("/");
        if (new Integer(split[0]).intValue() > 31) {
            throw new Exception("Jour invalide");
        }
        if (new Integer(split[1]).intValue() > 12) {
            throw new Exception("Mois invalide");
        }

        return split[1] + "/" + split[0] + "/" + split[2];
    }


//	date => jj/mm/yyyy
    public static String dateToString(Date d) {
        String day = d.getDate() + "";
        String mois = (d.getMonth() + 1) + "";
        if (d.getDate() < 10) {
            day = "0" + d.getDate();
        }
        if (d.getMonth() < 9) {
            mois = "0" + (d.getMonth() + 1);
        }

        return day + "/" + mois + "/" + (d.getYear() + 1900);
    }

    public int calculPage(int nbData, int nbParPage) {
        int reponse = 0;
        float inter = (float) nbData / (float) nbParPage;

        if (inter - ((int) inter) > 0.0) {
            reponse = nbData / nbParPage + 1;
        } else {
            reponse = nbData / nbParPage;
        }
        return reponse;
    }

    public String premierMaj(String s) {
        char[] lettreChar = s.toCharArray();
        String pl = String.valueOf(lettreChar[0]).toUpperCase();
        lettreChar[0] = pl.toCharArray()[0];
        return String.valueOf(lettreChar);
    }

//	g�n�rer valeur par d�faut si value==null ou value vide
    public Object setDefaultValueObjectNull(Object objet, String value) {
        Object res = value;
        String typeObjet = objet.getClass().getName();
//		objet=value;
        if (value.toString().compareTo("") == 0 || value.toString().compareTo("jj/mm/aaaa") == 0) {

            if (typeObjet.compareTo("java.lang.String") == 0) {
                res = "-";
            } else if (typeObjet.compareTo("java.util.Date") == 0) {
                Date d = new Date();
                d.setMonth(1);
                d.setYear(1900);
                d.setDate(1);
                d.setMinutes(0);
                d.setHours(0);
                d.setSeconds(0);
                res = "1/2/3800";
            } else if (typeObjet.compareTo("java.lang.Integer") == 0) {
                res = 123456;
            } else if (typeObjet.compareTo("java.lang.Double") == 0) {
                res = 123456.0;
            } else {
                res = null;
            }
        }
        return res;
    }

    public Object setValueInString(Field field, String value) throws Exception
    {
        try {
            String typeObjet = field.getType().getName();


            if (typeObjet.compareTo("java.lang.String") == 0) {
                return value;
            } else if (typeObjet.compareTo("java.util.Date") == 0) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(format1.parse(value));
                Date d = c.getTime();
                return d;
            } else if (typeObjet.compareTo("java.lang.Integer") == 0) {
                if(value == null || value.compareToIgnoreCase("null")==0){
                    return new Integer(0);
                }
                return new Integer(value);
            } else if (typeObjet.compareTo("java.lang.Double") == 0) {
                if(value == null || value.compareToIgnoreCase("null")==0){
                    return new Double(0);
                }
                return new Double(value);
            }
            else if (typeObjet.compareTo("java.lang.Float") == 0) {
                return new Float(value);
            }
            else if (typeObjet.compareTo("java.lang.Boolean") == 0) {
                if(value.compareToIgnoreCase("1")==0) {
                    return new Boolean("true");
                }
                else return new Boolean("false");

            }else if (typeObjet.compareTo("java.sql.Time")==0){
                Time time=new Time(0);
                String[] splitTime=value.split(":");
                time.setHours(new Integer(splitTime[0]));
                time.setMinutes(new Integer(splitTime[1]));
                time.setSeconds(new Integer(splitTime[2]));
                return time;
            }
            else {
                return value;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public boolean isDateOk(String s) {
        try {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            formatter.parse(s);
        } catch (ParseException ex) {

            return false;
        }
        return true;
    }
   
}
