package com.example.domain.usecase

import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class GetRootNodeUseCase(private val repository: ITreeRepository) {
    suspend operator fun invoke(): Node = repository.getRootNode()
}