package pranjal.learning.organdonation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.lang.reflect.Array;

import de.hdodenhof.circleimageview.CircleImageView;

import static pranjal.learning.organdonation.DashBoard.currentUser;
import static pranjal.learning.organdonation.drawer_clicked.str;

public class myAdapterAppointment  extends FirebaseRecyclerAdapter<modelAppointment, myAdapterAppointment.viewHolder> {

    Context context;
    String slot;

    // fetch all the data form model class
    public myAdapterAppointment(@NonNull FirebaseRecyclerOptions<modelAppointment> options, Context context) {
        super(options);

        this.context = context;
    }

    // put data to cardView
    @Override
    protected void onBindViewHolder(@NonNull final myAdapterAppointment.viewHolder holder, final int position, @NonNull final modelAppointment modelAppointment) {

        holder.pdDate.setText(modelAppointment.getpDate());
        holder.pdSlot.setText(modelAppointment.getpSlot());
        holder.pdName.setText(modelAppointment.getpName());
        holder.pdContact.setText(modelAppointment.getpContact());
        holder.pd_with.setText("Dr. " + modelAppointment.getdName());
        holder.pd_contact.setText(modelAppointment.getdContact());
        holder.pd_address.setText(modelAppointment.getdAddress());

        holder.deleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slot = "";
                String delDate = modelAppointment.getpDate();
                String[] array = delDate.split("/");
                final String str = array[0] + "_" + array[1] + "_" + array[2];

                if(modelAppointment.getpSlot().equals("9:00-9:50")) {
                    slot = "slot1";
                } else if(modelAppointment.getpSlot().equals("10:00-10:50")) {
                    slot = "slot2";
                } else if(modelAppointment.getpSlot().equals("11:00-11:50")) {
                    slot = "slot3";
                } else if(modelAppointment.getpSlot().equals("12:00-12:50")) {
                    slot = "slot4";
                } else if(modelAppointment.getpSlot().equals("14:00-14:50")) {
                    slot = "slot5";
                } else if(modelAppointment.getpSlot().equals("15:00-15:50")) {
                    slot = "slot6";
                }

                System.out.println(slot);

                // creating alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.pdDate.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete..?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("doctor_timeSlots")
                                .child(modelAppointment.getdUid()).child(str).child(slot).setValue("unBooked");

                        FirebaseDatabase.getInstance().getReference().child("doctor_appointment")
                                .child(modelAppointment.getdUid()).child(modelAppointment.getDateTime()).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("user_appointment").child(currentUser.getUid())
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // it will show alert dialog
                builder.show();
            }
        });
    }

    // creating xml template "i.e. singlerow.xml" file template
    // i.e. creating runtime view
    @NonNull
    @Override
    public myAdapterAppointment.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowappointment, parent, false);
        return new myAdapterAppointment.viewHolder(view);
    }

    // creating data holders
    // accessing UI elements of "singlerow.xml" file
    class viewHolder extends RecyclerView.ViewHolder {

        TextView pdDate, pdSlot, pdName, pdContact, pd_with, pd_contact, pd_address;
        ImageButton deleteAppointment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            deleteAppointment = itemView.findViewById(R.id.deleteAppointment);
            pdDate = itemView.findViewById(R.id.appointDate);
            pdSlot = itemView.findViewById(R.id.timeSlot);
            pdName = itemView.findViewById(R.id.patient_name);
            pdContact = itemView.findViewById(R.id.patient_contact);
            pd_with = itemView.findViewById(R.id.consultation_with);
            pd_contact = itemView.findViewById(R.id.drContact);
            pd_address = itemView.findViewById(R.id.doctorAddress);
        }
    }
}

