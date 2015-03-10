package thelist.levelasian.ntnu.thelist;

import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hk on 05.03.15.
 */
public class SplashActivity extends Activity {

    private static String TAG = SplashActivity.class.getName();
    private static long SLEEP_TIME = 3;
    ArrayList<Party> theList;

    private Firebase listFb;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        setContentView(R.layout.splash);
        Firebase.setAndroidContext(this);

        listFb = new Firebase("https://thelist.firebaseio.com/parties");
        theList = new ArrayList<Party>();

        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            loc =gps.getLocation();
        }

        listFb.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map asd = dataSnapshot.getValue(Map.class);
                Party tempParty = new Party(dataSnapshot.getKey());
                tempParty.setDateTime(Timestamp.valueOf(asd.get("dateTime").toString()));
                tempParty.setNumber(asd.get("phoneNumber").toString());
                tempParty.setPartyName(asd.get("partyName").toString());
                tempParty.setHostName(asd.get("hostName").toString());
                tempParty.setLocation(Double.parseDouble(asd.get("alt").toString()),
                        Double.parseDouble(asd.get("lat").toString()),
                        Double.parseDouble(asd.get("lon").toString()));
                theList.add(tempParty);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }
    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            // Start main activity
            Intent intent = new Intent(SplashActivity.this, ListActivity.class);
            intent.putExtra("theList",theList);
            intent.putExtra("location",loc);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }

}
