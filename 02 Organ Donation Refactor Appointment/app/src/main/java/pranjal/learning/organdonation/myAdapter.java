package pranjal.learning.organdonation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import static pranjal.learning.organdonation.drawer_clicked.status;
import static pranjal.learning.organdonation.drawer_clicked.str;
import static pranjal.learning.organdonation.DashBoard.currentUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAdapter extends FirebaseRecyclerAdapter<model, myAdapter.viewHolder> {

    Context context;

    // fetch all the data form model class
    public myAdapter(@NonNull FirebaseRecyclerOptions<model> options, Context context) {
        super(options);

        this.context = context;
    }

    // put data to cardView
    @SuppressLint("RestrictedApi")
    @Override
    protected void onBindViewHolder(@NonNull final viewHolder holder, final int position, @NonNull final model model) {


        holder.name.setText(model.getName());
        holder.uniqueId.setText(model.getUniqueId());
        holder.age.setText(model.getAge() + " years");
        holder.gender.setText(model.getGender());
        holder.organ.setText(model.getOrgan());

        // for image (displaying image using URL)
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        if(status)
        {
            holder.delete.setVisibility(View.VISIBLE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creating alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete..?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(str.equals("donor"))
                        {
                            FirebaseDatabase.getInstance().getReference().child("donor_user").child(currentUser.getUid())
                                    .child(getRef(position).getKey()).removeValue();

                            FirebaseDatabase.getInstance().getReference().child("donor_app")
                                    .child(model.getUniqueId()).removeValue();
                        }
                        else if(str.equals("recipient"))
                        {
                            FirebaseDatabase.getInstance().getReference().child("recipient_user").child(currentUser.getUid())
                                    .child(getRef(position).getKey()).removeValue();

                            FirebaseDatabase.getInstance().getReference().child("recipient_app")
                                    .child(model.getUniqueId()).removeValue();
                        }
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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("layout clicked");

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.custompopup))
                        .setGravity(Gravity.CENTER)
                        .create();

                View myView = dialogPlus.getHolderView();
                final ImageView imageP = myView.findViewById(R.id.imageP);
                final TextView nameP = myView.findViewById(R.id.nameViewP);
                final TextView uidP = myView.findViewById(R.id.uidViewP);
                final TextView ageP = myView.findViewById(R.id.ageViewP);
                final TextView genderP = myView.findViewById(R.id.genderViewP);
                final TextView organP = myView.findViewById(R.id.organViewP);
                final TextView organTextP = myView.findViewById(R.id.organTextViewP);
                final TextView contactP = myView.findViewById(R.id.contactViewP);
                final TextView emailP = myView.findViewById(R.id.emailViewP);
                final TextView cityP = myView.findViewById(R.id.cityViewP);
                final TextView stateP = myView.findViewById(R.id.stateViewP);
                final ImageView callP = myView.findViewById(R.id.phoneCallP);
                ImageButton buttonP = myView.findViewById(R.id.cancel);

                // for image (displaying image using URL)
                Glide.with(imageP.getContext()).load(model.getPurl()).into(imageP);

                nameP.setText(model.getName());
                uidP.setText(model.getUniqueId());
                ageP.setText(model.getAge() + " years");
                genderP.setText(model.getGender());

                if(model.getUniqueId().contains("D"))
                {
                    organTextP.setText("Organ to\ndonate:");
                }
                else if(model.getUniqueId().contains("R"))
                {
                    organTextP.setText("Organ\nrequired:");
                }

                organP.setText(model.getOrgan());
                contactP.setText(model.getContact());
                emailP.setText(model.getMail());
                cityP.setText(model.getCity());
                stateP.setText(model.getState());

                callP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        System.out.println("call clicked");

                        String dial = "tel:" + model.getContact();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(dial));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                emailP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = "mailto:" + model.getMail();

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
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.siglerow, parent, false);
        return new viewHolder(view);
    }

    // creating data holders
    // accessing UI elements of "singlerow.xml" file
    class viewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        FloatingActionButton delete;
        CircleImageView img;
        TextView name, uniqueId, gender, age, organ;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.mainLayout);
            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nameText);
            uniqueId = itemView.findViewById(R.id.uniqueIdText);
            age = itemView.findViewById(R.id.ageText);
            gender = itemView.findViewById(R.id.genderText);
            organ = itemView.findViewById(R.id.organText);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
