package com.example.sepiapets.repository

import android.app.Application
import com.example.sepiapets.model.PetList
import com.example.sepiapets.model.WorkHour

class Repository(application: Application) {

    private val fetchData = FetchData(application)

    fun getPetListData(): PetList {
        return fetchData.getPetListResponse();
    }

    fun getWorkHour(): WorkHour {
        return fetchData.getWorkHourResponse();
    }
}