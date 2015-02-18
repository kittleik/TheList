package thelist.levelasian.ntnu.thelist;

import java.sql.Timestamp;

/**
 * Created by hk on 14.02.15.
 */
public class Party {
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