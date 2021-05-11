package pranjal.learning.organdonation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoard extends AppCompatActivity {

    ImageView rightIcon;
    TextView currentUserName;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Menu menu;

    public static FirebaseUser currentUser;
    public static String current_username;
    public static String datetime;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh_mm_ss_dd_MM_yyyy");
        datetime = ft.format(dNow);

        /////////////////////////////////////////////////////////////////////////////////////////////////

        rightIcon = findViewById(R.id.right_iconT);
        currentUserName = findViewById(R.id.currentUserT);

        // getting current user
        currentUser = mAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            String UID = currentUser.getUid();
            System.out.println(UID);

            // for general user
            databaseReference = FirebaseDatabase.getInstance().getReference().child("donor_recipient_user");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.hasChild(currentUser.getUid()))
                    {
                        current_username = snapshot.child(currentUser.getUid()).child("name").getValue().toString();
                        currentUserName.setText(current_username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No user", Toast.LENGTH_SHORT).show();
        }


        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DashBoard.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(DashBoard.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(DashBoard.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(DashBoard.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

    /////////////////////////////////////////////////////////////////////////////////////////////////

        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbarT);
        setSupportActionBar(toolbar);

        // open navigation
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // navigation item clicked
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.donation_request)
                {
                    Intent intent = new Intent(getBaseContext(), drawer_clicked.class);
                    intent.putExtra("id", "donation_request");
                    startActivity(intent);
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(menuItem.getItemId() == R.id.recipient_request)
                {
                    Intent intent = new Intent(getBaseContext(), drawer_clicked.class);
                    intent.putExtra("id", "recipient_request");
                    startActivity(intent);
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(menuItem.getItemId() == R.id.faq_request)
                {
                    Intent intent = new Intent(getBaseContext(), drawer_faq.class);
                    intent.putExtra("id", "faq_request");
                    startActivity(intent);
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(menuItem.getItemId() == R.id.menu_appointment)
                {
                    startActivity(new Intent(DashBoard.this, myAppointments.class));
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(menuItem.getItemId() == R.id.menu_logout)
                {
                    startActivity(new Intent(DashBoard.this, Login.class));
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });

    }

    public void donorClicked(View view) {

        startActivity(new Intent(DashBoard.this, donor.class));
        Log.i("info", "donorClicked");
    }

    public void recipientClicked(View view) {

        startActivity(new Intent(DashBoard.this, recipient.class));
        Log.i("info", "recipientClicked");
    }

    public void visitDoctorClicked(View view) {

        startActivity(new Intent(DashBoard.this, doctor.class));
        //startActivity(new Intent(DashBoard.this, visitDoctor.class));
        Log.i("info", "visitDoctorClicked");
    }

    public void faqClicked(View view) {

        startActivity(new Intent(DashBoard.this, faq.class));
        Log.i("info", "faqClicked");
    }
}