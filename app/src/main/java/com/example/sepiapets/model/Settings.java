package com.example.sepiapets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

@SerializedName("workHours")
@Expose
private String workHours;

public String getWorkHours() {
return workHours;
}

public void setWorkHours(String workHours) {
this.workHours = workHours;
}

}