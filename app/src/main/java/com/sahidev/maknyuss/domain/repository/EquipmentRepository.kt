package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.model.Equipment

interface EquipmentRepository {
    suspend fun addEquipment(equipment: Equipment)
}