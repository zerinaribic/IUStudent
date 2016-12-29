package ius.iustudent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ius.iustudent.adapters.ClassAdapter;
import ius.iustudent.models.Event;

public class ClassListFragment extends Fragment{
    private List<Event> events;
    ListView classList;
    public ClassListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_list_view, container, false);
        classList = (ListView) rootView.findViewById(R.id.class_list);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        try {
            events = getEvents();
        } catch (IOException e) {
            events = new ArrayList<>();
            Toast.makeText(getContext(), "Error reading event data", Toast.LENGTH_SHORT).show();
        }
        classList.setAdapter(new ClassAdapter(getContext(), events));
        classList.setEmptyView(rootView.findViewById(R.id.empty));
        registerForContextMenu(classList);
        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = (Event) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity(), ShowClassActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewEventActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResume();
    }

    @Override
    public void onResume() {
        try {
            events.clear();
            events.addAll(getEvents());
            ClassAdapter adapter = (ClassAdapter) classList.getAdapter();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error reading event data", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.class_list){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.class_list_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                events.remove(info.position);
                ClassAdapter adapter = (ClassAdapter) classList.getAdapter();
                adapter.notifyDataSetChanged();
                try {
                    saveEvents();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private List<Event> getEvents() throws IOException {
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("data", 0);
        String eventJson = sharedPrefs.getString("events", "[]");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(eventJson, new TypeReference<LinkedList<Event>>(){});
    }

    private void saveEvents() throws IOException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", 0);
        ObjectMapper mapper = new ObjectMapper();
        String newJson = mapper.writeValueAsString(events);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("events", newJson);
        editor.apply();
    }
}