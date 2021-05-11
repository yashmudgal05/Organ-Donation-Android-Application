package pranjal.learning.organdonation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class timeSlots extends AppCompatActivity {

    TextView message;
    ImageView rightIcon;

    TextView sl, s2, s3, s4, s5, s6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        final String d = getIntent().getStringExtra("d");
        final String date = getIntent().getStringExtra("date");
        String slot1 = getIntent().getStringExtra("slot1");
        String slot2 = getIntent().getStringExtra("slot2");
        String slot3 = getIntent().getStringExtra("slot3");
        String slot4 = getIntent().getStringExtra("slot4");
        String slot5 = getIntent().getStringExtra("slot5");
        String slot6 = getIntent().getStringExtra("slot6");

        sl = findViewById(R.id.slot1);
        s2 = findViewById(R.id.slot2);
        s3 = findViewById(R.id.slot3);
        s4 = findViewById(R.id.slot4);
        s5 = findViewById(R.id.slot5);
        s6 = findViewById(R.id.slot6);

        if(slot1.equals("booked")) {

            sl.setText("9:00-9:50\nunavailable");
            sl.setEnabled(false);
        }

        if(slot2.equals("booked")) {

            s2.setText("10:00-10:50\nunavailable");
            s2.setEnabled(false);
        }

        if(slot3.equals("booked")) {

            s3.setText("11:00-11:50\nunavailable");
            s3.setEnabled(false);
        }

        if(slot4.equals("booked")) {

            s4.setText("12:00-12:50\nunavailable");
            s4.setEnabled(false);
        }

        if(slot5.equals("booked")) {

            s5.setText("2:00-2:50\nunavailable");
            s5.setEnabled(false);
        }

        if(slot6.equals("booked")) {

            s6.setText("3:00-3:50\nunavailable");
            s6.setEnabled(false);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("Time slots");

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(timeSlots.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(timeSlots.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(timeSlots.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(timeSlots.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////

        sl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "9:00-9:50");
                intent.putExtra("slot", "slot1");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "10:00-10:50");
                intent.putExtra("slot", "slot2");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "11:00-11:50");
                intent.putExtra("slot", "slot3");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "12:00-12:50");
                intent.putExtra("slot", "slot4");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "14:00-14:50");
                intent.putExtra("slot", "slot5");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Appointment.class);
                intent.putExtra("s", "15:00-15:50");
                intent.putExtra("slot", "slot6");
                intent.putExtra("date", date);
                intent.putExtra("d", d);
                startActivity(intent);
            }
        });
    }
}