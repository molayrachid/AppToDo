package com.zouhaoui.nabil.apptodo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 13/04/2017.
 */

public class Albums implements Serializable {

    private List<album> albums = new ArrayList<>();

    public Albums() {
    }

    public List<album> getAlbums() {
        return albums;
    }
}
