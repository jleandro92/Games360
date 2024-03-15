package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.games.BDHelper.GamesDB;
import com.example.games.model.Games;
import com.example.games.model.GamesAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    GamesDB bdHelper;
    ArrayList<Games> listGame;
    //ArrayAdapter adapter;
    Games jogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnCad = (Button) findViewById(R.id.btnCad);
        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Formulario.class);
                startActivity(intent);
            }
        });

        lista = (ListView) findViewById(R.id.listGame);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Games jogosEsc = (Games) adapter.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, Formulario.class);
                i.putExtra("games-esc", jogosEsc);
                startActivity(i);
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                jogo = (Games) adapter.getItemAtPosition(position);
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuItem menuDelete = menu.add("Deletar Game");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                bdHelper = new GamesDB(MainActivity.this);
                bdHelper.deletarGame(jogo);
                bdHelper.close();

                carregarGames();
                return true;
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        carregarGames();
    }

    public void carregarGames(){
        bdHelper = new GamesDB(MainActivity.this);
        listGame = bdHelper.getLista();
        bdHelper.close();

        if (listGame != null){
            GamesAdapter adapter = new GamesAdapter(MainActivity.this, listGame);
            lista.setAdapter(adapter);
        }
    }

}