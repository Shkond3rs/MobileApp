package shkonda.artschools.core.common

import android.content.Context
import coil.request.ImageRequest
import shkonda.artschools.R

fun loadImage(
    context: Context,
    imageUrl: String,
    placeHolderAndErrorImage: Int = R.drawable.no_image
): ImageRequest {
    return ImageRequest.Builder(context = context)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeHolderAndErrorImage)
        .error(placeHolderAndErrorImage)
        .build()
}
