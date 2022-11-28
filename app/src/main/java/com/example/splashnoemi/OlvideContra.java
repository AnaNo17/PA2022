package com.example.splashnoemi;

import static com.example.splashnoemi.Registro.archivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.splashnoemi.Json.MyInfo;
import com.example.splashnoemi.data.des.MyDesUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class OlvideContra extends AppCompatActivity {
    Button olvideContra, Regresar;
    EditText usuario;
    public static final String TAG = "Noemi";
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    byte []res = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);
        olvideContra = findViewById(R.id.button2);
        Regresar = findViewById(R.id.Regreso);
        usuario = findViewById(R.id.usuarioO);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OlvideContra.this, Login.class);
                startActivity(intent);
            }
        });
        olvideContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                MyDesUtil myDesUtil = null;
                String testCifrado = null;
                String testCifrado2 = null;
                MyDesUtil myDesUtil2 = null;
                Intent intent = getIntent();
                String texto = null;
                String usu = null;
                String password = null;
                String pass = null;
                String mens = null;
                String correo = null;
                Object object = null;
                MyInfo info = null;
                List<MyInfo> list =new ArrayList<MyInfo>();
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                usu = usuario.getText().toString();
                if(usu.equals(info.getUsuario())){
                    correo = info.getCorreo();
                    password = String.format ("Contrasena%d", (int)(Math.random()*100) );
                    mens = "<html><body><h1>Tu nueva contraseña es: "+password+"</h1></body></html>";
                    res = createSha1(password+"NuevaContrasena");
                    pass = bytesToHex(res);
                    info.setPassword(pass);
                    List2Json(info,list);
                    Log.d(TAG, correo);
                    Log.d(TAG, mens);
                    myDesUtil = new MyDesUtil();
                    myDesUtil2 = new MyDesUtil();
                    if( isNotNullAndNotEmpty( KEY ) )
                    {
                        myDesUtil.addStringKeyBase64( KEY );
                        myDesUtil2.addStringKeyBase64( KEY );
                    }
                    if( !isNotNullAndNotEmpty( correo ) )
                    {
                        return;
                    }
                    if( !isNotNullAndNotEmpty( mens ) )
                    {
                        return;
                    }
                    testCifrado = myDesUtil.cifrar( correo );
                    testCifrado2 = myDesUtil2.cifrar(mens);
                    if( !isNotNullAndNotEmpty( testCifrado ) )
                    {
                        return;
                    }
                    Log.i( TAG , testCifrado );
                    if( !isNotNullAndNotEmpty( testCifrado2 ) )
                    {
                        return;
                    }
                    Log.i( TAG , testCifrado2);
                    if( texto == null || texto.length() == 0 )
                    {
                        Toast.makeText(getBaseContext() , "Text is null" , Toast.LENGTH_LONG );
                    }
                    if(sendInfo(testCifrado, testCifrado2))
                    {
                        Log.i( TAG , "Se envio");
                        return;
                    }
                    Toast.makeText(getBaseContext() , "Error en el envío" , Toast.LENGTH_LONG );
                }else{
                    Toast.makeText(getBaseContext() , "Usuario incorrecto" , Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    public boolean sendInfo( String text, String text2 )
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( text == null || text.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , text );
            jsonObject.put("mensaje" , text2 );
            Log.i(TAG, String.valueOf(jsonObject));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }
    public boolean isNotNullAndNotEmpty( String aux )
    {
        return aux != null && aux.length() > 0;
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