package pranjal.learning.organdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    TextInputLayout t1, t2;
    ProgressBar bar;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextInputLayout) findViewById(R.id.email);
        t2 = (TextInputLayout) findViewById(R.id.pwd);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    // directing to SignUp intent
    public void goToSignUp(View view) {

        startActivity(new Intent(Login.this, SignUp.class));
        finish();
    }

    // user login with fireBase
    public void loginHere(View view) {

        bar.setVisibility(View.VISIBLE);
        final String email = t1.getEditText().getText().toString();
        String password = t2.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();

                            // for general user
                            currentUser = mAuth.getInstance().getCurrentUser();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor_user");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(snapshot.hasChild(currentUser.getUid()))
                                    {
                                        bar.setVisibility(View.INVISIBLE);
                                        t1.getEditText().setText("");
                                        t2.getEditText().setText("");
                                        startActivity(new Intent(Login.this, dashboard_doc.class));
                                    }
                                    else
                                    {
                                        bar.setVisibility(View.INVISIBLE);
                                        t1.getEditText().setText("");
                                        t2.getEditText().setText("");
                                        startActivity(new Intent(Login.this, DashBoard.class));
                                    }
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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