package com.mid_202102316_binardi;

import static com.mid_202102316_binardi.R.id.btntampil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nim,nama,jeniskelamin,alamat,email;
    Button simpan, tampil, hapus, edit;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nim = findViewById(R.id.edtnim);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtnim = nim.getText().toString();
                String txtnama = nama.getText().toString();
                String txtjeniskelamin = jeniskelamin.getText().toString();
                String txtalamat = alamat.getText().toString();
                String txtemail = email.getText().toString();

                if (TextUtils.isEmpty(txtnim) || TextUtils.isEmpty(txtnama) || TextUtils.isEmpty(txtjeniskelamin)
                        || TextUtils.isEmpty(txtalamat) || TextUtils.isEmpty(txtemail)){
                    Toast.makeText(MainActivity.this,"Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                }else {
                    Boolean checkkode = db.checknim(txtnim);
                    if (checkkode == false){
                        Boolean insert = db.insertData(txtnim,txtnama,txtjeniskelamin,txtalamat,txtemail);
                        if (insert == true){
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),BiodataActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MainActivity.this,"Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilData();
                if (res.getCount()==0){
                    Toast.makeText(MainActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("NIM Mahasiswa : " + res.getString(0) + "\n");
                    buffer.append("Nama Mahasiswa : " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin : " + res.getString(2) + "\n");
                    buffer.append("Alamat : " + res.getString(3) + "\n");
                    buffer.append("Email : " + res.getString(4) + "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });


    }
}