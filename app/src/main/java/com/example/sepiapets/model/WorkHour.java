package com.example.sepiapets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkHour {

@SerializedName("settings")
@Expose
private Settings settings;

public Settings getSettings() {
return settings;
}

public void setSettings(Settings settings) {
this.settings = settings;
}

}