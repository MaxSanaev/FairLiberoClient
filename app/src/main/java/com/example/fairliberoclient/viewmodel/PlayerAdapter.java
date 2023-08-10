package com.example.fairliberoclient.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Player;
import com.example.fairliberoclient.model.Tournament;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private Context context;

    private List<Player> player;

    private List<Player> selectedPlayers;

    private Tournament tournament;

    private boolean readOnly; // апрет выбора элементов

    public PlayerAdapter(Context context, List<Player> player, Tournament tournament) {
        this.context = context;
        this.player = player;
        this.tournament = tournament;
        this.selectedPlayers = new ArrayList<>();
        readOnly = false;

    }

    public PlayerAdapter(Context context, List<Player> player, Tournament tournament, boolean read_only) {
        this.context = context;
        this.player = player;
        this.tournament = tournament;
        this.selectedPlayers = new ArrayList<>();
        this.readOnly = read_only;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText((player.get(position)).getId() + "");
        holder.playerInfo.setText((player.get(position)).getPlayerInfo());
        Bitmap img = this.getImage((player.get(position)).getPhoto());
        holder.photo.setImageBitmap(img);
        holder.photo.setClipToOutline(true); // cкругляем углы

        Player selectedPlayer = player.get(position);
        if (readOnly == false) {
            int ind = selectedPlayers.indexOf(selectedPlayer);
            if (ind >= 0) {
                holder.itemView.setBackgroundColor(Color.parseColor("#40FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#F5000000"));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Player selectedPlayer = player.get(position);
                    int ind = selectedPlayers.indexOf(selectedPlayer);
                    if (ind >= 0) {
                        // Снимаем выделение с игрока
                        selectedPlayers.remove(ind);
                    } else {
                        // Выделяем игрока
                        if (checkAddPlayersRestriction(selectedPlayer))
                            selectedPlayers.add(selectedPlayer);
                        else {
                            Toast toast = Toast.makeText(context, "Вы уже выбрали максимальное количество игроков или рейтинг.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    // Проверка можно ли добавить игрока
    private boolean checkAddPlayersRestriction(Player player) {
        int currentRating = 0;
        for (Player p : selectedPlayers) {
            currentRating = currentRating + p.getRatingPlayer();
        }
        currentRating = currentRating + player.getRatingPlayer();
        return selectedPlayers.size() <= tournament.getCount2()
                && currentRating <= tournament.getRating2();
    }


    // convert from byte array to bitmap
    private Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getItemCount() {
        return player.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, playerInfo;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            id = itemView.findViewById(R.id.id);
            playerInfo = itemView.findViewById(R.id.playerInfo);
        }
    }

    public List<Player> getSelectedPlayers() {
        return selectedPlayers;
    }
}