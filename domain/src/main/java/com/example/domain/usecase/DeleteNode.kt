package com.example.domain.usecase

import com.example.domain.repository.TreeRepository

class DeleteNodeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteNode(id)
}


