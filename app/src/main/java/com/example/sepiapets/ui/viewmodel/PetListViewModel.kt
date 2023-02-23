package com.example.sepiapets.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sepiapets.model.PetList
import com.example.sepiapets.model.WorkHour
import com.example.sepiapets.repository.Repository
import com.example.sepiapets.utils.Utility
import kotlinx.coroutines.launch

//@PetListViewModel Handles Operation fom Repository and Activity
class PetListViewModel(application: Application): AndroidViewModel(application){

    private val repository = Repository(application)
    private val _petList: MutableLiveData<PetList> = MutableLiveData()
    val petList: LiveData<PetList> = _petList
    private val _isWorkingHour: MutableLiveData<Boolean> = MutableLiveData()
    val isWorkingHour: LiveData<Boolean> = _isWorkingHour

    init {
        getPetList()
    }

    // Returns Pet list from pet list Json
    private fun getPetList() = viewModelScope.launch {
        _petList.value = repository.getPetListData()
    }

    // Returns working time from config Json
    private fun getWorkHour(): WorkHour {
        return repository.getWorkHour();
    }

    // Check the Working hour for block the user from accessing the application content
    fun checkWorkingHour() {
        val isWorkingHour = Utility.checkForWorkingHour(getWorkHour())
        _isWorkingHour.postValue(isWorkingHour)
    }
}