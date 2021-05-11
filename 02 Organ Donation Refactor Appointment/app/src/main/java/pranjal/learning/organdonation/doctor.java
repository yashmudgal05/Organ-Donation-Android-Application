package pranjal.learning.organdonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class doctor extends AppCompatActivity {

    Context context;

    RecyclerView recyclerView;
    myAdapterDr adapter;
    FloatingActionButton fb;

    SearchView searchView;

    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("List of Doctors");

        searchView = findViewById(R.id.searchView);
        context = getApplicationContext();
        /////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////// retrieve data form firebase /////////////////////////////////////
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<modelDr> options =
                new FirebaseRecyclerOptions.Builder<modelDr>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("doctor_user"), modelDr.class)
                        .build();

        // it will be directed to constructor of myAdapterDr
        adapter = new myAdapterDr(options, context);

        // creates recyclerView and update it with data
        recyclerView.setAdapter(adapter);

        // handling "add" button click
        fb = findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), addDataDR.class));
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("toolbar");
                message.setText("");

                searchView.setQueryHint("Search speciality");

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
                message.setText("List of Doctors");
                return false;
            }
        });

    }

    public void processSearch(String s) {

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<modelDr> options =
                new FirebaseRecyclerOptions.Builder<modelDr>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("speciality").startAt(s).endAt(s+"\uf8ff"), modelDr.class)
                        .build();

        // it will be directed to constructor of myAdapterDr
        adapter = new myAdapterDr(options, context);
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