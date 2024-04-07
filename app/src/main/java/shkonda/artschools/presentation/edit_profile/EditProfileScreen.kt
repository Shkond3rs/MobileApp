package shkonda.artschools.presentation.edit_profile

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.quizapp.presentation.edit_profile.EditProfileViewModel
import shkonda.artschools.R
import shkonda.artschools.core.common.loadImage
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.core.ui.components.CustomLoadingSpinner
import shkonda.artschools.core.ui.components.CustomTopBarTitle
import shkonda.artschools.presentation.home.UserState
import shkonda.artschools.presentation.utils.EditProfileScreenPreferencesNames

private val SETTINGS_PROFILE_IMG_SIZE = 108.dp
private val PREFERENCES_HEIGHT = 56.dp

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
//    BackHandler {
//        Navigator.navigate(NavScreen.ProfileScreen.route)
//    }
    val userState by viewModel.userState.collectAsState()

    EditProfileScreenContent(
        modifier = modifier,
        userState = userState,
        viewModel = viewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun EditProfileScreenContent(
    modifier: Modifier,
    userState: UserState,
    viewModel: EditProfileViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        CustomTopBarTitle(
            modifier = modifier,
            title = "Edit Profile"
        )
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(userState) {
            is UserState.Nothing -> {}
            is UserState.Loading -> {
                Box(
                    modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    CustomLoadingSpinner()
                }
            }
            is UserState.Success -> {
                ProfileSection(
                    modifier = modifier,
                    userName = userState.data.userName,
                    userImage = userState.data.profilePictureUrl,
                    onEditProfileClick = {
                        Navigator.navigate(NavScreen.UpdateProfileScreen.route)
                    },
                    onPreferenceClick = {
                        viewModel.setPreferenceOnClick(it)
                    }
                )
            }
            is UserState.Error -> {
                TopBarError(
                    modifier = modifier,
                    errorMessage = userState.errorMessage
                )
            }
        }

    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier,
    userName: String,
    userImage: String,
    onEditProfileClick: () -> Unit,
    onPreferenceClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                modifier = modifier,
                userImage = userImage
            )
            UserRealNameAndUserName(
                modifier = modifier,
                userName = userName
            )
            EditProfileButton(modifier = modifier, onEditProfileClick = onEditProfileClick)
        }

        Preferences(modifier = modifier.weight(1f), onPreferenceClick = onPreferenceClick)
    }
}

@Composable
private fun UserRealNameAndUserName(
    modifier: Modifier,
    userName: String
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "@$userName",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun EditProfileButton(modifier: Modifier, onEditProfileClick: () -> Unit) {
    TextButton(
        modifier = modifier.padding(top = 16.dp),
        onClick = onEditProfileClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Blue)
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp),
            text = "Edit Profile >",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun Preferences(modifier: Modifier, onPreferenceClick: (String) -> Unit) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
            )
            .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        preferenceList.forEach {
            Preference(
                iconId = it.iconId,
                text = it.text,
                onPreferenceClick = { onPreferenceClick(it.text) },
                action = it.action
            )
        }
    }
}

@Composable
private fun Preference(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    onPreferenceClick: () -> Unit,
    action: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PREFERENCES_HEIGHT)
            .clickable(onClick = onPreferenceClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = iconId), contentDescription = null)
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = text,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        action()
    }
}


@Composable
private fun ProfileImage(modifier: Modifier, userImage: String) {
    AsyncImage(
        modifier = modifier
            .size(SETTINGS_PROFILE_IMG_SIZE)
            .clip(CircleShape)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant
                ), shape = CircleShape
            ),
        model = loadImage(context = LocalContext.current, imageUrl = userImage),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

data class PreferenceModel(
    val iconId: Int,
    val text: String,
    val onPreferenceClick: () -> Unit,
    val action: @Composable RowScope.() -> Unit
)

private val preferenceList = listOf(
    PreferenceModel(
        iconId = R.drawable.ic_baseline_language,
        text = EditProfileScreenPreferencesNames.LANGUAGE,
        onPreferenceClick = {}
    ) {
        Text(
            text = "English >",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_outline_dark_mode,
        text = EditProfileScreenPreferencesNames.DARK_MODE,
        onPreferenceClick = {}
    ) {
        Switch(checked = true, onCheckedChange = {})
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_info,
        text = EditProfileScreenPreferencesNames.INFORMATION,
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_password,
        text = EditProfileScreenPreferencesNames.CHANGE_PASSWORD,
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_headphones,
        text = EditProfileScreenPreferencesNames.CONTACT_US,
        onPreferenceClick = { }
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_delete_forever,
        text = EditProfileScreenPreferencesNames.DELETE_ACCOUNT,
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_logout,
        text = EditProfileScreenPreferencesNames.LOG_OUT,
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
)
@Composable
private fun TopBarError(modifier: Modifier, errorMessage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(48.dp),
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = "Ошибка отображения",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = errorMessage,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}