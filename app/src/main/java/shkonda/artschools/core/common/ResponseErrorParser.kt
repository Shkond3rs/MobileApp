package shkonda.artschools.core.common

import org.json.JSONObject
import retrofit2.HttpException

fun HttpException.getErrorMessage(): String? {
    return try {
        response()?.errorBody()?.source()?.buffer?.snapshot()?.utf8()?.let {
            JSONObject(it).getString("error")
        }
    } catch (e: Exception) {
        null
    }
}