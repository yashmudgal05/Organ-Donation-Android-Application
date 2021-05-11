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
import com.google.firebase.database.FirebaseDatabase;

import static pranjal.learning.organdonation.DashBoard.currentUser;

public class drawer_clicked extends AppCompatActivity {

    Context context;

    public static Boolean status = false;
    public static String str;

    FloatingActionButton fb;
    RecyclerView recyclerView;
    myAdapter adapter;

    TextView message;
    ImageView rightIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_clicked);

        String id = getIntent().getStringExtra("id");

        /////////////////////////////////////////////////////////////////////////////////////////////////

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("My Requests");

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(drawer_clicked.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(drawer_clicked.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(drawer_clicked.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(drawer_clicked.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });


        ////////////////////////////// retrieve data form firebase /////////////////////////////////////

        // donor
        if(id.equals("donation_request"))
        {
            message.setText("My donation requests");
            str = "donor";
            status = true;
            recyclerView = findViewById(R.id.recycleView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("donor_user").child(currentUser.getUid()), model.class)
                            .build();
            adapter = new myAdapter(options, context);
            recyclerView.setAdapter(adapter);
        }

        // recipient
        if(id.equals("recipient_request"))
        {
            message.setText("My recipient requests");
            str = "recipient";
            status = true;
            recyclerView = findViewById(R.id.recycleView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("recipient_user").child(currentUser.getUid()), model.class)
                            .build();
            adapter = new myAdapter(options, context);
            recyclerView.setAdapter(adapter);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////

        fb = findViewById(R.id.backButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), DashBoard.class));
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