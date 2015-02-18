package thelist.levelasian.ntnu.thelist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ListActivity extends ActionBarActivity {

    TextView list;
    Firebase listFb;
    Button makeParty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Firebase.setAndroidContext(this);

        listFb = new Firebase("https://thelist.firebaseio.com");

        list = (TextView) findViewById(R.id.textView);
        makeParty = (Button) findViewById(R.id.button);

        /**Party asdf = new Party("Thex","Pelle",12345432,19,new Timestamp(2015,2,20,20,0,0,0));
        Party fdsa = new Party("untsunts", "Kåre",65436789,21,new Timestamp(2015,2,19,19,0,0,0));

        listFb = listFb.child("parties");
        Map<String, Party> parties = new HashMap<String, Party>();
        parties.put("x", asdf);
        parties.put("unts", fdsa);

        listFb.setValue(parties);**/

        makeParty.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent callSub = new Intent();
                callSub.setClass(ListActivity.this, makeParty.class);
                startActivity(callSub);
            }

        });

        final String partyName, hostName;
        int phoneNumber, hostAge;
        final Timestamp timestamp;

        listFb = listFb.child("parties");


        listFb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map asd = dataSnapshot.getValue(Map.class);
                Party tempParty = new Party(dataSnapshot.getKey());
                tempParty.setDateTime(Timestamp.valueOf(asd.get("dateTime").toString()));
                tempParty.setNumber(asd.get("phoneNumber").toString());
                tempParty.setPartyName(asd.get("partyName").toString());
                tempParty.setHostName(asd.get("hostName").toString());

                list.append(tempParty.toString()+"\n\n");
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
