package dev.edmt.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText input_title,input_content;
    private ListView list_data;
    private ProgressBar circular_progress;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private List<Note> list_notes = new ArrayList<>();

    private Note selectedNote; // hold user when we select item in listview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Firebase Demo");
        setSupportActionBar(toolbar);

        //Control
        circular_progress = (ProgressBar)findViewById(R.id.circular_progress);
        input_title = (EditText)findViewById(R.id.title);
        input_content = (EditText)findViewById(R.id.content);
        list_data = (ListView)findViewById(R.id.list_data);
        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = (Note)adapterView.getItemAtPosition(i);
                selectedNote = note;
                input_title.setText(note.getTitle());
                input_content.setText(note.getContent());
            }
        });


        //Firebase
        initFirebase();
        addEventFirebaseListener();


    }

    private void addEventFirebaseListener() {
        //Progressing
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(list_notes.size() > 0)
                    list_notes.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Note note = postSnapshot.getValue(Note.class);
                    list_notes.add(note);
                }
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this,list_notes);
                list_data.setAdapter(adapter);

                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference  = mFirebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add)
        {
            createNote();
        }
        else if(item.getItemId() == R.id.menu_save)
        {
            if(input_title.getText().toString().equals("") ||input_content.toString().equals("")){}
            else {
                Note note = new Note(selectedNote.getNoteid(), input_title.getText().toString(), input_content.getText().toString());
                updateNote(note);
            }
        }
        else if(item.getItemId() == R.id.menu_remove){
            deleteNote(selectedNote);
        }
        return true;
    }

    private void deleteNote (Note selectedNote) {
        mDatabaseReference.child("notes").child(selectedNote.getNoteid()).removeValue();
        clearEditText();
    }

    private void updateNote(Note note) {
        mDatabaseReference.child("notes").child(note.getNoteid()).child("title").setValue(note.getTitle());
        mDatabaseReference.child("notes").child(note.getNoteid()).child("content").setValue(note.getContent());
        clearEditText();
    }

    private void createNote() {
        Note note = new Note(UUID.randomUUID().toString(),input_title.getText().toString(),input_content.getText().toString());
        mDatabaseReference.child("notes").child(note.getNoteid()).setValue(note);
        clearEditText();

    }

    private void clearEditText() {
        input_title.setText("");
        input_content.setText("");
    }
}
