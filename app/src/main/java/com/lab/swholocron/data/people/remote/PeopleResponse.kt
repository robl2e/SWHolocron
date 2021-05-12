package com.lab.swholocron.data.people.remote

import com.lab.swholocron.data.people.Person


data class SearchPeopleResponse(val page: Int, val results: List<Person>)