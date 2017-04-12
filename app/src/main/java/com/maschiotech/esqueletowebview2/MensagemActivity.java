package com.maschiotech.esqueletowebview2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MensagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        Button tentarNovamenteBtn = (Button) findViewById(R.id.tentarNovamenteButton);
        tentarNovamenteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MensagemActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}
