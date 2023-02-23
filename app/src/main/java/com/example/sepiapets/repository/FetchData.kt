package com.example.sepiapets.repository

import android.app.Application
import com.example.sepiapets.model.PetList
import com.example.sepiapets.model.WorkHour
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException

/*
*  @FetchData class help's to fetch required data
* */
class FetchData(private val application: Application) {

    private val gson = Gson()

    // Returns list of Pet from pets_list.json
    fun getPetListResponse(): PetList {
        var response = "";
        try {
            response = application.assets.open("pets_list.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return gson.fromJson(response, PetList::class.java)
    }

    // Returns Work Hour from config.json
    fun getWorkHourResponse(): WorkHour {
        var response = "";
        try {
            response = application.assets.open("config.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return gson.fromJson(response, WorkHour::class.java)
    }
}