package com.example.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.TreeNavigator
import com.example.presentation.databinding.FragmentTreeBinding
import com.example.presentation.state.TreeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TreeFragment : Fragment() {
    private var _binding: FragmentTreeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TreeViewModel by viewModels()
    private lateinit var adapter: TreeNodeAdapter

    @Inject
    lateinit var treeNavigator: TreeNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        val nodeId = arguments?.getString("nodeId")

        viewModel.loadNode(nodeId)

        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        binding.addButton.setOnClickListener {
            val currentNode = (viewModel.uiState.value as? TreeUiState.Success)?.node
            currentNode?.id?.let { viewModel.addChild(it) }
        }

        binding.backButton.setOnClickListener {
            treeNavigator.navigateUp()
        }

        binding.deleteAllButton.setOnClickListener {
            viewModel.clearAllNodes()
        }
    }

    private fun setupRecyclerView() {
        adapter = TreeNodeAdapter(onNodeClick = { nodeId ->
            treeNavigator.navigateToChild(nodeId)
        }, onDeleteClick = { nodeId ->
            viewModel.deleteChild(nodeId)
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateUiVisibility(state)
                when (state) {
                    is TreeUiState.Success -> {

                        val isRoot = state.node.parentId == null
                        binding.backButton.isVisible = true && !isRoot
                        val childCount = state.node.children.size
                        binding.parentNode.text = getString(R.string.label_node_parent, childCount)
                        binding.parentNodeName.text = state.node.name
                        adapter.submitList(state.node.children)

                    }

                    is TreeUiState.Error -> binding.parentNodeName.text = state.message
                    is TreeUiState.Loading -> Unit
                }
            }
        }
    }

    private fun updateUiVisibility(state: TreeUiState) {
        binding.progressBar.isVisible = state is TreeUiState.Loading
        binding.parentNodeNameCard.isVisible = state !is TreeUiState.Loading
        binding.recyclerView.isVisible = state is TreeUiState.Success
        binding.deleteAllButton.isVisible = state is TreeUiState.Success
        binding.backButton.isVisible = state is TreeUiState.Success
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
