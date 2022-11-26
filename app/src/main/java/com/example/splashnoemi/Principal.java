package com.example.splashnoemi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashnoemi.Json.MyData;
import com.example.splashnoemi.Json.MyInfo;
import com.example.splashnoemi.MyAdapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    private TextView usuario;
    private ListView listView;
    Button regresor;
    private List<MyData> list;
    public static String TAG = "ola";
    private int []images = { R.drawable.cerrar,R.drawable.llave};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        MyData myData = null;
        usuario = findViewById(R.id.textUser);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<MyData>();

        regresor= (Button)findViewById(R.id.salir);
        regresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresador = new Intent(Principal.this, Login.class);
                startActivity(regresador);
            }
        });
        if( intent != null)
        {
            aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                usuario.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("MyInfo");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        info = (MyInfo) object;
                        usuario.setText("X-type te da la bienvenida usuario: " + info.getUsuario() );
                        list = info.getContras();
                    }
                }
            }
        }

        MyAdapter myAdapter = new MyAdapter( list , getBaseContext() );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        MyInfo info = null;
        Object object = null;
        Intent intent = getIntent();
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        switch (item.getItemId() ) {
            case R.id.agregarId:
                Intent olvideContra = new Intent(Principal.this, Agregacontra.class);
                olvideContra.putExtra("MyInfo", info);
                startActivity(olvideContra);
                break;
            case R.id.elimId:
                Intent elimContra = new Intent(Principal.this, eliminacontra.class);
                elimContra.putExtra("MyInfo", info);
                startActivity(elimContra);
                break;
            case R.id.editarId:
                Intent editaContra = new Intent(Principal.this, editacontra.class);
                editaContra.putExtra("MyInfo", info);
                startActivity(editaContra);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
    private void toast( int i )
    {
        Toast.makeText(getBaseContext(), list.get(i).getContra(), Toast.LENGTH_SHORT).show();
    }
}

