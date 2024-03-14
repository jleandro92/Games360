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
    ImageView imageView;
    String pathFile;
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
            editDesc.setText(editarJogos.getDesc());
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
                jogo.setDesc(editDesc.getText().toString());

                if (btnPoli.getText().toString().equals("Cadastrar")){
                    bdHelper.salvarGame(jogo);
                    bdHelper.close();
                }else {
                    bdHelper.editarGame(jogo);
                    bdHelper.close();
                }
            }
        });

        btnFoto = findViewById(R.id.btnFoto);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return;
            }
        }
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPictureTakeAction();
            }
        });
        imageView = findViewById(R.id.image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathFile);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void dispatchPictureTakeAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if (photoFile != null){
                pathFile = photoFile.getAbsolutePath();
                Uri photURI = FileProvider.getUriForFile(Formulario.this, "com.example.games.provider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photURI);
                startActivityForResult(takePic, 1);
            }
        }
    }

    private File createPhotoFile(){
       String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
       File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
       File image = null;
       try {
           image = File.createTempFile(name, ".jpg", storageDir);
       }catch (IOException e){
           Log.d("mylog", "Excep : " + e.toString());
       }
       return image;
    }
}