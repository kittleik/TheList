package thelist.levelasian.ntnu.thelist;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hk on 05.03.15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Party> partyList;
    java.util.Date date= new java.util.Date();
    private Timestamp now = new Timestamp(date.getTime());
    private Location loc;
    static OnItemClickListener mItemClickListener;
    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        protected TextView vPartyName;
        protected TextView vDateTime;
        protected ImageView vPartyImage;
        protected TextView vDistance;

        public ViewHolder(View v) {
            super(v);
            vPartyName = (TextView) v.findViewById(R.id.partyNameView);
            vDateTime = (TextView) v.findViewById(R.id.dateTimeView);
            vPartyImage = (ImageView) v.findViewById(R.id.imageView);
            vDistance = (TextView) v.findViewById(R.id.distanceView);
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v){
           // Uri number = Uri.parse("tel:"+ vPhoneNumber.getText());
           // Intent intent = new Intent(Intent.ACTION_DIAL, number);
            // v.getContext().startActivity(intent);
            mItemClickListener.onItemClick(v, getPosition());
        }

    }

    public MyAdapter(List<Party> partyList, Location loc){
        this.loc = loc;
        this.partyList = partyList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public RecyclerView.Adapter SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
        return this;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Party p = partyList.get(i);
        Location loc2 = new Location(p.getPartyName());
        loc2.setAltitude(p.getAlt());
        loc2.setLongitude(p.getLon());
        loc2.setTime(p.getTime());
        loc2.setLatitude(p.getLat());
        float dist =0;
        if(loc != null){
            dist = loc.distanceTo(loc2);
            holder.vDistance.setText(Float.toString(dist)+" m");
        }else{
            holder.vDistance.setText("");
        }

        holder.vDateTime.setText(p.getDay()+ " "+ p.getClockTime());
        holder.vPartyName.setText(p.getPartyName());
        new DownloadImageTask(holder.vPartyImage)
                .execute("http://storage.googleapis.com/party_pictures/partu.gif");
    }

    @Override
    public int getItemCount() {

        return partyList.size();
    }

}


