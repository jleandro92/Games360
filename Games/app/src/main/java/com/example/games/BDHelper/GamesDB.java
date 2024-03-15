package com.example.games.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.games.model.Games;

import java.util.ArrayList;

public class GamesDB extends SQLiteOpenHelper {

    private static final String DATABASE = "bdgame";

    private static final int VERSION = 1;

    public GamesDB (Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String game = "CREATE TABLE game(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeGame TEXT NOT NULL, ano INTEGER, genero TEXT, descricao TEXT, imagePath TEXT);";
        db.execSQL(game);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String game = "DROP TABLE IF EXISTS game";
        db.execSQL(game);
    }

    public void salvarGame(Games jogos){
        ContentValues values = new ContentValues();

        values.put("nomeGame", jogos.getNomeGame());
        values.put("ano", jogos.getAno());
        values.put("genero", jogos.getGenero());
        values.put("desc", jogos.getDescricao());

        getWritableDatabase().insert("game", null, values);
    }

    public void editarGame(Games jogos){
        ContentValues values = new ContentValues();

        values.put("nomeGame", jogos.getNomeGame());
        values.put("ano", jogos.getAno());
        values.put("genero", jogos.getGenero());
        values.put("desc", jogos.getDescricao());

        String [] args = {jogos.getId().toString()};
        getWritableDatabase().update("game", values,"id=?", args);
    }

    public void deletarGame(Games jogos){
        String [] args = {jogos.getId().toString()};
        getWritableDatabase().delete("game","id=?", args);
    }

    public ArrayList<Games> getLista(){
        String[] columns = {"id", "nomeGame", "ano", "genero", "desc"};
        Cursor cursor = getWritableDatabase().query("game", columns, null, null, null, null, null, null);
        ArrayList<Games> jogos = new ArrayList<Games>();

        while (cursor.moveToNext()){
            Games jogo = new Games();
            jogo.setId(cursor.getLong(0));
            jogo.setNomeGame(cursor.getString(1));
            jogo.setAno(cursor.getInt(2));
            jogo.setGenero(cursor.getString(3));
            jogo.setDescricao(cursor.getString(4));

            jogos.add(jogo);

        }
        return jogos;
    }
}
