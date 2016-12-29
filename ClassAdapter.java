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


public class ClassAdapter extends ArrayAdapter<Event> {
    public ClassAdapter(Context context, List<Event> objects) {
        super(context, R.layout.class_list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_list_item, null);
        }
        Event event = getItem(position);
        TextView className = (TextView) convertView.findViewById(R.id.class_name);
        TextView startTime = (TextView) convertView.findViewById(R.id.adapter_start_time);
        TextView endTime = (TextView) convertView.findViewById(R.id.adapter_end_time);
        TextView day = (TextView) convertView.findViewById(R.id.class_day);
        className.setText(event.getName());
        startTime.setText(String.valueOf(event.getStartTime()) + ":00");
        endTime.setText(String.valueOf(event.getEndTime()) + ":00");
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        day.setText(days[event.getDay()]);
        return convertView;
    }

}
