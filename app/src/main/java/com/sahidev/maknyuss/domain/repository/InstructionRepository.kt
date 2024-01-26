package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.model.Instruction

interface InstructionRepository {
    suspend fun addInstruction(instruction: Instruction)
}