package shkonda.artschools.data.data_source.user.entity

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("username") val username: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("profilePictureUrl") val image: String,
    @SerializedName("artCategory") val artCategory: Long,
    @SerializedName("points") val points: String,
    @SerializedName("correctAnswers") val correctAnswers: String
)
