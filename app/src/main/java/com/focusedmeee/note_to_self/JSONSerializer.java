package com.focusedmeee.note_to_self;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user pc on 30-Dec-16.
 */

public class JSONSerializer {

    private String mFileName;
    private Context mContext;

    public JSONSerializer(String fileName, Context context){
        mFileName = fileName;
        mContext = context;
    }

    public void saveJSONData(List<Note> noteList) throws IOException, JSONException{
        JSONArray jsonArray = new JSONArray();

        for (Note note : noteList)
            jsonArray.put(note.convertToJSON());

        Writer writer = null;
        try{
            OutputStream outputStream = mContext.openFileOutput(mFileName, mContext.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        }finally {
            if (writer != null)
                writer.close();
        }

    }

    public ArrayList<Note> loadJSONData() throws IOException, JSONException{
        ArrayList<Note> notes = new ArrayList<Note>() ;

        BufferedReader reader = null;
        try{
            InputStream inputStream = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
                jsonString.append(line);

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < jsonArray.length(); i++){
                notes.add(new Note(jsonArray.getJSONObject(i)));
            }

        }catch (FileNotFoundException e){
            Toast.makeText(mContext, mFileName + " file not found", Toast.LENGTH_SHORT);
        }
        finally {
            if (reader != null)
                reader.close();
        }

        return notes;
    }
}
