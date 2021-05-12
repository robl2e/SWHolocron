package com.lab.swholocron.ui.main

import com.lab.swholocron.data.people.Person

data class SearchResultUIModel(val name: String, val uri: String, val abbrevName: String)

fun Person.to(): SearchResultUIModel {
    return SearchResultUIModel(
        name = name,
        uri = url,
        abbrevName = name.substring(0, 2)
    )
}

fun List<Person>.to(): List<SearchResultUIModel> {
    val results = mutableListOf<SearchResultUIModel>()
    for (person in this) {
        results.add(person.to())
    }
    return results
}
