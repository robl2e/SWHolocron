package com.lab.swholocron.data.people

import com.lab.swholocron.data.common.Failure
import com.lab.swholocron.data.common.Result
import com.lab.swholocron.data.common.Success
import com.lab.swholocron.data.people.remote.PeopleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface PeopleRepository {
    suspend fun search(query: String): Result<List<Person>, Exception>
    suspend fun get(id: String): Result<Person, Exception>
}

class PeopleRepositoryImpl(private val service: PeopleService) :
    PeopleRepository {

    override suspend fun search(query: String): Result<List<Person>, Exception> =
        withContext(Dispatchers.IO) {
            val response = service.searchPeople(query).execute()

            if (response.isSuccessful) {
                val peopleResponse = response.body()
                peopleResponse?.let {
                    it.results.let { people ->
                        return@withContext Success<List<Person>, Exception>(
                            people
                        )
                    }
                }
            }
            return@withContext Failure<List<Person>, Exception>(RuntimeException("Failed to get listing"))
        }

    override suspend fun get(id: String): Result<Person, Exception> = withContext(Dispatchers.IO) {
        val response = service.getPerson(id).execute()

        if (response.isSuccessful) {
            val person = response.body()
            person?.let {
                return@withContext Success<Person, Exception>(it)
            }
        }
        return@withContext Failure<Person, Exception>(RuntimeException("Failed to get person"))
    }

}