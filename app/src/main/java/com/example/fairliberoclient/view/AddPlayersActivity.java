package com.example.fairliberoclient.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Player;

import com.example.fairliberoclient.model.Tournament;
import com.example.fairliberoclient.viewmodel.PlayerAdapter;
import com.example.fairliberoclient.viewmodel.DatabaseHelper;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AddPlayersActivity extends AppCompatActivity {
    private ImageButton plus, confirm;
    private RecyclerView recyclerView;
    private DatabaseHelper database;
    private List<Player> playerList;
    private PlayerAdapter adapter;
    private TextView teamName, tournamentLevel, infoForCount1, infoForCount2;

    private Tournament tournament;

    private List<Player> selectedPlayers;

    private int currentRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        currentRating = 0;
        database = new DatabaseHelper(this); // выделение памяти и задание текущего контекста работы с БД
        teamName = findViewById(R.id.teamName);
        tournamentLevel = findViewById(R.id.tournamentLevel);
        plus = findViewById(R.id.plus);
        infoForCount1 = findViewById(R.id.infoForCount1);
        infoForCount2 = findViewById(R.id.infoForCount2);
        confirm = findViewById(R.id.confirm);

        playerList = new ArrayList<>();
        selectedPlayers = new ArrayList<>();
        Intent intent = getIntent();
        int tournamentId = intent.getIntExtra("tournamentId", -1);
        if (tournamentId >= 0) {
            try {
                tournament = database.getTournament(tournamentId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            tournamentLevel.setText(tournament.getLevelName());
            infoForCount1.setText(this.getCurrentRatingString(6));
            infoForCount2.setText(this.getCurrentRatingString(7));


            // считывание данных из БД и запись их в коллекцию
            try {
                fetchAllPlayers();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            teamName.setText(intent.getStringExtra("teamName"));
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Функционал находится в стадии разработки!\nПопробуйте в следующих версиях!", Toast.LENGTH_SHORT);
                    toast.show();
//                Intent intentMy = new Intent(AddPlayersActivity.this, AddPlayerActivity.class);
//                intentMy.putExtra("100", 1);
//                AddPlayersActivity.this.startActivity(intentMy);
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkSaveConditions()) {
                        int teamId = database.addTeam(selectedPlayers, tournament, teamName.getText().toString());
                        if (teamId > 0) {
                            Intent intentMy = new Intent(AddPlayersActivity.this, RegistrationSuccessActivity.class);
                            intentMy.putExtra("team_id", teamId);
                            intentMy.putExtra("team_name", teamName.getText().toString());
                            intentMy.putExtra("tournament_id", tournament.getId());
                            AddPlayersActivity.this.startActivity(intentMy);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Ошибка при регистрации команды!\nКоманда не зарегистрирована!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Выберите правильное количество игроков!\nКоманда не зарегистрирована!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            recyclerView = findViewById(R.id.recyclerViewPlayers);
            recyclerView.setLayoutManager(new LinearLayoutManager(this)); // задание структуры вывода данных в recyclerView
            adapter = new PlayerAdapter(this, playerList, tournament); // инициализация адаптера и передача в рего данных из БД
            recyclerView.setAdapter(adapter); // передача в recyclerView адаптер
            recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                    selectedPlayers = adapter.getSelectedPlayers();
                    getCurrentRating();
                    infoForCount1.setText(getCurrentRatingString(6));
                    infoForCount2.setText(getCurrentRatingString(7));
                }
            });
        }
    }


    // роверка условий на возможность сохранения команды
    private boolean checkSaveConditions() {
        return (selectedPlayers.size() == tournament.getCount1() || selectedPlayers.size() == tournament.getCount2())
                && ((selectedPlayers.size() == tournament.getCount1() && currentRating <= tournament.getRating1())
                || (selectedPlayers.size() == tournament.getCount2() && currentRating <= tournament.getRating2()));
    }

    private void getCurrentRating() {
        currentRating = 0;
        for (Player player : selectedPlayers) {
            currentRating = currentRating + player.getRatingPlayer();
        }
    }

    private String getCurrentRatingString(int baseCount) {
        int baseRating = 0;
        switch (baseCount) {
            case 6:
                baseRating = tournament.getRating1();
                break;
            case 7:
                baseRating = tournament.getRating2();
                break;
            default:
                return "Нет данных";
        }
        return "Лимит игроков: " + baseCount + "\n Отобрано: " + ((selectedPlayers.size() == 0) ? "0" : selectedPlayers.size()) + " Рейтинг: " + currentRating + "/" + baseRating;

    }

    public void fetchAllPlayers() throws ParseException {
        // чтение БД и запись данных в курсор
        Cursor cursor = database.getPlayers(tournament.getId());

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
