package com.example.telefonrehberiprojesi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class rehber_kaydet extends AppCompatActivity {
    // Değişkenler
Button btn_iptal, btn_kaydet;
EditText adTxt, soyadTxt, numaraTxt;
String ad, soyad, numara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rehber_kaydet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SQLiteDatabase database;
        // Databesi oluşturur, oluşturulmuşsa açar
        database=openOrCreateDatabase("rehber",MODE_PRIVATE,null);

        // Butonlar, edittextler
        btn_iptal = findViewById(R.id.btn_iptal);
        btn_kaydet = findViewById(R.id.btnKaydet);
        adTxt = findViewById(R.id.adTxt);
        soyadTxt = findViewById(R.id.soyadTxt);
        numaraTxt = findViewById(R.id.numaraTxt);
        Intent anasayfa = new Intent(getApplicationContext(), MainActivity.class);


        btn_kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad = adTxt.getText().toString();
                soyad = soyadTxt.getText().toString();
                numara = numaraTxt.getText().toString();

                // Kaydetme işlemi
                String KAYDET = "INSERT INTO rehber(adi, soyadi, numara) VALUES (?,?,?)"; // Kaydetme komudu
                SQLiteStatement durum = database.compileStatement(KAYDET);
                durum.bindString(1,ad);
                durum.bindString(2,soyad);
                durum.bindString(3,numara);
                durum.execute(); // Komudu çalıştırma

                Toast.makeText(rehber_kaydet.this, "Kayıt Edildi", Toast.LENGTH_SHORT).show();
                startActivity(anasayfa); // Kaydetme sonrası ana sayfaya dönüş
            }
        });

        btn_iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rehber_kaydet.this, "Geri dönüldü", Toast.LENGTH_SHORT).show();

                startActivity(anasayfa); // İptal butonu, ana sayfaya dönüş
            }
        });
    }
}