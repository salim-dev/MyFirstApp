package com.example.formation.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText pseudo;
    public final static String INTENT_KEY_PSEUDO = "com.example.formation.myfirstapp.MainActivity.INTENT_KEY_PSEUDO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome");

        btnSend = (Button) findViewById(R.id.btn_send);

        pseudo = (EditText) findViewById(R.id.pseudo);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pseudo.length() >= 3){
                    //Displaying Toast with Pseudo message
//                    Toast.makeText(getApplicationContext(),pseudo.getText(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(INTENT_KEY_PSEUDO, pseudo.getText().toString());
                    startActivity(intent);
                }

            }
        });




    }
}
