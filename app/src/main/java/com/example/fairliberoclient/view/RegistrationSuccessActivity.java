package com.example.fairliberoclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Player;
import com.example.fairliberoclient.model.Tournament;
import com.example.fairliberoclient.viewmodel.DatabaseHelper;
import com.example.fairliberoclient.viewmodel.PlayerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistrationSuccessActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseHelper database;

    private List<Player> playerList;

    private Tournament tournament;

    private String teamName;

    private int teamId, tournamentId;

    private PlayerAdapter adapter;
    private TextView registrationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);
        database = new DatabaseHelper(this); // выделение памяти и задание текущего контекста работы с БД
        playerList = new ArrayList<>();

        Intent intent = getIntent();
        teamName = intent.getStringExtra("team_name");
        tournamentId = intent.getIntExtra("tournament_id", -1);
        teamId = intent.getIntExtra("team_id", -1);
        if (tournamentId > 0) {
            try {
                tournament = database.getTournament(tournamentId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");

            String s = "Команда " + teamName + " успешно зарегистрирована на турнир уровня " + tournament.getLevelName() + "\n"
                    + "дата проведения: " + sdf1.format(tournament.getBeginDate());

            registrationInfo = findViewById(R.id.registrationInfo);
            registrationInfo.setText(s);

            // считывание данных из БД и запись их в коллекцию
            try {
                fetchAllNotes();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            recyclerView = findViewById(R.id.recyclerViewPlayers);
            recyclerView.setLayoutManager(new LinearLayoutManager(this)); // задание структуры вывода данных в recyclerView
            adapter = new PlayerAdapter(this, playerList, tournament, true); // инициализация адаптера без возможности выбора игроков и передача в него данных из БД
            recyclerView.setAdapter(adapter); // передача в recyclerView адаптера
        }
    }

    public void fetchAllNotes() throws ParseException {
        // чтение БД и запись данных в курсор
        Cursor cursor = database.getPlayers();

        if (cursor.getCount() == 0) { // если данные отсутствую, то вывод на экран об этом тоста
            Toast.makeText(this, "Игроков нет", Toast.LENGTH_SHORT).show();
        } else { // иначе помещение их в контейнер данных notesList
            while (cursor.moveToNext()) {
                // помещение в контейнер tournamentList из курсора данных
                playerList.add(new Player(
                        cursor.getInt(0)
                        , cursor.getString(1)
                        , cursor.getString(2)
                        , cursor.getString(3)
                        , cursor.getBlob(4)
                        , cursor.getInt(5)
                        , cursor.getString(6)
                        , cursor.getInt(7)
                        , cursor.getInt(8)));
            }
        }
    }
}