package thelist.levelasian.ntnu.thelist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by hk on 14.02.15.
 */
public class makeParty extends ActionBarActivity {
    ArrayList<Party> theList;

    private Firebase listFb2;
    EditText eName, eNr, ePartyName, eDate;
    Button create, cancel;
    Firebase listFb;
    static final int DATE_DIALOG_ID = 100;
    static final int TIME_DIALOG_ID = 101;

    private TextView text_date;
    private TextView text_time;

    private int hour, min;

    private int year;
    private int month;
    private int day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeparty);
        Firebase.setAndroidContext(this);
        listFb = new Firebase("https://thelist.firebaseio.com");

        Firebase.setAndroidContext(this);

        listFb2 = new Firebase("https://thelist.firebaseio.com/parties");
        theList = new ArrayList<Party>();

        listFb2.addChildEventListener(new ChildEventListener() {

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

        eName = (EditText) findViewById(R.id.editText);
        eNr = (EditText) findViewById(R.id.editText2);
        ePartyName = (EditText) findViewById(R.id.editText3);
        text_date = (TextView) findViewById(R.id.textView2);
        text_time = (TextView) findViewById(R.id.textView3);

        create = (Button) findViewById(R.id.button2);
        cancel = (Button) findViewById(R.id.button3);
        listFb = listFb.child("parties");
        Button btn = (Button) findViewById(R.id.button4);
        Button btnTime = (Button) findViewById(R.id.button5);
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String dayString, monthString;
        if (month+1<10) monthString = "0" + Integer.toString(month+1);
        else monthString = Integer.toString(month+1);
        if (day<10) dayString = "0" + Integer.toString(day);
        else dayString = Integer.toString(day);
        text_date.setText(Integer.toString(year)+"-"+monthString +"-"+ dayString);
        text_time.setText(new StringBuilder()
                        .append(20).append(":").append("00"));

        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                showDialog(DATE_DIALOG_ID);
            }
        });
        btnTime.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        create.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eName.getText().toString().isEmpty()){
                    eName.setText("You need to specify a name");
                }
                else if(ePartyName.getText().toString().isEmpty()){
                    ePartyName.setText("You need to specify a party name");
                }
                else {
                    Map<String, String> parties = new HashMap<String, String>();
                    parties.put("dateTime", text_date.getText().toString() + " " + text_time.getText().toString() + ":00.000");
                    parties.put("hostName", eName.getText().toString());
                    parties.put("phoneNumber", eNr.getText().toString());
                    parties.put("partyName", ePartyName.getText().toString());

                    listFb.push().setValue(parties);
                    Intent intent = new Intent();
                    intent.putExtra("theList", theList);
                    intent.setClass(makeParty.this, Loading.class);
                    startActivity(intent);
                }
            }

        });
        cancel.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("theList",theList);
                intent.setClass(makeParty.this, ListActivity.class);
                startActivity(intent);
            }

        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, min, true);
        }
        return null;

    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            String dayString, monthString;
            if (month+1<10) monthString = "0" + Integer.toString(month+1);
            else monthString = Integer.toString(month+1);
            if (day<10) dayString = "0" + Integer.toString(day);
            else dayString = Integer.toString(day);
            text_date.setText(Integer.toString(year)+"-"+monthString +"-"+ dayString);
        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute){
            hour = selectedHour;
            min = selectedMinute;
            String minString, hourString;
            if (selectedHour<10) hourString = "0" + Integer.toString(hour);
            else hourString = Integer.toString(hour);
            if (selectedMinute<10) minString = "0" + Integer.toString(min);
            else minString = Integer.toString(min);
            text_time.setText(hourString + ":" + minString);
        }
    };


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


