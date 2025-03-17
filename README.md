# About app

A structured Android application for managing a tree of nodes, built with Clean Architecture and MVVM. 

# Features

* Display a tree structure with recursive navigation between node levels
* Add new nodes as children of the current node
* Delete individual nodes or clear all nodes
* Generate unique node names using the last 20 bytes of SHA-256 hash
* Persist tree state locally and restore it on app restart

# Tools and Technologies

* Kotlin
* Dagger Hilt
* Room
* Coroutines
* StateFlow
