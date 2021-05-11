package pranjal.learning.organdonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapterAppointDr extends FirebaseRecyclerAdapter<modelAppointment, myAdapterAppointDr.viewHolder> {

    Context context;

    // fetch all the data form model class
    public myAdapterAppointDr(@NonNull FirebaseRecyclerOptions<modelAppointment> options, Context context) {
        super(options);

        this.context = context;
    }

    // put data to cardView
    @Override
    protected void onBindViewHolder(@NonNull final myAdapterAppointDr.viewHolder holder, final int position, @NonNull final modelAppointment modelAppointment) {

        holder.drDate.setText(modelAppointment.getpDate());
        holder.drSlot.setText(modelAppointment.getpSlot());
        holder.drName.setText(modelAppointment.getpName());
        holder.drContact.setText(modelAppointment.getpContact());
        holder.drAge.setText(modelAppointment.getpAge());
        holder.drGender.setText(modelAppointment.getpGender());
    }

    // creating xml template "i.e. singlerow.xml" file template
    // i.e. creating runtime view
    @NonNull
    @Override
    public myAdapterAppointDr.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowappointdr, parent, false);
        return new myAdapterAppointDr.viewHolder(view);
    }

    // creating data holders
    // accessing UI elements of "singlerow.xml" file
    class viewHolder extends RecyclerView.ViewHolder {

        TextView drDate, drSlot, drName, drContact, drGender, drAge;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            drDate = itemView.findViewById(R.id.appointDateDr);
            drSlot = itemView.findViewById(R.id.timeSlotDr);
            drAge = itemView.findViewById(R.id.patient_age);
            drGender = itemView.findViewById(R.id.patient_gender);
            drName = itemView.findViewById(R.id.patient_name);
            drContact = itemView.findViewById(R.id.patient_contact);
        }
    }
}


