package pranjal.learning.organdonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import static pranjal.learning.organdonation.dashboard_doc.bool;

public class donor extends AppCompatActivity {

    Context context;

    RecyclerView recyclerView;
    myAdapter adapter;
    FloatingActionButton fb;

    TextView message;
    SearchView searchView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("List of Donors");

        searchView = findViewById(R.id.searchView);
        context = getApplicationContext();
    /////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////// retrieve data form firebase /////////////////////////////////////
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("donor_app"), model.class)
                        .build();

        // it will be directed to constructor of myAdapter
        adapter = new myAdapter(options, context);

        // creates recyclerView and update it with data
        recyclerView.setAdapter(adapter);

        // handling "add" button click
        fb = findViewById(R.id.fadd);
        if(bool)
        {
            fb.setVisibility(View.INVISIBLE);
        }
        else
        {
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getApplicationContext(), addDataD.class));
                }
            });
        }

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("toolbar");
                message.setText("");

                searchView.setQueryHint("Search organ");

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    // while we are typing
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        processSearch(query);

                        return false;
                    }

                    // when we complete our typing
                    @Override
                    public boolean onQueryTextChange(String newText) {

                        processSearch(newText);

                        return false;
                    }
                });
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                System.out.println("toolbar closed");
                message.setText("List of Donors");
                return false;
            }
        });
    }

    public void processSearch(String s) {

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("donor").orderByChild("organ").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        // it will be directed to constructor of myAdapter
        adapter = new myAdapter(options, context);
        // starts reading data from adapter
        adapter.startListening();
        // creates recyclerView and update it with data
        recyclerView.setAdapter(adapter);
    }

    // start reading data from adapter
    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    // called once adapter data will be completely read
    @Override
    protected void onStop() {
        super.onStop();

        adapter.startListening();
    }
}