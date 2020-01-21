package com.example.myapplication.Models;

import org.json.JSONArray;

public class Model {
    private String name;
    private String short_Desc;
    private String thumb_URL;
    private String video_URL;
    private String description;
    private String image;
    private JSONArray jsonArray1 = new JSONArray();
    private JSONArray jsonArray2 = new JSONArray();


    //----------------Constructor for TheFragment---------------------

    public Model(String short_Desc, String description, String video_URL, String thumb_URL) {
        this.short_Desc = short_Desc;
        this.description = description;
        this.video_URL = video_URL;
        this.thumb_URL = thumb_URL;
    }


    //---------------------Constructor For MainActivity-----------------------------

    public Model(String name, JSONArray jsonArray1, JSONArray jsonArray2, String image) {
        this.name = name;
        this.jsonArray1 = jsonArray1;
        this.jsonArray2 = jsonArray2;
        this.image = image;
    }

    //----------------Methods Get And Sets----------------------------


    public String getImage() {
        return image;
    }


    public JSONArray getJsonArray1() {
        return jsonArray1;
    }

    public JSONArray getJsonArray2() {
        return jsonArray2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_Desc() {
        return short_Desc;
    }


    public String getDescription() {
        return description;
    }


    public String getVideo_URL() {
        return video_URL;
    }


    public String getThumb_URL() {
        return thumb_URL;
    }

}
