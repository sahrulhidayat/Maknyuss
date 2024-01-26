package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.repository.EquipmentRepository
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : EquipmentRepository {
    override suspend fun addEquipment(equipment: Equipment) {
        localDataSource.addEquipment(equipment)
    }
}