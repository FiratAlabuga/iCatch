package com.example.icatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText,passwordText,confirmpasswordText;
    GoogleSignInClient mGoogleSignInClient;
    private ImageButton Google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg);
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.EmailEditText);
        passwordText = findViewById(R.id.PasswordEditText);
        confirmpasswordText = findViewById(R.id.ConfirmPasswordEditText);

    }

    public void Kaydol(View view) {
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        String confirmpassword = confirmpasswordText.getText().toString();

        if(password.matches(confirmpassword))
        {
            //verify email
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {Toast.makeText(MainActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Kisisel_Bilgiler_SP.class);
                        intent.putExtra("userEmail", email);
                        intent.putExtra("userPassword",password);
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Hata" + e.getLocalizedMessage().toString());
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            Toast.makeText(MainActivity.this,"Şifreler Eşleşmiyor",Toast.LENGTH_SHORT).show();
        }


    }

    public void GrisGit(View view) {
        Intent intent = new Intent(MainActivity.this,GirisLogin_SP.class);
        startActivity(intent);
    }

}