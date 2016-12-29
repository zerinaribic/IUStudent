package ius.iustudent.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ius.iustudent.R;
import ius.iustudent.models.Event;
import ius.iustudent.models.EventStack;

public class EventAdapter extends ArrayAdapter<EventStack> {

    public EventAdapter(Context context, int res,List<EventStack> objects) {
        super(context, res, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_layout, null);
        }
        Event[] events = getItem(position).getEvents();
        TextView hour = (TextView) convertView.findViewById(R.id.adapter_hour);
        TextView mon = (TextView) convertView.findViewById(R.id.adapter_monday);
        TextView tue = (TextView) convertView.findViewById(R.id.adapter_tuesday);
        TextView wed = (TextView) convertView.findViewById(R.id.adapter_wednesday);
        TextView thu = (TextView) convertView.findViewById(R.id.adapter_thursday);
        TextView fri = (TextView) convertView.findViewById(R.id.adapter_friday);
        TextView sat = (TextView) convertView.findViewById(R.id.adapter_saturday);
        hour.setText(String.valueOf(position));
        mon.setText(events[0].getName());
        if(!events[0].isEmpty()){
            mon.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            mon.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        tue.setText(events[1].getName());
        if(!events[1].isEmpty()){
            tue.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            tue.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        wed.setText(events[2].getName());
        if(!events[2].isEmpty()){
            wed.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            wed.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        thu.setText(events[3].getName());
        if(!events[3].isEmpty()){
            thu.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            thu.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        fri.setText(events[4].getName());
        if(!events[4].isEmpty()){
            fri.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            fri.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        sat.setText(events[5].getName());
        if(!events[5].isEmpty()){
            sat.setBackgroundColor(convertView.getResources().getColor(R.color.bpBlue));
        } else {
            sat.setBackgroundColor(convertView.getResources().getColor(R.color.bpWhite));
        }
        return convertView;
    }
}
