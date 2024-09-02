package com.example.timetable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppRepository @Inject constructor() {
    private val apiService = Retrofit.api

//    suspend fun fetchTables(coroutineScope: CoroutineScope) {
//        coroutineScope.launch {
//            try {
//                val response = apiService.getTimetable()
//                _uiState.value = response.timetableEvent ?: listOf()
//            } catch (e: Exception) {
//                println("error fetching")
//                e.printStackTrace()
//            }
//
//            filterToday()
//        }
//        }
//    }
}
