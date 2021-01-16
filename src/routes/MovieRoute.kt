package com.akshayshah.ktorexample.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.Movie
import models.movieStorage

/**
 * Routing for movies
 */
fun Route.movieRouting() {
    /**
     * this is an all in one route for movies
     */
    route("/movie") {
        /**
         * this would get all movies in our temporary database
         */
        get {
            if (movieStorage.isNotEmpty()) {
                call.respond(movieStorage)
            } else {
                call.respondText("No movies found", status = HttpStatusCode.NotFound)
            }
        }
        /**
         * this would get movies with provided id
         */
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val movie =
                movieStorage.find { it.id == id } ?: return@get call.respondText(
                    "No movie with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(movie)
        }
        /**
         * this would add a movie to our temporary database
         */
        post {
            val movie = call.receive<Movie>()
            movieStorage.add(movie)
            call.respondText("Movie stored correctly", status = HttpStatusCode.Accepted)
        }
        /**
         * this would delete a movie from our temporary database
         */
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (movieStorage.removeIf { it.id == id }) {
                call.respondText("Movie removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.registerMovieRoutes() {
    routing {
        movieRouting()
    }
}