package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
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

public class GirisLogin_SP extends AppCompatActivity {
    EditText emailText,passwordText;
    TextView forgotpassword,SignUpButton;
    private FirebaseAuth firebaseAuth;
    String userEmail = "";
    String userPassword = "";

    GoogleSignInClient mGoogleSignInClient;
    private ImageButton Google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_log);
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.EmailEditText);
        passwordText = findViewById(R.id.PasswordEditText);
        forgotpassword = findViewById(R.id.ForgotPassword);
        SignUpButton = findViewById(R.id.girisYap);
        userEmail =  emailText.getText().toString();
        userPassword = passwordText.getText().toString();

    }

    public void gir(View view){
        if(userEmail != "" && userPassword != "")
        { userEmail = emailText.getText().toString();
            userPassword = passwordText.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(GirisLogin_SP.this,AnaHome_SP.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GirisLogin_SP.this,"Şifreniz Yanlış",Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(GirisLogin_SP.this,"Alanlara Boş Geçilemez",Toast.LENGTH_LONG).show();
        }
    }

    public void ForgotPassword(View view)
    {
        Intent intent = new Intent(GirisLogin_SP.this,Sifre_Unutma_SP.class);
        startActivity(intent);
    }
    public void SignUpActivity(View view)
    {
        Intent intent = new Intent(GirisLogin_SP.this,MainActivity.class);
        startActivity(intent);
    }

}
