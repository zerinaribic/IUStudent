package ius.iustudent.overview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ius.iustudent.R;
import ius.iustudent.adapters.ClassAdapter;
import ius.iustudent.models.Event;

public class TodayScheduleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_today_fragment, container, false);
        ListView classList = (ListView) view.findViewById(R.id.class_list);
        List<Event> events;
        try {
            events = getEvents();
        } catch (IOException e) {
            e.printStackTrace();
            events = new LinkedList<>();
        }
        classList.setAdapter(new ClassAdapter(getActivity(), events));
        classList.setEmptyView(view.findViewById(R.id.empty));
        return view;
    }

    private List<Event> getEvents() throws IOException{
        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        ObjectMapper mapper = new ObjectMapper();
        String examJson = preferences.getString("events", "[]");
        LinkedList<Event> events = mapper.readValue(examJson, new TypeReference<LinkedList<Event>>(){});
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        LinkedList<Event> eventsToday = new LinkedList<>();
        for (Event event: events){
            if(day == event.getDay()){
                eventsToday.add(event);
            }
        }
        Collections.sort(eventsToday, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                if(e1.getStartTime() > e2.getStartTime()){
                    return 1;
                } else if (e1.getStartTime() < e2.getStartTime()){
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return eventsToday;
    }
}
