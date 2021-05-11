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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class addDataDR extends AppCompatActivity {

    TextView message;
    ImageView rightIcon;
    Uri filePath;
    FirebaseAuth mAuth;
    String datetime;

    RadioGroup radioGroup;
    RadioButton worksAt;
    String ch;

    ImageView profileImage;
    Button uploadImage;
    EditText name, uid, speciality, experience;
    Button generateId;
    EditText work, contact, email, city, state;
    CheckBox acknowledgement;
    Button uploadInformation;

    String bName, bUid, bSpeciality, bExperience, bWorksAt, bContact, bEmail, bCity, bState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_d_r);

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        message = findViewById(R.id.message);
        message.setText("Complete your profile");

        generateId = findViewById(R.id.generateId);

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);

        work = findViewById(R.id.worksAt);
        acknowledgement = findViewById(R.id.acknowledgement);
        uploadInformation = findViewById(R.id.uploadInformation);
        uploadInformation.setEnabled(false);
        /////////////////////////////////////////////////////////////////////////////////////////////////

        rightIcon = findViewById(R.id.right_icon_l);
        // menu clicked
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(addDataDR.this, "Clicked", Toast.LENGTH_SHORT).show();

                PopupMenu popup = new PopupMenu(addDataDR.this, v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                // this method is called when an option is clicked from our app menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.logout:
                                Log.i("Item Selected", "logout option is clicked");
                                startActivity(new Intent(addDataDR.this, Login.class));
                                finish();
                                return true;
                            case R.id.help:
                                Log.i("Item Selected", "Help option is clicked");
                                startActivity(new Intent(addDataDR.this, help.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////

        profileImage = (ImageView) findViewById(R.id.donorImage);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadInformation = (Button) findViewById(R.id.uploadInformation);

        // browsing image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // manage user permission
                Dexter.withActivity(addDataDR.this)
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

    public void generateId(View view) {

        uid = findViewById(R.id.uid);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMs");
        String datetime = ft.format(dNow);

        uid.setText("DR" + datetime);
        generateId.setEnabled(false);
    }


    public void radioButtonSelected(View view) {

        //radio group
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if(selectedId == R.id.hospital)
        {
            System.out.println("hospital selected");
            work.setVisibility(View.VISIBLE);
            work.setHint("Name of hospital");
            ch = "hospital";
        }
        else if(selectedId == R.id.clinic)
        {
            System.out.println("clinic selected");
            work.setVisibility(View.VISIBLE);
            work.setHint("Name of Clinic");
            ch = "clinic";
        }
    }

    // uploading data to firebase
    private void uploadToFireBase() {

        // dialogue box for progress
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File uploader");
        dialog.show();

        name = findViewById(R.id.fullName);
        uid = findViewById(R.id.uid);
        speciality = findViewById(R.id.speciality);
        experience = findViewById(R.id.experience);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        bName = name.getText().toString();
        bUid = uid.getText().toString();
        bSpeciality = speciality.getText().toString();
        bExperience = experience.getText().toString();
        bWorksAt = work.getText().toString();
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

                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("doctor_user");

                                FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();

                                if (currentUser != null) {

                                    DataHolderDr obj = new DataHolderDr(bName, bUid, bSpeciality, bExperience, bWorksAt, bContact, bEmail, bCity, bState, uri.toString(), ch);
                                    root.child(currentUser.getUid()).setValue(obj);

                                    startActivity(new Intent(getApplicationContext(), dashboard_doc.class));
                                    finish();
                                }
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