package thelist.levelasian.ntnu.thelist;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hk on 18.03.15.
 */
public class PartyInfo extends Activity {
    private Party p;
    private Location loc;
    TextView pNText;
    Button call;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_info);

        Intent i = getIntent();
        p = (Party)i.getSerializableExtra("party");
        loc = (Location)i.getSerializableExtra("location");

        pNText = (TextView) findViewById(R.id.partyNameView);
        pNText.setText(p.getPartyName());
        call = (Button) findViewById(R.id.callButton);
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + p.getNumber());
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                PartyInfo.this.startActivity(intent);
            }
        });

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute("http://storage.googleapis.com/party_pictures/partu.gif");
    }
}