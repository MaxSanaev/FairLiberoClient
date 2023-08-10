package com.example.fairliberoclient.model;

import java.util.Calendar;


public class Player {
    private int id;
    private String family;
    private String name;
    private String fatherName;
    private byte[] photo;
    private int ratingPlayer;
    private String role;
    private int hight;
    private int birstYear;

    public Player(int id, String family, String name, String fatherName, byte[] photo, int ratingPlayer, String role, int hight, int birstYear) {
        this.id = id;
        this.family = family;
        this.name = name;
        this.fatherName = fatherName;
        this.photo = photo;
        this.ratingPlayer = ratingPlayer;
        this.role = role;
        this.hight = hight;
        this.birstYear = birstYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getRatingPlayer() {
        return ratingPlayer;
    }

    public void setRatingPlayer(int ratingPlayer) {
        this.ratingPlayer = ratingPlayer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public int getBirstYear() {
        return birstYear;
    }

    public void setBirstYear(int birstYear) {
        this.birstYear = birstYear;
    }


    public String getPlayerInfo() {
        int current = Calendar.getInstance().get(Calendar.YEAR);

        return this.family + " " + this.name + " " + this.fatherName + "\nрейтинг: " + this.ratingPlayer +"\n"
                + "рост: " + this.hight + " см " + "\nвозраст (лет): " + (current - this.birstYear);
    }

}
