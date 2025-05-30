package com.esp32flower.data

import com.esp32flower.data.Tank
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val firestore: FirebaseFirestore
) {
    suspend fun getMeasures(): List<Measure> {
        val snapshot = firestore.collection("esp32_firestore").document("measurements")
            .collection("dev1")
            .get()
            .await() // czekaj asynchronicznie na wynik

        return snapshot.documents.mapNotNull { document ->
            mapDocumentToMeasure(document.data)
        }
    }

    suspend fun getTankInfo(): Tank {
        val snapshot = firestore
            .collection("esp32_firestore")
            .document("measurements")
            .get()
            .await()

        val data = snapshot.data
        return mapDocumentToTank(data) ?: Tank(500, 1000, false)
    }

    suspend fun updateTankCapacity(newCapacity: Int) {
        firestore
            .collection("esp32_firestore")
            .document("measurements")
            .update("tank_size", newCapacity)
            .await()
    }

    suspend fun updateWaterLevel(newLevel: Int)  {
        firestore
            .collection("esp32_firestore")
            .document("measurements")
            .update("water_condition", newLevel)
            .await()
    }

    suspend fun updateRunPump(isPumpOn: Boolean) {
        firestore
            .collection("esp32_firestore")
            .document("measurements")
            .update("user_run_pomp", isPumpOn)
            .await()
    }


    private fun mapDocumentToMeasure(data: Map<String, Any>?): Measure? {
        if (data == null) return null
        return Measure(
            airTemperature = (data["temperature"] as? String)?.toFloat() ?: 0f,
            airHumidity = (data["air_humidity"] as? String)?.toFloat() ?: 0f,
            soilHumidity = (data["soil_humidity"] as? String)?.toFloat() ?: 0f,
            lightIntensity = (data["light"] as? String)?.toFloat() ?: 0f,
            time = (data["time"] as Timestamp)
        )
    }

    private fun mapDocumentToTank(data: Map<String, Any>?): Tank? {
        if (data == null) return null
        return Tank(
            waterLevel = (data["water_condition"] as? Number)?.toInt() ?: 0,
            tankSize = (data["tank_size"] as? Number)?.toInt() ?: 0,
            isPumpOn = (data["user_run_pomp"] as? Boolean) ?: false
        )
    }



}
