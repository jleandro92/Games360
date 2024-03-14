package com.example.games.model;

import java.io.Serializable;

public class Games implements Serializable{
    private Long id;
     private String nomeGame;
     private int ano;
     private String genero;
     private String desc;

    public String toString(){
        return "Game: " + nomeGame + "\n" +
                "Lançamento: " + ano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeGame() {
        return nomeGame;
    }

    public void setNomeGame(String nomeGame) {
        this.nomeGame = nomeGame;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
