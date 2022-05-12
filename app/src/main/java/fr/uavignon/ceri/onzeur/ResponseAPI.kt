package fr.uavignon.ceri.onzeur

import com.beust.klaxon.*

private val klaxon = Klaxon()


data class ResponseAPI (
    val action: String,
    val title: String,
    val artist: String,
    val ref: String,
    val status: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<ResponseAPI>(json)
    }
}
