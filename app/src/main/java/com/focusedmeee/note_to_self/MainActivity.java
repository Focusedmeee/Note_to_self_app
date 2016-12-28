package com.focusedmeee.note_to_self;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;


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

        ListView listNote = (ListView)findViewById(R.id.listView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getSupportFragmentManager(), "");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewNote(Note newNote) {
        mNoteAdapter.addNote(newNote);
    }
}
