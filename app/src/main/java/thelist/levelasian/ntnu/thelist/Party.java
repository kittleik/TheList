package thelist.levelasian.ntnu.thelist;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by hk on 14.02.15.
 */
public class Party implements Serializable{
    private String partyName;
    private String hostName;
    private Timestamp dateTime;
    private String phoneNumber;
    private String id;

    public Party(String id){
        this.partyName = "Not defined";
        this.hostName = "Not defined";
        this.phoneNumber = "Not defined";
        this.dateTime = new Timestamp(Long.MIN_VALUE);
        this.id = id;
    }
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
    public Timestamp getDateTime(){
        return dateTime;
    }
    public String getDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        String b ="";
        int a = cal.get(java.util.Calendar.DAY_OF_WEEK);
        Integer datei = new Integer(dateTime.getDate());
        switch(a){
            case 2: b = "Mandag"+" "+datei.toString()+".";
                break;
            case 3: b = "Tirsdag"+" "+datei.toString()+".";
                break;
            case 4: b = "Onsdag"+" "+datei.toString()+".";
                break;
            case 5: b = "Torsdag"+" "+datei.toString()+".";
                break;
            case 6: b = "Fredag"+" "+datei.toString()+".";
                break;
            case 7: b = "Lørdag"+" "+datei.toString()+".";
                break;
            case 1: b = "Søndag"+" "+datei.toString()+".";
                break;
        }
        java.util.Date date= new java.util.Date();
        Timestamp c = new Timestamp(date.getTime());

        if (dateTime.getDate()==c.getDate())b = "I dag";

        return b;
    }
    public String getTime(){
        String res;
        Integer temp = new Integer(dateTime.getHours());
        res = temp.toString();
        temp = dateTime.getMinutes();
        String tempString = "";
        if( temp < 10)tempString="0";
        tempString = tempString+temp.toString();
        res = res+":"+tempString;
        return res;
    }
    public void setHostName(String hostName){
        this.hostName = hostName;
    }
    public String getHostName(){
        return hostName;
    }
    public void setPartyName(String partyName){
        this.partyName = partyName;
    }
    public String getPartyName(){
        return partyName;
    }
    public void setNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getNumber(){
        return phoneNumber;
    }
    @Override
    public String toString(){
        return ("Party name: "+partyName+"\n"+"Host name: "+hostName+"\n"+"Phone nr: "+phoneNumber+"\n"+"Time: "+dateTime);
    }
}