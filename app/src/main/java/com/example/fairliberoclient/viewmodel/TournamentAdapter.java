package com.example.fairliberoclient.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Tournament;

import java.util.List;


public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {
    private Context context;

    private List<Tournament> tournament;

    private int selectedPosition;

    private int selectedId;

    public TournamentAdapter(Context context, List<Tournament> tournament) {
        this.context = context;
        this.tournament = tournament;
        selectedPosition = selectedId = -1;
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
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#40FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#F5000000"));
        }
        holder.tournamentInfo.setText((tournament.get(position)).getTournamentInfo());
        holder.tournamentId.setText((tournament.get(position)).getId() + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition == -1) {
                    selectedPosition = position;
                    selectedId = (tournament.get(position)).getId();
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
        return tournament.size();
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
