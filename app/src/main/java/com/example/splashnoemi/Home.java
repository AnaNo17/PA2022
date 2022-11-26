package com.example.splashnoemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashnoemi.Json.MyInfo;

public class Home extends AppCompatActivity {
    Button regresor, contras;
    private TextView usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        usuario = findViewById(R.id.textUser);
        Intent intent = getIntent();
        contras= (Button)findViewById(R.id.Contras);
        contras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contra = new Intent(Home.this, Principal.class);
                startActivity(contra);
            }
        });
        if( intent != null)
        {
            aux = intent.getStringExtra("usuario" );
            if( aux != null && aux.length()> 0 )
            {
                usuario.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("MyInfo");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        info = (MyInfo) object;
                        usuario.setText("Bienvenido " + info.getUsuario());
                    }
                }
            }
        }

        regresor= (Button)findViewById(R.id.salir);
        regresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresador = new Intent(Home.this, Login.class);
                startActivity(regresador);
            }
        });

    }
}