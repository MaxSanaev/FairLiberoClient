package com.example.fairliberoclient.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fairliberoclient.R;
import com.example.fairliberoclient.model.Tournament;
import com.example.fairliberoclient.viewmodel.DatabaseHelper;
import com.example.fairliberoclient.viewmodel.TournamentAdapter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddTeamActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Tournament> tournamentList; // поле для контейнера списка турниров

    private DatabaseHelper database; // поле работы с БД
    private TournamentAdapter adapter; // поле для адаптера
    private EditText editTextaddTeam;
    private Button save;

    private int tournamentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        recyclerView = findViewById(R.id.rw_tournaments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tournamentList = new ArrayList<>(); // выделение памяти и задание типа контейнера для списка заметок
        database = new DatabaseHelper(this); // выделение памяти и задание текущего контекста работы с БД

        // считывание данных из БД и запись их в коллекцию
        try {
            fetchAllTournaments();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // задание структуры вывода данных в recyclerView
        adapter = new TournamentAdapter(this, tournamentList); // инициализация адаптера и передача в рего данных из БД
        recyclerView.setAdapter(adapter); // передача в recyclerView адаптер
        save = findViewById(R.id.save);
        save.setEnabled(false);
        tournamentId = -1;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = editTextaddTeam.getText().toString();


                Intent intent = new Intent(AddTeamActivity.this, AddPlayersActivity.class);
                intent.putExtra("tournamentId", tournamentId);
                intent.putExtra("teamName", teamName);
                AddTeamActivity.this.startActivity(intent);

            }

        });
        editTextaddTeam = findViewById(R.id.editTextaddTeam);
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                tournamentId = adapter.getSelectedId();
                checkRequired();
            }
        });
        editTextaddTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRequired();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // метод считывания из БД всех записей
    public void fetchAllTournaments() throws ParseException {
        // чтение БД и запись данных в курсор
        Cursor cursor = database.getTournaments();

        if (cursor.getCount() == 0) { // если данные отсутствую, то вывод на экран об этом тоста
            Toast.makeText(this, "Турниров нет", Toast.LENGTH_SHORT).show();
        } else { // иначе помещение их в контейнер данных notesList
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (cursor.moveToNext()) {
                // помещение в контейнер tournamentList из курсора данных
                tournamentList.add(new Tournament(
                          cursor.getInt(0)
                        , cursor.getInt(1)
                        , format.parse(cursor.getString(2))
                        , format.parse(cursor.getString(3))
                        , cursor.getInt(4)
                        , cursor.getInt(5)
                        , cursor.getString(6)
                        , cursor.getInt(7)
                        , cursor.getInt(8)
                        , cursor.getInt(9)
                        , cursor.getInt(10)
                ));
            }
        }
    }


    public void checkRequired(){
        boolean enabled = adapter.getSelectedPosition() >= 0 && !editTextaddTeam.getText().toString().isEmpty() && checkTeamName(editTextaddTeam.getText().toString());
        save.setEnabled(enabled);
    }

    // Проверка на уникальность имени команды в турнире
    private boolean checkTeamName(String teamName) {
        if(teamName.isEmpty())
            return false;
        Cursor cursor = database.getTournamentTeams(tournamentId);
        if (cursor.getCount() == 0) { // если данные отсутствуют, то все ок
            return true;
        } else { // иначе проверяем есть ли такая команда
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (cursor.moveToNext()) {
                if(cursor.getString(1) == teamName) {
                    Toast.makeText(this,"Команда с таким именем уже зарегистрирована на турнире!\nкажите другое имя команды!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    }