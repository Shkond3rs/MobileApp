package shkonda.artschools.core.common

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun encodeForSafe(string: String): String =
    URLEncoder.encode(string, StandardCharsets.UTF_8.toString())