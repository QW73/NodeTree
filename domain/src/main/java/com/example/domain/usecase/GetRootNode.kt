package com.example.domain.usecase

import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class GetRootNodeUseCase(private val repository: ITreeRepository) {
    suspend operator fun invoke(): Result<Node> = repository.getRootNode()
}