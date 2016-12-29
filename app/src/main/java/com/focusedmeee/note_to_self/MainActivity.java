package com.focusedmeee.note_to_self;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Focusedmeee";
    public static final String NOTE_TO_SELF_PREFS_ID = "Note to self";

    private NoteAdapter mNoteAdapter;
    private boolean mSound;
    private int mAnimOption;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteAdapter = new NoteAdapter();

        ListView listNote = (ListView) findViewById(R.id.listView);
        listNote.setAdapter(mNoteAdapter);
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note tempNote = mNoteAdapter.getItem(position);

                DialogShowNote dialog = new DialogShowNote();
                dialog.sendNoteSelected(tempNote);
                dialog.show(getSupportFragmentManager(), "123");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mPrefs = this.getSharedPreferences(NOTE_TO_SELF_PREFS_ID, MODE_PRIVATE);
        mSound = mPrefs.getBoolean(SettingsActivity.SOUND_KEY, true);
        mAnimOption = mPrefs.getInt(SettingsActivity.ANIM_OPTION_KEY, SettingsActivity.FAST);

        Toast.makeText(this, "onResume executed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();

        mNoteAdapter.saveNotes();

        Toast.makeText(this, "onPause executed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                DialogNewNote dialog = new DialogNewNote();
                dialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewNote(Note newNote) {
        mNoteAdapter.addNote(newNote);
    }


    public class NoteAdapter extends BaseAdapter {


        private JSONSerializer mSerializer;
        List<Note> noteList = new ArrayList<Note>();

        public NoteAdapter() {
            mSerializer = new JSONSerializer("NoteToSelf.json", MainActivity.this.getApplicationContext());

            try {
                noteList = mSerializer.loadJSONData();
            } catch (Exception e) {
                noteList = new ArrayList<Note>();

                Toast.makeText(MainActivity.this,"Error loading notes: " + e.getMessage() , Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error loading notes " + e.getMessage());
            }
        }

        @Override
        public int getCount() {
            return noteList.size();
        }

        @Override
        public Note getItem(int position) {
            return noteList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }

            TextView txtDescription = (TextView) convertView.findViewById(R.id.li_txtDescription);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.li_txtTitle);
            ImageView imgImportant = (ImageView) convertView.findViewById(R.id.li_imgImportant);
            ImageView imgTodo = (ImageView) convertView.findViewById(R.id.li_imgTodo);
            ImageView imgIdea = (ImageView) convertView.findViewById(R.id.li_imgIdea);

            Note currentNote = noteList.get(position);

            imgImportant.setVisibility((currentNote.isImportant()) ? View.VISIBLE : View.GONE);
            imgTodo.setVisibility((currentNote.isTodo()) ? View.VISIBLE : View.GONE);
            imgIdea.setVisibility((currentNote.isIdea()) ? View.VISIBLE : View.GONE);
            txtDescription.setText(currentNote.getDescription());
            txtTitle.setText(currentNote.getTitle());


            return convertView;
        }

        public void addNote(Note n) {
            noteList.add(n);
            notifyDataSetChanged();
        }

        public void saveNotes() {
            try {
                mSerializer.saveJSONData(noteList);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,"Error saving notes " + e.getMessage() , Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error saving notes " + e.getMessage());
            }
        }
    }
}
