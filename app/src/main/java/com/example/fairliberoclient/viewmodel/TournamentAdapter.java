package com.example.fairliberoclient.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Tournament;

import java.util.ArrayList;
import java.util.List;


public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {
    private Context context;

    private List<Tournament> tournaments;

    private int selectedPosition;

    private int selectedId;

    // Турниры, регистрация на которые запрещена
    private List<Tournament> disabledTournaments;

    public TournamentAdapter(Context context, List<Tournament> tournaments) {
        this.context = context;
        this.tournaments = tournaments;
        selectedPosition = selectedId = -1;
        disabledTournaments = new ArrayList<>();
        for(Tournament t : tournaments) {
            if(!t.checkEnabledRegistration()) {
                disabledTournaments.add(t);
            }
        }
    }

    public int getSelectedId() {
        return this.selectedId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournaments_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if((tournaments.get(position)).checkEnabledRegistration()) {

            if (selectedPosition == position) {
                // Выбранный турнир
                holder.itemView.setBackgroundColor(Color.parseColor("#40FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#F5000000"));
            }
        } else {
            // Отмечаем запрещенные к регистрации турниры
            holder.itemView.setBackgroundColor(Color.parseColor("#80FF0000"));
        }
        holder.tournamentInfo.setText((tournaments.get(position)).getTournamentInfo());
        holder.tournamentId.setText((tournaments.get(position)).getId() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(disabledTournaments.contains(tournaments.get(position))) {
                    Toast.makeText(context,"Турнир недоступен для регистрацции!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedPosition == -1) {
                    selectedPosition = position;
                    selectedId = (tournaments.get(position)).getId();
                } else {
                    selectedPosition = selectedId = -1;
                }
                notifyDataSetChanged();

            }
        });

    }


    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tournamentInfo, tournamentId;

        ViewHolder(@NonNull View view) {
            super(view);
            tournamentInfo = view.findViewById(R.id.tournamentInfo);
            tournamentId = view.findViewById(R.id.tournamentId);

        }

    }
}
