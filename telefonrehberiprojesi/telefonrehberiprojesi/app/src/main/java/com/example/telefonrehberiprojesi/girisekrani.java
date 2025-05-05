package com.example.telefonrehberiprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class girisekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_girisekrani);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Timer timer = new Timer();
        new CountDownTimer(3000, 3000) { // 10 saniyelik sayaç, her 1 saniyede tetiklenir
            public void onTick(long millisUntilFinished) {
                Log.d("TIMER", "Kalan süre: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d("TIMER", "Sayaç bitti");
                startActivity(intent);
            }
        }.start();
    }
}