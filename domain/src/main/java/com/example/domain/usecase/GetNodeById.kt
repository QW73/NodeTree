package com.example.domain.usecase

import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class GetNodeByIdUseCase(private val repository: ITreeRepository) {
    suspend operator fun invoke(id: String): Result<Node> = repository.getNodeById(id)
}