package com.odc.dataapp.http

import com.odc.dataapp.models.Comments
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Requettes {
    val baseUrl = "https://jsonplaceholder.typicode.com/"

    fun getInstance(): Retrofit {
        // convertire le json en notre modele de données data class
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}

interface API {
    @GET("/comments")
    // suspend (ne pas s'executer sur le meme thread que l'interface)
    suspend fun covidDataHttp(): Response<List<Comments>>?
}