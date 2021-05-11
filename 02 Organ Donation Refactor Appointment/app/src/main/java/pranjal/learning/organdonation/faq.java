package pranjal.learning.organdonation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static pranjal.learning.organdonation.DashBoard.currentUser;
import static pranjal.learning.organdonation.DashBoard.datetime;

public class faq extends AppCompatActivity {

    ImageView rightIcon;
    RecyclerView recyclerView;
    myAdapterQ adapter;

    DataHolderQ obj;
    FirebaseDatabase fb;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    Button uploadQuestion;
    TextView message;
    TextInputLayout question;

    String bQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("Frequently asked questions");

        uploadQuestion = findViewById(R.id.uploadQuestion);
        /////////////////////////////////////////////////////////////////////////////////////////////

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(faq.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(faq.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(faq.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(faq.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

        ////////////////////////////// retrieve data form firebase /////////////////////////////////////
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // connecting recyclerView and firebase
        // generating query for firebase and fetching data from it
        // it will return data from fireBase and store it to "options
        FirebaseRecyclerOptions<modelQ> options =
                new FirebaseRecyclerOptions.Builder<modelQ>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("faq_app"), modelQ.class)
                        .build();

        // it will be directed to constructor of myAdapterDr
        adapter = new myAdapterQ(options);

        // creates recyclerView and update it with data
        recyclerView.setAdapter(adapter);

        uploadQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadToFireBase();
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

    // uploading data to firebase
    private void uploadToFireBase() {

        question = (TextInputLayout) findViewById(R.id.question);

        bQuestion = question.getEditText().getText().toString();
        String bAnswer = "will be answered soon ...";

        fb = FirebaseDatabase.getInstance();
        DatabaseReference root = fb.getReference("faq_app");
        databaseReference = fb.getReference("faq_user");

        obj = new DataHolderQ(bQuestion, bAnswer);

        // for app
        root.child(currentUser.getUid() + " " + datetime).setValue(obj);

        // for user
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(currentUser.getUid()))
                {
                    DatabaseReference reference = fb.getReference("faq_user").child(currentUser.getUid());
                    reference.child(datetime).setValue(obj);
                }
                else
                {
                    databaseReference.child(currentUser.getUid()).child(datetime).setValue(obj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(new Intent(getApplicationContext(), faq.class));
        finish();
    }
}