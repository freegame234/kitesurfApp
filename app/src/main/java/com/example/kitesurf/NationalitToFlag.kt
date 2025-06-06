package com.example.kitesurf

fun nationalityToFlag(countryName: String): String {
    val countryToCode = mapOf(
        "france" to "FR",
        "algérie" to "DZ",
        "suède" to "SE",
        "allemagne" to "DE",
        "espagne" to "ES",
        "italie" to "IT",
        "états-unis" to "US",
        "royaume-uni" to "GB",
        "canada" to "CA",
        "brésil" to "BR",
        "chine" to "CN",
        "japon" to "JP",
        "corée du sud" to "KR",
        "maroc" to "MA",
        "tunisie" to "TN",
        "sénégal" to "SN",
        "belgique" to "BE",
        "suisse" to "CH",
        "pays-bas" to "NL",
        "portugal" to "PT",
        "australie" to "AU",
        "nouvelle-zélande" to "NZ",
        "inde" to "IN",
        "russie" to "RU",
        "mexique" to "MX",
        "argentine" to "AR",
        "colombie" to "CO",
        "afrique du sud" to "ZA"
    )

    val code = countryToCode[countryName.lowercase()] ?: return "🏳️"

    return code.uppercase().map {
        Character.toChars(0x1F1E6 + it.code - 'A'.code).concatToString()
    }.joinToString("")
}
