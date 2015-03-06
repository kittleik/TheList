package thelist.levelasian.ntnu.thelist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ListActivity extends ActionBarActivity {

    private static String TAG = SplashActivity.class.getName();
    private boolean first = true;

    private Button makeParty;

    ArrayList<Party> theList;

    private TextView tw;
    private RecyclerView partyList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent i = getIntent();
        theList = (ArrayList<Party>)i.getSerializableExtra("theList");
        first = false;

        partyList = (RecyclerView) findViewById(R.id.recyclerView);
        partyList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        partyList.setLayoutManager(mLayoutManager);
        tw = (TextView) findViewById(R.id.textView);
        if(theList.isEmpty()){

            tw.setText("Ingen fester i nærheten. Lag fest, fitte.");
        }else {
            tw.setText("");
        }
        mAdapter = new MyAdapter(theList);
        Collections.sort(theList, new DateComp());
        partyList.setAdapter(mAdapter);

        makeParty = (Button) findViewById(R.id.button);

        makeParty.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent callSub = new Intent();
                callSub.setClass(ListActivity.this, makeParty.class);
                startActivity(callSub);
            }

        });

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.attachToRecyclerView(partyList);


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

        return super.onOptionsItemSelected(item);
    }

}

