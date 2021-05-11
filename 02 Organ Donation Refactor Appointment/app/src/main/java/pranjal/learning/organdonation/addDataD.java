package pranjal.learning.organdonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import static pranjal.learning.organdonation.DashBoard.currentUser;
import static pranjal.learning.organdonation.DashBoard.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class addDataD extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView message;
    ImageView rightIcon;
    Uri filePath;
    FirebaseAuth mAuth;

    FirebaseDatabase fb;
    DatabaseReference databaseReference;
    DataHolder obj;
    Spinner genderSpinner;
    Spinner organSpinner;

    ImageView profileImage;
    Button uploadImage;
    EditText name, uid, age;
    Button generateId;
    String gender, organ;
    EditText contact, email, city, state;
    CheckBox acknowledgement;
    Button uploadInformation;

    String bName, bUid, bAge, bGender, bOrgan, bContact, bEmail, bCity, bState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("Register new donor");

        generateId = findViewById(R.id.generateId);

        acknowledgement = findViewById(R.id.acknowledgement);
        uploadInformation = findViewById(R.id.uploadInformation);
        uploadInformation.setEnabled(false);
    /////////////////////////////////////////////////////////////////////////////////////////////////

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(addDataD.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(addDataD.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(addDataD.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(addDataD.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////

        //gender spinner
        genderSpinner = findViewById(R.id.genderSpinner);

        genderSpinner.setOnItemSelectedListener(this);

        String[] gender = getResources().getStringArray(R.array.gender);
        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    ////////////////////////////////////////////////////////////////////////////////////////////////


        //organ spinner
        organSpinner = findViewById(R.id.organSpinner);

        organSpinner.setOnItemSelectedListener(this);

        String[] organ = getResources().getStringArray(R.array.organD);
        ArrayAdapter organAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, organ);
        organAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organSpinner.setAdapter(organAdapter);
        ////////////////////////////////////////////////////////////////////////////////////////////////


        profileImage = (ImageView) findViewById(R.id.donorImage);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadInformation = (Button) findViewById(R.id.uploadInformation);

        // browsing image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // manage user permission
                Dexter.withActivity(addDataD.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                // opens dialogue box for selecting image from system
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Please select a image"), 1);
                                Log.i("image selected", "done");
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                // re-ask for permission
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        acknowledgement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(acknowledgement.isChecked())
                {
                    uploadInformation.setEnabled(true);
                }

                if(!acknowledgement.isChecked())
                {
                    uploadInformation.setEnabled(false);
                }
            }
        });

        // uploading data to firebase
        uploadInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               uploadToFireBase();
            }
        });

    }

    // handling received image selected by the user from system
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            // url of the image location/path
            filePath = data.getData();

            try {

                Glide.with(profileImage.getContext()).load(filePath).into(profileImage);
            }
            catch(Exception e) {

                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.genderSpinner)
        {
            // Log.i("info", parent.getItemAtPosition(position).toString());
            gender = parent.getItemAtPosition(position).toString();
        }

        if(parent.getId() == R.id.organSpinner)
        {
            // Log.i("info", parent.getItemAtPosition(position).toString());
            organ = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void generateId(View view) {

        uid = findViewById(R.id.uid);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMs");
        String datetime = ft.format(dNow);

        uid.setText("D" + datetime);
        generateId.setEnabled(false);
    }

    // uploading data to firebase
    private void uploadToFireBase() {

        // dialogue box for progress
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File uploader");
        dialog.show();

        name = findViewById(R.id.fullName);
        uid = findViewById(R.id.uid);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        bName = name.getText().toString();
        bUid = uid.getText().toString();
        bAge = age.getText().toString();
        bGender = gender;
        bOrgan = organ;
        bContact = contact.getText().toString();
        bEmail = email.getText().toString();
        bCity = city.getText().toString();
        bState = state.getText().toString();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        // name with which image will be uploaded
        final StorageReference uploader = storage.getReference(bUid);

        uploader.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // getting URL of uploaded image file
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dialog.dismiss();

                                fb = FirebaseDatabase.getInstance();
                                DatabaseReference root = fb.getReference("donor_app");
                                databaseReference = fb.getReference("donor_user");

                                obj = new DataHolder(bName, bUid, bAge, bGender, bOrgan, bContact, bEmail, bCity, bState, uri.toString());

                                // for app
                                root.child(bUid).setValue(obj);

                                // for user
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if(snapshot.hasChild(currentUser.getUid()))
                                        {
                                            DatabaseReference reference = fb.getReference("donor_user").child(currentUser.getUid());
                                            reference.child(bUid).setValue(obj);
                                        }
                                        else
                                        {
                                            databaseReference.child(currentUser.getUid()).child(bUid).setValue(obj);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                startActivity(new Intent(getApplicationContext(), donor.class));
                                finish();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : " + (int)percent + " %");
                    }
                });
    }
}