package com.example.games.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.games.R;

import java.util.List;

public class GamesAdapter extends ArrayAdapter<Games> {
    private Context mContext;
    private List<Games> mGamesList;

    public GamesAdapter(Context context, List<Games> gamesList) {
        super(context, 0, gamesList);
        mContext = context;
        mGamesList = gamesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_game, parent, false);
        }

        Games currentGame = mGamesList.get(position);



        TextView nomeTextView = listItem.findViewById(R.id.nomeTextView);
        nomeTextView.setText(currentGame.getNomeGame());

        TextView anoTextView = listItem.findViewById(R.id.anoTextView);
        anoTextView.setText("Ano de lançamento: " + String.valueOf(currentGame.getAno()));

        TextView generoTextView = listItem.findViewById(R.id.generoTextView);
        generoTextView.setText("Gênero: " + currentGame.getGenero());


        return listItem;
    }
}
