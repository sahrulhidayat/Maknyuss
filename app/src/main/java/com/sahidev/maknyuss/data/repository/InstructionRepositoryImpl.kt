package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.repository.InstructionRepository
import javax.inject.Inject

class InstructionRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : InstructionRepository {
    override suspend fun addInstruction(instruction: Instruction) {
        localDataSource.addInstruction(instruction)
    }
}