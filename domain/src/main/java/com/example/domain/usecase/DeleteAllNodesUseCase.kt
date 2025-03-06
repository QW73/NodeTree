package com.example.domain.usecase

import com.example.domain.repository.TreeRepository

class DeleteAllNodesUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.deleteAllNodes()
}