package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

data class ApiSection(
    @SerializedName("name")
    val name: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("items")
    val items: List<ApiSectionItem> = emptyList(),
    @SerializedName("template")
    val template: ApiTemplate? = ApiTemplate.OTHER,
)