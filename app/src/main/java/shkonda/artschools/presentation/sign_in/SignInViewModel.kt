package shkonda.artschools.presentation.sign_in

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shkonda.artschools.core.common.Response
import shkonda.artschools.core.common.storeToken
import shkonda.artschools.core.navigation.NavScreen
import shkonda.artschools.core.navigation.Navigator
import shkonda.artschools.domain.model.auth.Login
import shkonda.artschools.domain.usecase.auth.SignInUseCase
import shkonda.artschools.presentation.utils.Messages
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
//    private val sendPasswordResetMailUseCase: SendPasswordResetMailUseCase,
    private val sharedPreferences: SharedPreferences

) : ViewModel(){
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Nothing)
    val signInState = _signInState.asStateFlow()

    private val _signInInputFieldState = MutableStateFlow<SignInInputFieldState>(SignInInputFieldState.Nothing)
    val signInInputFieldState = _signInInputFieldState.asStateFlow()

    /*private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Nothing)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()*/

    var showForgotPasswordScreen by mutableStateOf(false)
        private set
    var forgotPasswordEmail by mutableStateOf("")
        private set
    var username by mutableStateOf("test0")
        private set
    var email by mutableStateOf("test0@gmail.com")
        private set
    var password by mutableStateOf("123456")
        private set

    var usernameError by mutableStateOf(false)
        private set
    var emailError by mutableStateOf(false)
        private set
    var passwordError by mutableStateOf(false)
        private set
    /*fun forgotPassword() = viewModelScope.launch(Dispatchers.IO) {
        sendPasswordResetMailUseCase(sendPasswordResetMail = SendPasswordResetMail(email = forgotPasswordEmail)).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _forgotPasswordState.value = ForgotPasswordState.Loading
                }
                is Response.Success -> {
                    _forgotPasswordState.value = ForgotPasswordState.Success
                }
                is Response.Error -> {
                    _forgotPasswordState.value = ForgotPasswordState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }*/

    fun signIn() = viewModelScope.launch(Dispatchers.IO) {
        if (checkInputFields()) {
            signInUseCase(login = Login(username = username, email = email, password = password)).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _signInState.value = SignInState.Loading
                    }
                    is Response.Success -> {
                        _signInState.value = SignInState.Success(data = response.data)

                        with(sharedPreferences.edit()) {
//                            storeToken(token = response.data.token.accessToken)
                            storeToken(token = response.data.token)
                        }

                        Navigator.navigate(NavScreen.HomeScreen.route) {}
                    }
                    is Response.Error -> {
                        _signInState.value = SignInState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    fun updateUsernameField(newValue: String) { username = newValue }
    fun updateEmailField(newValue: String) { email = newValue }

    fun updatePasswordField(newValue: String) { password = newValue }

    fun updateForgotPasswordField(newValue: String) { forgotPasswordEmail = newValue }

    fun resetSignInputState() { _signInInputFieldState.value = SignInInputFieldState.Nothing }

    fun resetSignInState() { _signInState.value = SignInState.Nothing }

//    fun resetForgotPasswordState() { _forgotPasswordState.value = ForgotPasswordState.Nothing }

    fun resetShowForgotPasScr() { showForgotPasswordScreen = false }

    fun openForgotPasswordScreen() { showForgotPasswordScreen = true }

    private fun checkInputFields(): Boolean =
        if (username.isBlank() && email.isBlank() && password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            usernameError = true
            emailError = true
            passwordError = true
            false
        } else if (username.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            usernameError = true
            emailError = false
            passwordError = false
            false
        } else if (email.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            usernameError = false
            emailError = true
            passwordError = false
            false
        } else if (password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            usernameError = false
            passwordError = true
            emailError = false
            false
        } else {
            usernameError = false
            emailError = false
            passwordError = false
            true
        }

    fun navigateSignUpScreen() {
        Navigator.navigate(NavScreen.SignUpScreen.route) {}
    }

    fun resetShowForPas() { showForgotPasswordScreen = false }
}