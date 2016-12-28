package com.focusedmeee.note_to_self;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user pc on 28-Dec-16.
 */

public class NoteAdapter extends BaseAdapter {

    List<Note> noteList = new ArrayList<Note>();

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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView txtDescription = (TextView)convertView.findViewById(R.id.li_txtDescription);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.li_txtTitle);
        ImageView imgImportant = (ImageView)convertView.findViewById(R.id.li_imgImportant);
        ImageView imgTodo = (ImageView)convertView.findViewById(R.id.li_imgTodo);
        ImageView imgIdea = (ImageView)convertView.findViewById(R.id.li_imgIdea);

        Note currentNote = noteList.get(position);

        imgImportant.setVisibility((currentNote.isImportant()) ? View.VISIBLE : View.GONE);
        imgTodo.setVisibility((currentNote.isTodo()) ? View.VISIBLE : View.GONE);
        imgIdea.setVisibility((currentNote.isIdea()) ? View.VISIBLE : View.GONE);
        txtDescription.setText(currentNote.getDescription());
        txtTitle.setText(currentNote.getTitle());


        return convertView;
    }

    public void addNote(Note n){
        noteList.add(n);
        notifyDataSetChanged();
    }
}
