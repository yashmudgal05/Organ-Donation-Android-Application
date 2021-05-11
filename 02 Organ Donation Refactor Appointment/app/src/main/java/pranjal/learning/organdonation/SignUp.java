package pranjal.learning.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity  implements View.OnClickListener {

    TextInputLayout t1, t2;
    TextInputLayout userName;
    ProgressBar bar;
    Button appCompatButton;

    FirebaseUser currentUser;
    RadioButton btn1, btn2;
    String btn_str;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        appCompatButton = findViewById(R.id.appCompatButton);
        appCompatButton.setEnabled(false);

        btn1 = findViewById(R.id.donor_recipient);
        btn2 = findViewById(R.id.doctor_signUp);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_str = "donor_recipient";
                appCompatButton.setEnabled(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_str = "doctor";
                appCompatButton.setEnabled(true);
            }
        });

        t1 = (TextInputLayout) findViewById(R.id.email);
        t2 = (TextInputLayout) findViewById(R.id.pwd);
        userName = (TextInputLayout) findViewById(R.id.yourName);
        bar = (ProgressBar) findViewById(R.id.progressBar);
    }

    // directing signUp page
    public void goToLogin(View view) {

        startActivity(new Intent(SignUp.this, Login.class));
        finish();
    }

    // user signUp with firebase
    public void login(View view) {

        bar.setVisibility(View.VISIBLE);

        String email = t1.getEditText().getText().toString();
        String password = t2.getEditText().getText().toString();

        System.out.println(email);
        System.out.println(password);

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            // uploading data to firebase
                            uploadToFireBase();

                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            userName.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(SignUp.this, Login.class));
                            finish();
                        }
                        else
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            userName.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "Process Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void uploadToFireBase() {

        String user = userName.getEditText().getText().toString();

        currentUser = mAuth.getInstance().getCurrentUser();

        DataHolder obj = new DataHolder(user);

        // User is signed in
        FirebaseDatabase db = FirebaseDatabase.getInstance();


        if(btn_str.equals("donor_recipient"))
        {
            System.out.println("donor");
            DatabaseReference root = db.getReference("donor_recipient_user");
            root.child(currentUser.getUid()).setValue(obj);
        }
        else if(btn_str.equals("doctor"))
        {
            System.out.println("doctor");
            DatabaseReference root = db.getReference("doctor_user");
            root.child(currentUser.getUid()).setValue(obj);
        }
    }

    // for handling keyboard
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.imageView || v.getId() == R.id.textView || v.getId() == R.id.layout)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}