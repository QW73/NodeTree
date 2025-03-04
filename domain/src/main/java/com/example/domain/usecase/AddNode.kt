package com.example.domain.usecase

import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class AddNodeUseCase(private val repository: ITreeRepository) {
    suspend operator fun invoke(parentId: String?, name: String): Result<Node> =
        repository.addNode(parentId, name)
}