package com.example.splashnoemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OlvideContra extends AppCompatActivity {
    Button Regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);
        Regresar = (Button)findViewById(R.id.Regreso);
        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regreso = new Intent(OlvideContra.this, Login.class);
                startActivity(regreso);
            }
        });
    }
}