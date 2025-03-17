package com.example.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.state.TreeUiState
import com.example.presentation.ui.components.TreeNodeItem


@Composable
fun TreeScreen(nodeId: String?, navController: NavController) {
    val viewModel: TreeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(nodeId) {
        viewModel.loadNode(nodeId)
    }

    when (uiState) {
        is TreeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is TreeUiState.Success -> {
            val node = (uiState as TreeUiState.Success).node
            val isRoot = node.parentId == null

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Parent node card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Parent (${node.children.size} children)",
                            modifier = Modifier.padding(bottom = 4.dp),
                            style = MaterialTheme.typography.bodyMedium

                        )
                        Text(text = node.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // List of children
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(node.children, key = { it.id }) { child ->
                        TreeNodeItem(node = child,
                            onNodeClick = { navController.navigate("tree/${child.id}") },
                            onDeleteClick = { viewModel.deleteChild(child.id) })
                    }
                }

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    if (!isRoot) {
                        Button(
                            onClick = {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                }
                            }, modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Back")
                        }
                    }
                    Button(
                        onClick = { viewModel.addChild(node.id) },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Add")
                    }
                    Button(onClick = {
                        viewModel.clearAllNodes()
                        navController.popBackStack(navController.graph.startDestinationId, true)
                        navController.navigate("tree/null")
                    }) {
                        Text("Delete All")
                    }
                }
            }
        }

        is TreeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (uiState as TreeUiState.Error).message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

