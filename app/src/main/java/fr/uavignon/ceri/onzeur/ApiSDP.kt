package fr.uavignon.ceri.onzeur

import okhttp3.*
import okhttp3.internal.wait
import okio.IOException


class ApiSDP {
    val IP : String = "http://192.168.5.66:9090/"
    val _ENDPOINT: String = "http://192.168.5.66:8080/RESTreaming/"
    private val client = OkHttpClient()
    private var ref: String = "null"

    fun setRef(ref : String) { this.ref = ref}

    fun getRef(): String { return ref}


    fun playSong(artiste: String, titre: String) {
        if (ref != "null") {
            stopSong()
            ref = "null"
        }
        val url : String = _ENDPOINT + "play/" + artiste + "/" + titre;
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    //println(response.body!!.string())
                    ref = response.body!!.string()
                    println(ref)
                }
            }
        })

    }

    fun pauseSong() {
        val url : String = _ENDPOINT + "pause/" + ref
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }

    fun resumeSong() {
        val url : String = _ENDPOINT + "resume/" + ref
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }

    fun stopSong() {
        val url : String = _ENDPOINT + "stop/" + ref

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }



}