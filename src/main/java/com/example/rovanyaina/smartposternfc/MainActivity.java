package com.example.rovanyaina.smartposternfc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView desc,mai,call;
    private ImageButton im1,im2,im3;

    String uriz;
    String latitude,longitude,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im1 = (ImageButton) findViewById(R.id.imageCall);
        im2 = (ImageButton) findViewById(R.id.imageMap);
        im3 = (ImageButton) findViewById(R.id.imageMail);
        desc = (TextView ) findViewById(R.id.textdesc);
        mai = (TextView ) findViewById(R.id.maill);
        call = (TextView ) findViewById(R.id.appel);
        String jsons= getIntent().getStringExtra("json");
        //Log.d("json",json);
        String nume="";

        try {
            JSONObject jsonObj = new JSONObject(jsons);
            nume = jsonObj.getString("telephone");
            latitude=jsonObj.getString("longitude");
            longitude=jsonObj.getString("latitude");
            desc.setText(jsonObj.getString("description"));
            call.setText(jsonObj.getString("telephone"));
            mai.setText(jsonObj.getString("email"));
            mail=jsonObj.getString("email");

            //Log.d("titre", latitude);
            uriz = "tel:" + nume;




            im1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);

                    callIntent.setData(Uri.parse(uriz));

                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            });

            im2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent inte = new Intent(MainActivity.this, MapsActivity.class);
                    inte.putExtra("latitude", latitude);
                    inte.putExtra("longitude", longitude);
                    startActivity(inte);
                }
            });
            im3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    sendEmail(mail);
                }
            });
        }catch (final JSONException e){

        }

    }

    protected void sendEmail(String mail) {
        Log.i("Send email", "");
        String[] TO = {mail};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Envoi mail..."));
            finish();
            Log.i("Mail envoye", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Aucun Email Envoye.", Toast.LENGTH_SHORT).show();
        }

    }
}
