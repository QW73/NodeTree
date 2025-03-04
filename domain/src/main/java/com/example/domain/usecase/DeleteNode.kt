package com.example.domain.usecase

import com.example.domain.repository.ITreeRepository

class DeleteNodeUseCase(private val repository: ITreeRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteNode(id)
}


