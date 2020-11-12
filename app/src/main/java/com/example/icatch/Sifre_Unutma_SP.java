package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
public class Sifre_Unutma_SP extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sifre_unutma);
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.EmaileditText);
    }

    public void ResetPassword(View view)
    {
        String email = emailText.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(Sifre_Unutma_SP.this,"Mail Boş Geçilemez",Toast.LENGTH_LONG).show();
        }
        else
        {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        //check e-mail exsist
                        Toast.makeText(Sifre_Unutma_SP.this,"Şifre Sıfırlama Maili , Mailinize Gönderilmiştir",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Sifre_Unutma_SP.this,GirisLogin_SP.class);
                        startActivity(intent);
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(Sifre_Unutma_SP.this,message,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }
}
