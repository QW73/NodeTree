package com.example.domain.usecase

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository

class AddNodeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(parentId: String?): Result<TreeNode> = repository.addNode(parentId)
}