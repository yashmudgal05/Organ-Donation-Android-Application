package pranjal.learning.organdonation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAdapterDr extends FirebaseRecyclerAdapter<modelDr, myAdapterDr.viewHolder> {

    Context context;

    public static String nameAppoint;
    public static String uidAppoint;
    public static String speciliteAppoint;
    public static String mailAppoint;
    public static String phoneAppoint;
    public static String addressAppoint;

    // fetch all the data form model class
    public myAdapterDr(@NonNull FirebaseRecyclerOptions<modelDr> options, Context context) {
        super(options);

        this.context = context;
    }

    // put data to cardView
    @Override
    protected void onBindViewHolder(@NonNull final myAdapterDr.viewHolder holder, final int position, @NonNull final modelDr modelDr) {

        holder.name.setText("Dr. " + modelDr.getName());
        holder.uniqueId.setText(modelDr.getUniqueId());
        holder.speciality.setText(modelDr.getSpeciality());
        holder.experience.setText(modelDr.getExperience() + " years");

        // for image (displaying image using URL)
        Glide.with(holder.img.getContext()).load(modelDr.getPurl()).into(holder.img);

        ///////////////////////////////////////////////////////////////////////////////////////////////
        //appointment button clicked
        holder.bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameAppoint = modelDr.getName();
                uidAppoint = modelDr.getUniqueId();
                speciliteAppoint = modelDr.getSpeciality();
                phoneAppoint = modelDr.getContact();
                addressAppoint = modelDr.getWorksAt() + " " + modelDr.getCity() + " " + modelDr.getState();

                openPage(context);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////

        holder.layoutDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("layout clicked");

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.custompopupdr))
                        .setGravity(Gravity.CENTER)
                        .create();

                View myView = dialogPlus.getHolderView();
                final ImageView imageD = myView.findViewById(R.id.imageD);
                final TextView nameD = myView.findViewById(R.id.nameViewD);
                final TextView uidD = myView.findViewById(R.id.uidViewD);
                final TextView specialityD = myView.findViewById(R.id.specialityViewD);
                final TextView experienceD = myView.findViewById(R.id.experienceViewD);
                final TextView worksD = myView.findViewById(R.id.workViewD);
                final TextView emailD = myView.findViewById(R.id.emailViewD);
                final TextView contactD = myView.findViewById(R.id.contactViewD);
                final TextView cityD = myView.findViewById(R.id.cityViewD);
                final TextView stateD = myView.findViewById(R.id.stateViewD);
                final ImageView callD = myView.findViewById(R.id.phoneCallD);
                ImageButton buttonP = myView.findViewById(R.id.cancelDr);

                // for image (displaying image using URL)
                Glide.with(imageD.getContext()).load(modelDr.getPurl()).into(imageD);

                nameD.setText("Dr. " + modelDr.getName());
                uidD.setText(modelDr.getUniqueId());
                specialityD.setText(modelDr.getSpeciality());
                experienceD.setText(modelDr.getExperience() + " years");

                if(modelDr.getCh().contains("hospital"))
                {
                    worksD.setText("Work at " + modelDr.getWorksAt().toUpperCase() + " hospital");
                }
                else if(modelDr.getCh().contains("clinic"))
                {
                    worksD.setText("Have personal clinic " + modelDr.getWorksAt().toUpperCase());
                }

                emailD.setText(modelDr.getMail());
                contactD.setText(modelDr.getContact());
                cityD.setText(modelDr.getCity());
                stateD.setText(modelDr.getState());

                callD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        System.out.println("call clicked");

                        String dial = "tel:" + modelDr.getContact();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(dial));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                emailD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = "mailto:" + modelDr.getMail();

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse(email));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                dialogPlus.show();

                buttonP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogPlus.dismiss();
                    }
                });
            }
        });

    }

    // creating xml template "i.e. singlerow.xml" file template
    // i.e. creating runtime view
    @NonNull
    @Override
    public myAdapterDr.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdr, parent, false);
        return new myAdapterDr.viewHolder(view);
    }

    // creating data holders
    // accessing UI elements of "singlerow.xml" file
    class viewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutDr;

        CircleImageView img;
        TextView name, uniqueId, speciality, experience;
        Button bookAppointment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            layoutDr = itemView.findViewById(R.id.mainLayoutDr);
            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nameText);
            uniqueId = itemView.findViewById(R.id.uniqueIdText);
            speciality = itemView.findViewById(R.id.specialityTextView);
            experience = itemView.findViewById(R.id.experienceTextView);
            bookAppointment = itemView.findViewById(R.id.bookAppointment);
        }
    }

    private void openPage(Context context) {

        Intent i = new Intent(context, Appointment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
