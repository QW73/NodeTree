package com.example.domain.usecase

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository

class GetNodeByIdUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(id: String): Result<TreeNode> = repository.getNodeById(id)
}