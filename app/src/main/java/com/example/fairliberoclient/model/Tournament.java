package com.example.fairliberoclient.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tournament {
    //поля турнира
    private int id;
    private int level;
    private Date beginDate;
    private Date deadLine;
    private int teamCount;

    //поля уровня турнира
    private int idLevel;
    private String levelName;
    private int count1;
    private int rating1;
    private int count2;
    private int rating2;


    public Tournament(int id, int level, Date beginDate, Date deadLine, int teamCount, int idLevel, String levelName, int count1, int rating1, int count2, int rating2) {
        this.id = id;
        this.level = level;
        this.beginDate = beginDate;
        this.deadLine = deadLine;
        this.teamCount = teamCount;
        this.idLevel = idLevel;
        this.levelName = levelName;
        this.count1 = count1;
        this.rating1 = rating1;
        this.count2 = count2;
        this.rating2 = rating2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getRating1() {
        return rating1;
    }

    public void setRating1(int rating1) {
        this.rating1 = rating1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public int getRating2() {
        return rating2;
    }

    public void setRating2(int rating2) {
        this.rating2 = rating2;
    }

    public String getTournamentInfo() {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");

        return "Уровень: " + this.levelName + " Дата: " + sdf1.format(this.beginDate) + "\n"
                + "Рейтинг для " + this.count1 + " игроков: " + this.rating1 + "\n"
                + "Рейтинг для " + this.count2 + " игроков: " + this.rating2 + "\n"
                + "Дедлайн заявки: " + sdf1.format(this.deadLine);

    }

}
