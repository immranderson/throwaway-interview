package com.example.hsexercise.feature

data class FeatureListViewState(
    val items: List<ItemRow> = listOf()
)

data class ItemRow(
    val authorName: String,
    val xDimen: String,
    val yDimen: String,
    val imageUrl: String
)