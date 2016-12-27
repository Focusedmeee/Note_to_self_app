package com.focusedmeee.note_to_self;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Note mTempNote = new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btnShowNote)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShowNote dialog = new DialogShowNote();

                dialog.sendNoteSelected(mTempNote);
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
        if(item.getItemId() == R.id.action_add){
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getSupportFragmentManager(), "");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewNote(Note newNote){
        mTempNote = newNote;
    }
}
