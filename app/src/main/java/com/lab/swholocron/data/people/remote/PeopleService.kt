package com.lab.swholocron.data.people.remote

import com.lab.swholocron.data.people.Person
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {
    @GET("people")
    fun searchPeople(@Query("search") query: String): Call<SearchPeopleResponse>

    @GET("people/{id}")
    fun getPerson(@Path("id") id: String): Call<Person>
}