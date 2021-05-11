package pranjal.learning.organdonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static pranjal.learning.organdonation.dashboard_doc.uniqueId;

public class myAppointmentDr extends AppCompatActivity {

    Context context;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    FloatingActionButton fb;

    RecyclerView recyclerView;
    myAdapterAppointDr adapter;

    TextView message;
    ImageView rightIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_dr);

        /////////////////////////////////////////////////////////////////////////////////////////////////

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("My appointments");

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(myAppointmentDr.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(myAppointmentDr.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(myAppointmentDr.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(myAppointmentDr.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////// retrieve data form firebase /////////////////////////////////////
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUser = mAuth.getInstance().getCurrentUser();

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<modelAppointment> options =
                new FirebaseRecyclerOptions.Builder<modelAppointment>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("doctor_appointment").child(uniqueId), modelAppointment.class)
                        .build();

        // it will be directed to constructor of myAdapterDr
        adapter = new myAdapterAppointDr(options, context);

        // creates recyclerView and update it with data
        recyclerView.setAdapter(adapter);
        /////////////////////////////////////////////////////////////////////////////////////////////

        fb = findViewById(R.id.backButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), dashboard_doc.class));
                finish();
            }
        });
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