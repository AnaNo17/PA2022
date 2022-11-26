package com.example.splashnoemi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.splashnoemi.Json.MyData;
import com.example.splashnoemi.Json.MyInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Registro extends AppCompatActivity {
    Switch SwitchH;
    RadioButton sexoB;
    Button login;
    Button Registro;
    ImageButton Calendario;
    EditText FechaNac;
    List<MyData> lista;
    int []images = { R.drawable.icono1,R.drawable.icono2,R.drawable.icono3};
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte []res = null;
    String pass, mascotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        lista = new ArrayList<MyData>();
        sexoB = (RadioButton)findViewById(R.id.radioBMujer);
        SwitchH= (Switch) findViewById(R.id.idswitch);
        login = (Button)findViewById(R.id.sesion);
        setContentView(R.layout.activity_registro);
        List<MyInfo> list =new ArrayList<MyInfo>();
        Registro = findViewById(R.id.Registro);
        Button Registro = findViewById(R.id.Registro);
        Button login = findViewById(R.id.sesion);
        ImageButton calendario = findViewById(R.id.imageButton);
        RadioButton sexoB = findViewById(R.id.radioBMujer);
        Switch SwitchH = findViewById(R.id.idswitch);

        EditText usuario = findViewById(R.id.txtUsu);
        EditText pswd = findViewById(R.id.TxtContra);
        EditText mail = findViewById(R.id.txtCorreo);
        EditText edad = findViewById(R.id.txtEdad);

        EditText FechaNac = findViewById(R.id.TxtNac);
        EditText Telefono = findViewById(R.id.TxtTel);
        CheckBox trabajador = findViewById(R.id.checkBoxEsTra);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Registro.this, Login.class);
                startActivity(login);
            }
        });
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String fecha = i+"/"+i1+"/"+i2;
                        FechaNac.setText(fecha);
                    }
                }, anio, mes, dia);
                dpd.show();
            }
        });

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                res = createSha1(String.valueOf(pswd.getText())+"ola");
                if( res != null ) {
                    Log.d(TAG, String.format("%s", bytesToHex(res)));
                    pass = bytesToHex(res);
                }
                boolean estadoRb = sexoB.isChecked();
                String sexo = null;
                if (estadoRb==true){
                    sexo = "Femenino";
                }else{
                    sexo = "Masculino";
                }

                boolean estadoCb = trabajador.isChecked();
                String tipoU = null;
                if(estadoCb==true){
                    tipoU = "Trabajador";
                }else{
                    tipoU = "Estudiante";
                }

                if(SwitchH.isChecked()){
                    mascotas = "Tiene mascotas";
                }else{
                    mascotas = "No tiene mascotas";
                }
                MyData myData = null;
                for( int i = 0; i < 3; i++)
                {
                    myData = new MyData();
                    myData.setContra( String.format( "Contraseña%d" , (int)(Math.random()*10000) ) );
                    if(i==0){
                        myData.setRed(String.format( "Facebook"));
                        myData.setImage(images[0]);
                    }
                    if(i==1){
                        myData.setRed(String.format( "Instagram"));
                        myData.setImage(images[1]);
                    }
                    if(i==2){
                        myData.setRed(String.format( "Whatsapp" ));
                        myData.setImage(images[2]);
                    }
                    lista.add(myData);
                }
                MyInfo info= new MyInfo();
                info.setUsuario(String.valueOf(usuario.getText()));
                info.setPassword(pass);
                info.setCorreo(String.valueOf(mail.getText()));
                info.setEdad(String.valueOf(edad.getText()));
                info.setSexo(sexo);
                info.setTusu(tipoU);
                info.setHijos(mascotas);
                info.setTelefono(String.valueOf(Telefono.getText()));
                info.setFechaNac(String.valueOf(FechaNac.getText()));
                info.setContras(lista);
                List2Json(info,list);
            }
        });
    }
    public void Objet2Json(MyInfo info){
        Gson gson =null;
        String json= null;
        String mensaje = null;
        gson =new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
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
    public byte[] createSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}