package fr.uavignon.ceri.onzeur

import okhttp3.*
import okhttp3.internal.wait
import okio.IOException


class ApiSDP {
    public val IP : String = "http://192.168.85.86:9090/"
    public val _ENDPOINT: String = "http://192.168.85.86:8080/RESTreaming/"
    private val client = OkHttpClient()
    public var ref: String? = null

    public fun playSong(artiste: String, titre: String) {
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
                    ref = response.body?.string()
                }
            }
        })

    }

    public fun pauseSong() {
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

    public fun resumeSong() {
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

    public fun stopSong() {
        val request = Request.Builder()
            .url(_ENDPOINT)
            .url("stop/")
            .url(ref!!)
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