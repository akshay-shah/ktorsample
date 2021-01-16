package models

import kotlinx.serialization.Serializable
val movieStorage = mutableListOf<Movie>(
    Movie(id = "101",movieName = "Dark",movieGenre = "thriller",releaseDate = "2017"),
    Movie(id = "102",movieName = "Inception",movieGenre = "sci-fi",releaseDate = "2015")
)

@Serializable
data class Movie(val id: String, val movieName: String, val movieGenre: String, val releaseDate: String)