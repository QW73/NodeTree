package com.example.domain.usecase

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository

class GetRootNodeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(): Result<TreeNode> = repository.getRootNode()
}

