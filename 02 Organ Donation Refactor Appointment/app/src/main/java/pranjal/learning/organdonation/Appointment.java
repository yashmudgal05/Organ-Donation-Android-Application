package pranjal.learning.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static pranjal.learning.organdonation.myAdapterDr.nameAppoint;
import static pranjal.learning.organdonation.myAdapterDr.uidAppoint;
import static pranjal.learning.organdonation.myAdapterDr.speciliteAppoint;
import static pranjal.learning.organdonation.myAdapterDr.phoneAppoint;
import static pranjal.learning.organdonation.myAdapterDr.addressAppoint;
import static pranjal.learning.organdonation.DashBoard.currentUser;
import static pranjal.learning.organdonation.DashBoard.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Appointment extends AppCompatActivity {

    DatabaseReference root, root_1, root_2, root_3, root_4;

    FloatingActionButton fb;

    String date;
    String d;
    String datetime_short;
    TextView message;
    TextView consultationWith;
    TextView specilite;
    TextView time_slot;
    ImageView rightIcon;
    TextView patientName, patientAge, patientGender, patientContact, patientDate, patientSlot;
    Button confirmAppointment;

    String slot1 = "unBooked";
    String slot2 = "unBooked";
    String slot3 = "unBooked";
    String slot4 = "unBooked";
    String slot5 = "unBooked";
    String slot6 = "unBooked";

    EditText selectDate;

    String pName, pAge, pGender, pContact, pDate, pSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        /////////////////////////////////////////////////////////////////////////////////////////////////

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd_MM_yyyy");
        datetime_short = ft.format(dNow);

        patientName = findViewById(R.id.patientName);
        patientAge = findViewById(R.id.patientAge);
        patientGender = findViewById(R.id.patientGender);
        patientContact = findViewById(R.id.patientContact);
        patientDate = findViewById(R.id.selectDate);
        patientSlot = findViewById(R.id.time_slot);

        System.out.println("uid " + uidAppoint);

        /////////////////////////////////////////////////////////////////////////////////////////////////

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("Confirm appointment");

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Appointment.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(Appointment.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(Appointment.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(Appointment.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////

        consultationWith = findViewById(R.id.consultationWith);
        specilite = findViewById(R.id.specialite);

        consultationWith.setText("Dr." + nameAppoint);
        specilite.setText(speciliteAppoint);

        /////////////////////////////////////////////////////////////////////////////////////////////////
        selectDate = findViewById(R.id.selectDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month + 1;
                        d = day + "/" + month + "/" + year;
                        selectDate.setText(d);

                        date = day + "_" + month + "_" + year;
                        initialUploadToFirebase();

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        selectDate.setText(getIntent().getStringExtra("date"));

    //////////////////////////////////////////////////////////////////////////////////////////////////////

        Button time_slot_button = findViewById(R.id.time_slot_button);

        time_slot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), timeSlots.class);
                intent.putExtra("d", date);
                intent.putExtra("date", d);
                intent.putExtra("slot1", slot1);
                intent.putExtra("slot2", slot2);
                intent.putExtra("slot3", slot3);
                intent.putExtra("slot4", slot4);
                intent.putExtra("slot5", slot5);
                intent.putExtra("slot6", slot6);
                startActivity(intent);
            }
        });
    /////////////////////////////////////////////////////////////////////////////////////////////////

        String s = getIntent().getStringExtra("s");
        time_slot = findViewById(R.id.time_slot);
        time_slot.setText(s);
    /////////////////////////////////////////////////////////////////////////////////////////////////

        date = getIntent().getStringExtra("d");

    /////////////////////////////////////////////////////////////////////////////////////////////////

        confirmAppointment = findViewById(R.id.bookAppointment);
        confirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadToFirebase();
            }
        });
    }

    public void initialUploadToFirebase() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        root = db.getReference("doctor_timeSlots");
        root_1 = db.getReference("doctor_timeSlots").child(uidAppoint);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor_timeSlots");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(uidAppoint))
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("doctor_timeSlots").child(uidAppoint);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(date))
                            {
                                slot1 = snapshot.child(date).child("slot1").getValue().toString();
                                slot2 = snapshot.child(date).child("slot2").getValue().toString();
                                slot3 = snapshot.child(date).child("slot3").getValue().toString();
                                slot4 = snapshot.child(date).child("slot4").getValue().toString();
                                slot5 = snapshot.child(date).child("slot5").getValue().toString();
                                slot6 = snapshot.child(date).child("slot6").getValue().toString();
                            }
                            else
                            {
                                System.out.println("1");
                                DataHolderSlots obj = new DataHolderSlots(slot1, slot2, slot3, slot4, slot5, slot6);
                                root_1.child(date).setValue(obj);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    System.out.println("2");
                    DataHolderSlots obj = new DataHolderSlots(slot1, slot2, slot3, slot4, slot5, slot6);
                    root.child(uidAppoint).child(date).setValue(obj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadToFirebase() {


        pName = patientName.getText().toString();
        pAge = patientAge.getText().toString();
        pGender = patientGender.getText().toString();
        pContact = patientContact.getText().toString();
        pDate = patientDate.getText().toString();
        pSlot = patientSlot.getText().toString();

        String slot = getIntent().getStringExtra("slot");
        if(slot != null) {
            if(slot.contains("slot1"))
            {
                slot1 = "booked";
            } else if(slot.contains("slot2"))
            {
                slot2 = "booked";
            }else if(slot.contains("slot3"))
            {
                slot3 = "booked";
            }else if(slot.contains("slot4"))
            {
                slot4 = "booked";
            }else if(slot.contains("slot5"))
            {
                slot5 = "booked";
            }else if(slot.contains("slot6"))
            {
                slot6 = "booked";
            }
        }

        System.out.println(pName);
        System.out.println(pAge);
        System.out.println(pGender);
        System.out.println(pContact);
        System.out.println(pDate);
        System.out.println(pSlot);
        System.out.println(nameAppoint);
        System.out.println(speciliteAppoint);
        System.out.println(phoneAppoint);
        System.out.println(addressAppoint);
        System.out.println("slot1 " + slot1);
        System.out.println("slot2 " + slot2);
        System.out.println("slot3 " + slot3);
        System.out.println("slot4 " + slot4);
        System.out.println("slot5 " + slot5);
        System.out.println("slot6 " + slot6);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMs");
        final String dt = "AP" + ft.format(dNow);

        DataHolderSlots obj = new DataHolderSlots(slot1, slot2, slot3, slot4, slot5, slot6);
        final DataholderAppoint obj_new = new DataholderAppoint(pName, pAge, pGender, pContact, pDate, pSlot,
                nameAppoint, speciliteAppoint, phoneAppoint, addressAppoint, uidAppoint, dt);

        // uploading
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        root = db.getReference("doctor_timeSlots").child(uidAppoint);
        root_1 = db.getReference("doctor_appointment");

        // time Slots
        root.child(date).setValue(obj);

        // doctor
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor_appointment");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(uidAppoint))
                {
                    root_2 = db.getReference("doctor_appointment").child(uidAppoint);
                    root_2.child(dt).setValue(obj_new);
                }
                else
                {
                    root_1.child(uidAppoint).child(dt).setValue(obj_new);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // user
        root_3 = db.getReference("user_appointment");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_appointment");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(currentUser.getUid()))
                {
                    root_4 = db.getReference("user_appointment").child(currentUser.getUid());
                    root_4.child(dt).setValue(obj_new);
                }
                else
                {
                    root_3.child(currentUser.getUid()).child(dt).setValue(obj_new);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), DashBoard.class));
        finish();
    }
}