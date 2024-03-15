package com.example.games;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.games.BDHelper.GamesDB;
import com.example.games.model.Games;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formulario extends AppCompatActivity {


    EditText editJogo, editAno, editGenero, editDesc;
    Button btnPoli;
    Button btnFoto;
    ImageView image;
    Games editarJogos, jogo;
    GamesDB bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        jogo = new Games();
        bdHelper = new GamesDB(Formulario.this);

        Intent intent = getIntent();
        editarJogos = (Games) intent.getSerializableExtra("games-esc");


        editJogo = (EditText) findViewById(R.id.editJogo);
        editAno = (EditText) findViewById(R.id.editAno);
        editGenero = (EditText) findViewById(R.id.editGenero);
        editDesc = (EditText) findViewById(R.id.editDesc);

        btnPoli = (Button) findViewById(R.id.btnPoli);

        if (editarJogos != null){
            btnPoli.setText("Editar");

            editJogo.setText(editarJogos.getNomeGame());
            editAno.setText(editarJogos.getAno()+"");
            editGenero.setText(editarJogos.getGenero());
            editDesc.setText(editarJogos.getDescricao());
            jogo.setId(editarJogos.getId());
        }else {
            btnPoli.setText("Cadastrar");
        }

        btnPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogo.setNomeGame(editJogo.getText().toString());
                jogo.setAno(Integer.parseInt(editAno.getText().toString()));
                jogo.setGenero(editGenero.getText().toString());
                jogo.setDescricao(editDesc.getText().toString());

                if (btnPoli.getText().toString().equals("Cadastrar")){
                    bdHelper.salvarGame(jogo);
                    bdHelper.close();
                }else {
                    bdHelper.editarGame(jogo);
                    bdHelper.close();
                }
                finish();
            }
        });
        btnFoto = (Button) findViewById(R.id.btnFoto);
        image = (ImageView) findViewById(R.id.image);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }
    private void takePicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bitmap b = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(b);
        }

    }

}