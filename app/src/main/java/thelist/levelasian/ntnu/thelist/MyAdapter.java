package thelist.levelasian.ntnu.thelist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hk on 05.03.15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Party> partyList;
    java.util.Date date= new java.util.Date();
    private Timestamp now = new Timestamp(date.getTime());

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        protected TextView vPartyName;
        protected TextView vHostName;
        protected TextView vPhoneNumber;
        protected TextView vDateTime;

        public ViewHolder(View v) {
            super(v);
            vPartyName = (TextView) v.findViewById(R.id.partyNameView);
            vHostName = (TextView) v.findViewById(R.id.hostNameView);
            vPhoneNumber = (TextView) v.findViewById(R.id.phoneNumberView);
            vDateTime = (TextView) v.findViewById(R.id.dateTimeView);
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v){
            Uri number = Uri.parse("tel:"+ vPhoneNumber.getText());
            Intent intent = new Intent(Intent.ACTION_DIAL, number);
            v.getContext().startActivity(intent);
        }

    }

    public MyAdapter(List<Party> partyList){
        this.partyList = partyList;
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

        holder.vDateTime.setText(p.getDay()+ " " + "klokken "+ p.getTime());
        holder.vPhoneNumber.setText(p.getNumber());
        holder.vHostName.setText(p.getHostName());
        holder.vPartyName.setText(p.getPartyName());
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }
}
