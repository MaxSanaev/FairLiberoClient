package com.example.fairliberoclient.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.fairliberoclient.model.Player;
import com.example.fairliberoclient.model.Tournament;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DatabaseName = "FairLibero";
    private static final int DatabaseVersion = 4;

    // Table Tournament
    private static final String tableTournamentName = "tournaments";
    private static final String columnTournamentId = "id";
    private static final String columnTournamentLevel = "level";
    private static final String columnTournamentBeginDate = "beginDate";
    private static final String columnTournamentDeadLine = "deadLine";
    private static final String columnTournamentTeamCount = "teamCount";


    // Table Tournament_Levels
    private static final String tableLevelName = "tournament_levels";
    private static final String columnLevelId = "id";
    private static final String columnLevelName = "name";
    private static final String columnLevelCount1 = "count1";
    private static final String columnLevelCount2 = "count2";
    private static final String columnLevelRating1 = "rating1";
    private static final String columnLevelRating2 = "rating2";


    // Table Players
    private static final String tablePlayersName = "players";
    private static final String columnPlayersId = "id";
    private static final String columnPlayersFamily = "family";
    private static final String columnPlayersName = "name";
    private static final String columnPlayersFatherName = "fatherName";
    private static final String columnPlayersPhoto = "photo";
    private static final String columnPlayersRatingPlayer = "ratingPlayer";
    private static final String columnPlayersRole = "role";
    private static final String columnPlayersHight = "hight";
    private static final String columnPlayersBirstYear = "birstYear";

    // Table Teams
    private static final String tableTeamsName = "teams";
    private static final String columnTeamsId = "id";
    private static final String columnTeamsName = "name";

    // Table PlayersToTeams
    private static final String tablePlayersToTeamsName = "playersToTeams";
    private static final String columnPlayersToTeamsId = "id";
    private static final String columnPlayersToTeamsTeamsId = "teams_id";
    private static final String columnPlayersToTeamsTournamentId = "tournament_Id";
    private static final String columnPlayersToTeamsPlayerId = "player_Id";

    public DatabaseHelper(@Nullable Context context) {
        // задание параметров (контекст, название БД, курсор, версия БД)
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    // метод создания рабочей таблицы в БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу tournament_levels

        String query = "CREATE TABLE " + tableLevelName + " (" + columnLevelId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnLevelName + " TEXT, " + columnLevelCount1 + " INT, " + columnLevelRating1 + " INT, " + columnLevelCount2 + " INT, " + columnLevelRating2 + " INT);";
        db.execSQL(query);

        // Заполняем таблицу tournament_levels демо-данными
        query = "INSERT INTO " + tableLevelName + " (" + columnLevelName + "," + columnLevelCount1 + "," + columnLevelRating1 + "," + columnLevelCount2 + "," + columnLevelRating2 + ")"
                + "VALUES('lite',6,30,7,35), "
                + "('medium',6,38,7,45), "
                + "('hard',6,42,7,50);";

        db.execSQL(query);


        query = "CREATE TABLE " + tableTournamentName + " (" + columnTournamentId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnTournamentLevel + " INT, " + columnTournamentBeginDate + " TEXT, " + columnTournamentDeadLine + " TEXT," + columnTournamentTeamCount + " INT);";
        db.execSQL(query);

        query = "INSERT INTO " + tableTournamentName + " (" + columnTournamentLevel + "," + columnTournamentBeginDate + "," + columnTournamentDeadLine + "," + columnTournamentTeamCount + ")"
                + "VALUES (1,date('now','+5 days'),date('now','+4 days'), 5),"
                + "(1,date('now','+1 days'),date('now','-1 days'), 5),"
                + "(2,date('now','-1 days'),date('now','-2 days'), 8),"
                + "(2,date('now','+3 days'),date('now','+2 days'), 6),"
                + "(3,date('now','+6 days'),date('now','+5 days'), 4),"
                + "(3,date('now','+3 days'),date('now','+2 days'), 5),"
                + "(3,date('now','+2 days'),date('now','+1 days'), 5);";
        db.execSQL(query);

        query = "CREATE TABLE " + tablePlayersName + " (" + columnPlayersId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnPlayersFamily + " TEXT, " + columnPlayersName + " TEXT, " + columnPlayersFatherName + " TEXT, " + columnPlayersPhoto + " BLOB, "
                + columnPlayersRatingPlayer + " INT, " + columnPlayersRole + " TEXT, " + columnPlayersHight + " INT, " + columnPlayersBirstYear + " INT);";
        db.execSQL(query);

        query = "INSERT INTO " + tablePlayersName + " (" + columnPlayersFamily + "," + columnPlayersName + "," + columnPlayersFatherName + ","
                + columnPlayersPhoto + "," + columnPlayersRatingPlayer + "," + columnPlayersRole + "," + columnPlayersHight + "," + columnPlayersBirstYear + ")"
                + "VALUES ('Петров','Александр','Сергеевич',null,8,'Диагональный',185,2003),"
                + "('Алексеев','Иван','Алексеевич',null,6,'блокирующий',198,2006),"
                + "('Фистюлев','Борис','Дмитриевич',null,6,'доигровщик',196,2000),"
                + "('Алексеев','Алексей','Андреевич',null,7,'блокирующий',210,2005),"
                + "('Санаев','Дмитрий','Иосифович',null,8,'доигровщик',186,1968),"
                + "('Афанасьева','Александра','Витальевна',null,3,'пасующий',170,2005),"
                + "('Амалия','Борисовна','Святославовна',null,4,'либеро',175,1995),"
                + "('Иванов','Иван','Иванович',null,4,'либеро',190,1990);";
        db.execSQL(query);

        query = "CREATE TABLE " + tableTeamsName + " (" + columnTeamsId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + columnTeamsName + " TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE " + tablePlayersToTeamsName + " (" + columnPlayersToTeamsId + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnPlayersToTeamsTeamsId + " INT,"
                + columnPlayersToTeamsTournamentId + " INT," + columnPlayersToTeamsPlayerId + " INT);";
        db.execSQL(query);

        this.setPlayersDemoPhotos(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tableTournamentName);
        db.execSQL("DROP TABLE IF EXISTS " + tableLevelName);
        db.execSQL("DROP TABLE IF EXISTS " + tablePlayersName);
        db.execSQL("DROP TABLE IF EXISTS " + tableTeamsName);
        db.execSQL("DROP TABLE IF EXISTS " + tablePlayersToTeamsName);
        // определение запроса на создание таблицы базы данных
        onCreate(db);

    }

    // Добавляем в демо данных фото игроков из ресурсов
    private void setPlayersDemoPhotos(SQLiteDatabase db) {

        Cursor cursor = this.getPlayers(db);
        if (cursor.getCount() == 0) { // если данные отсутствую, то вывод на экран об этом тоста
            return;
        } else { // иначе помещение их в контейнер данных notesList
            while (cursor.moveToNext()) {
                // помещение в контейнер tournamentList из курсора данных
                int id = cursor.getInt(0);
                int imgId = context.getResources().getIdentifier("player" + id, "drawable", context.getPackageName());
                Bitmap bmpPhoto = this.getBitmapFromResources(imgId);
                byte[] photo = this.getBitmapAsByteArray(bmpPhoto);
                ContentValues cv = new ContentValues();
                cv.put(columnPlayersPhoto, photo);
                db.update(tablePlayersName, cv, columnPlayersId + "=" + id, null);
            }
        }

    }

    private Bitmap getBitmapFromResources(int imgId) {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), imgId);
        return bm;
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    // Получаем список игроков
    public Cursor getPlayers() {
        String fields = columnPlayersId + "," + columnPlayersFamily + "," + columnPlayersName + "," + columnPlayersFatherName + "," + columnPlayersPhoto + ","
                + columnPlayersRatingPlayer + "," + columnPlayersRole + "," + columnPlayersHight + "," + columnPlayersBirstYear;
        // формирование запроса к БД
        String query = "SELECT " + fields + " FROM " + tablePlayersName;

        // метод getReadableDatabase() получает БД для чтения
        SQLiteDatabase database = this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (database != null) { // если БД существует, то инициализируем курсор
            cursor = database.rawQuery(query, null);
        }

        // возврат курсора
        return cursor;
    }

    // Получаем список игроков (для записи демо данных)
    public Cursor getPlayers(SQLiteDatabase db) {
        String fields = columnPlayersId + "," + columnPlayersFamily + "," + columnPlayersName + "," + columnPlayersFatherName + "," + columnPlayersPhoto + ","
                + columnPlayersRatingPlayer + "," + columnPlayersRole + "," + columnPlayersHight + "," + columnPlayersBirstYear;
        // формирование запроса к БД
        String query = "SELECT " + fields + " FROM " + tablePlayersName;

        // метод getReadableDatabase() получает БД для чтения
        //db= this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (db != null) { // если БД существует, то инициализируем курсор
            cursor = db.rawQuery(query, null);
        }

        // возврат курсора
        return cursor;
    }

    // Получаем список турниров
    public Cursor getTournaments() {
        String fields = "t." + columnTournamentId + ", t." + columnTournamentLevel + ",t." + columnTournamentBeginDate + ",t." + columnTournamentDeadLine + ",t." + columnTournamentTeamCount
                + ",l." + columnLevelId + ",l." + columnLevelName + ",l." + columnLevelCount1 + ",l." + columnLevelRating1 + ",l." + columnLevelCount2 + ",l." + columnLevelRating2;

        // формирование запроса к БД
        String query = "SELECT " + fields + " FROM " + tableTournamentName + " t"
                + " INNER JOIN " + tableLevelName + " l ON (t." + columnTournamentLevel + " = l." + columnLevelId + ")";

        // метод getReadableDatabase() получает БД для чтения
        SQLiteDatabase database = this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (database != null) { // если БД существует, то инициализируем курсор
            cursor = database.rawQuery(query, null);
        }

        // возврат курсора
        return cursor;
    }

    // Получаем турнир по Id
    public Tournament getTournament(int id) throws ParseException {
        String fields = "t." + columnTournamentId + ", t." + columnTournamentLevel + ",t." + columnTournamentBeginDate + ",t." + columnTournamentDeadLine + ",t." + columnTournamentTeamCount
                + ",l." + columnLevelId + ",l." + columnLevelName + ",l." + columnLevelCount1 + ",l." + columnLevelRating1 + ",l." + columnLevelCount2 + ",l." + columnLevelRating2;

        // формирование запроса к БД
        String query = "SELECT " + fields + " FROM " + tableTournamentName + " t"
                + " INNER JOIN " + tableLevelName + " l ON (t." + columnTournamentLevel + " = l." + columnLevelId + ")"
                + " WHERE (t." + columnTournamentId + " = " + id + ")";

        // метод getReadableDatabase() получает БД для чтения
        SQLiteDatabase database = this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (database != null) { // если БД существует, то инициализируем курсор
            cursor = database.rawQuery(query, null);
        }


        if (cursor != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (cursor.moveToNext()) {
                return new Tournament(
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
                );
            }
        }
        return null;
    }

    // Сохраняем наименование команды и список игроков
    public int addTeam(List<Player> players, Tournament tournament, String teamName) {
        int id = -1;
        // Сохраняем название команды
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnTeamsName, teamName);
        long teamNameId = database.insert(tableTeamsName, null, cv);
        if (teamNameId > 0) {
            // успешно добавили, обавляем игроков
            int tournament_id = tournament.getId();
            boolean ret = true;
            for (Player p : players) {
                cv.clear();
                cv.put(columnPlayersToTeamsTournamentId, tournament_id);
                cv.put(columnPlayersToTeamsTeamsId, teamNameId);
                cv.put(columnPlayersToTeamsPlayerId, p.getId());
                ret = ret && (database.insert(tablePlayersToTeamsName, null, cv) > 0);
            }
            if (ret == true)
                id = (int) teamNameId; // Данные успешно добавлены
        }
        return id;
    }

    // Получаем игроков зарегистрированных в команде по Id команды
    public Cursor getTeamPlayers(int team_id) {
        String fields = "p." + columnPlayersId + ",p." + columnPlayersFamily + ",p." + columnPlayersName + ",p." + columnPlayersFatherName + ",p." + columnPlayersPhoto + ",p."
                + columnPlayersRatingPlayer + ",p." + columnPlayersRole + ",p." + columnPlayersHight + ",p." + columnPlayersBirstYear;
        // формирование запроса к БД
        String query = "SELECT " + fields + " FROM " + tablePlayersName + " p INNER JOIN " + tablePlayersToTeamsName + " pt ON (" + "p." + columnPlayersId + " = " + "pt." + columnPlayersToTeamsPlayerId + ")"
                + " WHERE pt." + columnPlayersToTeamsTeamsId + " = " + team_id + ";";

        // метод getReadableDatabase() получает БД для чтения
        SQLiteDatabase database = this.getReadableDatabase();

        // создаём нулевой курсор
        Cursor cursor = null;

        if (database != null) { // если БД существует, то инициализируем курсор
            cursor = database.rawQuery(query, null);
        }

        // возврат курсора
        return cursor;
    }


}

