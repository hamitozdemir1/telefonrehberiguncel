package com.example.telefonrehberiprojesi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DuzenleActivity extends AppCompatActivity {

    // Edittextler butonlar değişkenler
    EditText editAd, editSoyad, editNumara;
    Button btnGuncelle, btnSil;
    SQLiteDatabase database;
    int kisiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duzenle);

        editAd = findViewById(R.id.editAd);
        editSoyad = findViewById(R.id.editSoyad);
        editNumara = findViewById(R.id.editNumara);
        btnGuncelle = findViewById(R.id.btnGuncelle);
        btnSil = findViewById(R.id.btnSil);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        // Veritabanını aç
        database = openOrCreateDatabase("rehber", MODE_PRIVATE, null);

        // Diğer sayfadan gelen verileri al
        kisiId = getIntent().getIntExtra("id", -1);
        String adi = getIntent().getStringExtra("adi");
        String soyadi = getIntent().getStringExtra("soyadi");
        String numara = getIntent().getStringExtra("numara");

        // Tıklanılan kişinin bilgilerini ekrana yazdır
        editAd.setText(adi);
        editSoyad.setText(soyadi);
        editNumara.setText(numara);

        // Güncelleme işlemi
        btnGuncelle.setOnClickListener(v -> {
            String yeniAd = editAd.getText().toString();
            String yeniSoyad = editSoyad.getText().toString();
            String yeniNumara = editNumara.getText().toString();

            String sql = "UPDATE rehber SET adi = ?, soyadi = ?, numara = ? WHERE id = ?"; // SQL komudu güncelleme

            SQLiteStatement durumupdate = database.compileStatement(sql);
            durumupdate.bindString(1,yeniAd);
            durumupdate.bindString(2,yeniSoyad);
            durumupdate.bindString(3,yeniNumara);
            durumupdate.bindLong(4, kisiId);
            durumupdate.execute();
            Toast.makeText(this, "Kayıt güncellendi", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        // Silme işlemi
        btnSil.setOnClickListener(v -> {
            String sql = "DELETE FROM rehber WHERE id = ?";
            SQLiteStatement durumdelete = database.compileStatement(sql);
            durumdelete.bindLong(1, kisiId);
            durumdelete.execute();

            Toast.makeText(this, "Kayıt silindi", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }
}
