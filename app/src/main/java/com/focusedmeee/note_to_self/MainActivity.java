package com.focusedmeee.note_to_self;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_TO_SELF_PREFS_ID = "Note to self";

    private NoteAdapter mNoteAdapter;
    private boolean mSound;
    private int mAnimOption;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ((Button)findViewById(R.id.btnShowNote)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogShowNote dialog = new DialogShowNote();
//
//                dialog.sendNoteSelected(mTempNote);
//                dialog.show(getSupportFragmentManager(), "123");
//            }
//        });

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
    public void onResume(){
        super.onResume();

        mPrefs = this.getSharedPreferences(NOTE_TO_SELF_PREFS_ID, MODE_PRIVATE);
        mSound = mPrefs.getBoolean(SettingsActivity.SOUND_KEY, true);
        mAnimOption = mPrefs.getInt(SettingsActivity.ANIM_OPTION_KEY, SettingsActivity.FAST);
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
}
