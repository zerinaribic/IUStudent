package ius.iustudent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ius.iustudent.adapters.EventAdapter;
import ius.iustudent.models.Event;
import ius.iustudent.models.EventStack;

public class EventListFragment extends Fragment {
    public EventListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_event_list, container, false);
        ListView eventView = (ListView) rootView.findViewById(R.id.event_list);
        List<EventStack> eventStacks = null;
        try {
            eventStacks = getEventStacks();
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        EventAdapter eventAdapter = new EventAdapter(getContext(), R.layout.event_layout, eventStacks);
        eventView.setAdapter(eventAdapter);
        return rootView;
    }

    private List<EventStack> getEventStacks() throws IOException {
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("data", 0);
        String eventJson = sharedPrefs.getString("events", "[]");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Event> events = mapper.readValue(eventJson, new TypeReference<ArrayList<Event>>(){});
        ArrayList<EventStack> eventStacks = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            Event mon = new Event();
            Event tue = new Event();
            Event wed = new Event();
            Event thu = new Event();
            Event fri = new Event();
            Event sat = new Event();
            for(Event event : events){
                if(i >= event.getStartTime() && i < event.getEndTime()){
                    switch (event.getDay()){
                        case 0:
                            mon = event;
                            break;
                        case 1:
                            tue = event;
                            break;
                        case 2:
                            wed = event;
                            break;
                        case 3:
                            thu = event;
                            break;
                        case 4:
                            fri = event;
                            break;
                        case 5:
                            sat = event;
                            break;
                    }
                }
            }
            eventStacks.add(new EventStack(mon, tue, wed, thu, fri ,sat));
        }
        return eventStacks;
    }
}
