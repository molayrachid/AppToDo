package com.zouhaoui.nabil.apptodo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hp on 13/04/2017.
 */

public class album implements Serializable {
    private int id;
    private String name;
    private Date date;

    public album(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
