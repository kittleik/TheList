package thelist.levelasian.ntnu.thelist;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
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
public class Loading extends Activity {

    private static String TAG = SplashActivity.class.getName();
    private static long SLEEP_TIME = 3;
    ArrayList<Party> theList;

    private Firebase listFb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

        setContentView(R.layout.loading);
        Firebase.setAndroidContext(this);

        listFb = new Firebase("https://thelist.firebaseio.com/parties");
        theList = new ArrayList<Party>();

        listFb.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map asd = dataSnapshot.getValue(Map.class);
                Party tempParty = new Party(dataSnapshot.getKey());
                tempParty.setDateTime(Timestamp.valueOf(asd.get("dateTime").toString()));
                tempParty.setNumber(asd.get("phoneNumber").toString());
                tempParty.setPartyName(asd.get("partyName").toString());
                tempParty.setHostName(asd.get("hostName").toString());
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

        // Start timer and launch main activity
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
            Intent intent = new Intent(Loading.this, ListActivity.class);
            intent.putExtra("theList",theList);
            Loading.this.startActivity(intent);
            Loading.this.finish();
        }
    }

}
