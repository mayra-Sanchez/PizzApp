package com.example.pizzapp

object ImageRepository {
    private val images = HashMap<String, String>()

    fun saveImage(id: String, data: String) {
        images[id] = data
    }

    fun getImage(id: String): String? {
        return images[id]
    }

    fun clearImage(id: String) {
        images.remove(id)
    }
}
