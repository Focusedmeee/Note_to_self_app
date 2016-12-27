package com.focusedmeee.note_to_self;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user pc on 28-Dec-16.
 */

public class DialogShowNote extends DialogFragment {

    private Note mNote;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_show_note, null);

        TextView txtTitle = (TextView)dialogView.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView)dialogView.findViewById(R.id.txtDescription);
        ImageView imageViewImportant = (ImageView)dialogView.findViewById(R.id.imageViewImportant);
        ImageView imageViewIdea = (ImageView)dialogView.findViewById(R.id.imageViewIdea);
        ImageView imageViewTodo = (ImageView)dialogView.findViewById(R.id.imageViewTodo);

        txtTitle.setText(mNote.getTitle());
        txtDescription.setText(mNote.getDescription());
        imageViewImportant.setVisibility((mNote.isImportant() ? View.VISIBLE : View.GONE));
        imageViewIdea.setVisibility((mNote.isIdea() ? View.VISIBLE : View.GONE));
        imageViewTodo.setVisibility((mNote.isTodo() ? View.VISIBLE : View.GONE));

        ((Button)dialogView.findViewById(R.id.BtnOK)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(dialogView);

        return builder.create();
    }

    public void sendNoteSelected(Note noteSelected){
        mNote = noteSelected;
    }
}
