package fr.uavignon.ceri.onzeur

import com.beust.klaxon.*

private val klaxon = Klaxon()


data class ResponseAPI (
    val action: String,
    val titre: String,
    val artiste: String,
    val imgURL: String,
    val ref: String,
    val statusCode: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<ResponseAPI>(json)
    }
}
