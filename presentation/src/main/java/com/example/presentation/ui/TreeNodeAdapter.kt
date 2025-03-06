package com.example.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TreeNode
import com.example.presentation.databinding.ItemTreeNodeBinding

class TreeNodeAdapter(
    private val onNodeClick: (String) -> Unit, private val onDeleteClick: (String) -> Unit
) : ListAdapter<TreeNode, TreeNodeAdapter.NodeViewHolder>(NodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
        val binding =
            ItemTreeNodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NodeViewHolder(private val binding: ItemTreeNodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(node: TreeNode) {
            binding.nodeName.text = node.name
            binding.root.setOnClickListener { onNodeClick(node.id) }
            binding.deleteButton.setOnClickListener { onDeleteClick(node.id) }
        }
    }
}

object NodeDiffCallback : DiffUtil.ItemCallback<TreeNode>() {
    override fun areItemsTheSame(oldItem: TreeNode, newItem: TreeNode): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TreeNode, newItem: TreeNode): Boolean =
        oldItem == newItem
}