package com.sangeeth.email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText recipient, subject, cc, message;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipient = findViewById(R.id.recipient);
        cc = findViewById(R.id.cc);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.body);
        send = findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }

    public void sendEmail(){
        Log.i("send email","inside function");
        String[] recipientList = recipient.getText().toString().split(",");
        String[] ccList = cc.getText().toString().split(",");
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientList);
        emailIntent.putExtra(Intent.EXTRA_CC, ccList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email"));
            finish();
            Log.i("Finished sending email ...","");
        }catch (ActivityNotFoundException ex){
            Toast.makeText(MainActivity.this, "There is no email client installed", Toast.LENGTH_SHORT).show();
        }




    }
}