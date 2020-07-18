package com.goldenbirds.ptpconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,pass,cnfpass;
    Button signupbtn;
    ProgressDialog dialog;
    private FirebaseAuth mAuth;
    String namest,emailst,passst,cnfpassst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        name=findViewById(R.id.signupusername);
        email=findViewById(R.id.signupemail);
        pass=findViewById(R.id.signuppass);
        cnfpass=findViewById(R.id.signupcnfpass);
        signupbtn=findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namest=name.getText().toString().trim();
                emailst=email.getText().toString().trim();
                passst=pass.getText().toString().trim();
                cnfpassst=cnfpass.getText().toString().trim();
                if(namest.equals("") || emailst.equals("")|| passst.equals("") ||cnfpassst.equals("")){
                    Toast.makeText(SignupActivity.this, "Please fill up All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(!passst.equals(cnfpassst)){
                    Toast.makeText(SignupActivity.this, "Password and Confrim Password Do not Match", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressdialog();
                    dialog.show();
                    createAccount(emailst,passst);
                }

            }
        });
    }


    public  void createAccount(String email,String  password){
        Log.i("test",""+email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if(dialog!=null){
                                dialog.dismiss();
                            }
                        }
                    }
                });
    }
    public  void progressdialog(){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(true);

    }


}