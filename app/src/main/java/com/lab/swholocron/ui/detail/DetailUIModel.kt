package com.lab.swholocron.ui.detail

import com.lab.swholocron.data.people.Person

data class DetailUIModel(
    val name: String,
    val birthYear: String,
    val gender: String,
    val height: String,
    val mass: String,
    val abbrevName: String
)

fun Person.to(): DetailUIModel {
    return DetailUIModel(
        name = name,
        birthYear = birth_year,
        gender = gender,
        height = height,
        mass = mass,
        abbrevName = name.substring(0, 2)
    )
}
