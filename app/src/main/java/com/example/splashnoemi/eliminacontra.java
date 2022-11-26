package com.example.splashnoemi;

import static com.example.splashnoemi.Registro.archivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashnoemi.Json.MyData;
import com.example.splashnoemi.Json.MyInfo;
import com.example.splashnoemi.MyAdapter.MyAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class eliminacontra extends AppCompatActivity {
    public static String TAG = "mensaje";
    private List<MyData> lista;
    Button eliminacontra, regresor;
    private ListView listView;
    private EditText contra2, red;
    private TextView contrasena, red2;
    private int []images = { R.drawable.icono1,R.drawable.icono2,R.drawable.icono3, R.drawable.icono4, R.drawable.icono5,
            R.drawable.icono6, R.drawable.icono7, R.drawable.icono8, R.drawable.icono9, R.drawable.icono10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminacontra);
        listView = (ListView) findViewById(R.id.listViewID);
        eliminacontra = findViewById(R.id.eliminacontra);
        Intent intent = getIntent();
        Object object = null;
        MyInfo info = null;
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        lista = info.getContras();
        MyAdapter myAdapter = new MyAdapter( lista , getBaseContext() );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast(i);
            }
        });
        regresor= (Button)findViewById(R.id.regresor1);
        regresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresador = new Intent(eliminacontra.this, Principal.class);
                startActivity(regresador);
            }
        });
        eliminacontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = null;
                MyInfo info = null;
                List<MyInfo> list =new ArrayList<MyInfo>();
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                info.setContras(lista);
                List2Json(info,list);
                Intent intent2 = new Intent(eliminacontra.this, Principal.class);
                intent2.putExtra("MyInfo", info);
                startActivity(intent2);
            }
        });

    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
    private int toast(int i)
    {
        lista.remove(i);
        Toast.makeText(getBaseContext(),"eliminado", Toast.LENGTH_SHORT).show();
        return lista.size();
    }
}
