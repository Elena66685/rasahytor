package com.example.rosakhutor;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Login {
    private int id;
    private String login;
    private LocalDateTime dayTime;


    Login(int id, String login, LocalDateTime dayTime){
        this.id = id;
        this.login = login;
        this.dayTime = dayTime;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDayTime() {
        return dayTime;
    }

    public String getLogin() {
        return login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayTime(LocalDateTime dayTime) {
        this.dayTime = dayTime;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
