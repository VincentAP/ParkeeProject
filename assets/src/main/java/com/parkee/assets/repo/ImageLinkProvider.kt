package com.parkee.assets.repo

object ImageLinkProvider {
    private val imageLink =
        mapOf(
            "AUD" to "https://www.linkpicture.com/q/aud_flag.png",
            "USD" to "https://www.linkpicture.com/q/usd_flag.png",
            "EUR" to "https://www.linkpicture.com/q/eur_flag.png",
            "GBP" to "https://www.linkpicture.com/q/gbp_flag.png"
        )

    fun getImageLink(key: String): String? = imageLink[key]
}