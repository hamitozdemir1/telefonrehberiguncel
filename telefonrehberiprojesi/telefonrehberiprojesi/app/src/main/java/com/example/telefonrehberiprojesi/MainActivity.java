package com.example.telefonrehberiprojesi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ListView listView;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.buttonKaydet);
        listView = findViewById(R.id.liste);

        database = openOrCreateDatabase("rehber", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS rehber(id INTEGER PRIMARY KEY, adi VARCHAR, soyadi VARCHAR, numara VARCHAR)");

        // Cursor databasedeki verileri çekmeye yarar
        Cursor cursor = database.rawQuery("SELECT * FROM rehber", null);

        ArrayList<String> kisiListesi = new ArrayList<>();


        // Sırasıyla baştan sonra verileri çekip listviewe yazdırır
        while (cursor.moveToNext()) {
            String ad = cursor.getString(cursor.getColumnIndexOrThrow("adi"));
            String soyad = cursor.getString(cursor.getColumnIndexOrThrow("soyadi"));
            String numara = cursor.getString(cursor.getColumnIndexOrThrow("numara"));

            String satir = ad + " " + soyad + " - " + numara;
            kisiListesi.add(satir);
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kisiListesi);
        listView.setAdapter(adapter);

        btn.setOnClickListener(v -> {
            Intent kaydet = new Intent(getApplicationContext(), rehber_kaydet.class);
            startActivity(kaydet);
        });

        // Listviewdeki bir iteme tıklayınca o item
        listView.setOnItemClickListener((parent, view, position, id) -> {

            Cursor cursor2 = database.rawQuery("SELECT * FROM rehber", null);
            int sayac = 0;
            while (cursor2.moveToNext()) {
                if (sayac == position) { // Tıklanılan kişinin verilerini çeker
                    int kisiId = cursor2.getInt(cursor2.getColumnIndexOrThrow("id"));
                    String adi = cursor2.getString(cursor2.getColumnIndexOrThrow("adi"));
                    String soyadi = cursor2.getString(cursor2.getColumnIndexOrThrow("soyadi"));
                    String numara = cursor2.getString(cursor2.getColumnIndexOrThrow("numara"));

                    Intent intent = new Intent(MainActivity.this, DuzenleActivity.class);
                    intent.putExtra("id", kisiId);
                    intent.putExtra("adi", adi);
                    intent.putExtra("soyadi", soyadi);
                    intent.putExtra("numara", numara);
                    startActivity(intent);
                    break;
                }
                sayac++;
            }
            cursor2.close();
        });

    }
}
