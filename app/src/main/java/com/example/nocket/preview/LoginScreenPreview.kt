package com.example.nocket.preview

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nocket.models.auth.AuthState
import com.example.nocket.ui.screen.auth.AuthMode
import com.example.nocket.ui.screen.auth.AuthUIActions
import com.example.nocket.ui.screen.auth.AuthUIState
import com.example.nocket.ui.screen.auth.LoginScreenContent
import com.example.nocket.ui.theme.AppTheme

// Preview Functions
@Preview(name = "Login Mode")
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreenContent(
            uiState = AuthUIState(
                authState = AuthState.Initial,
                isLoading = false,
                authMode = AuthMode.LOGIN,
                email = "user@example.com",
                password = "",
                passwordVisible = false
            ),
            actions = object : AuthUIActions {
                override fun onLogin(email: String, password: String) {}
                override fun onRegister(email: String, password: String, name: String) {}
                override fun onResetPassword(email: String) {}
                override fun onGoogleLogin(activity: ComponentActivity) {}
                override fun onLoginSuccess() {}
                override fun clearError() {}
            }
        )
    }
}

@Preview(name = "Register Mode")
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        LoginScreenContent(
            uiState = AuthUIState(
                authState = AuthState.Initial,
                isLoading = false,
                authMode = AuthMode.REGISTER,
                email = "user@example.com",
                password = "password123",
                name = "John Doe",
                passwordVisible = false
            ),
            actions = object : AuthUIActions {
                override fun onLogin(email: String, password: String) {}
                override fun onRegister(email: String, password: String, name: String) {}
                override fun onResetPassword(email: String) {}
                override fun onGoogleLogin(activity: ComponentActivity) {}
                override fun onLoginSuccess() {}
                override fun clearError() {}
            }
        )
    }
}

@Preview(name = "Loading State")
@Composable
private fun LoadingScreenPreview() {
    AppTheme {
        LoginScreenContent(
            uiState = AuthUIState(
                authState = AuthState.Loading,
                isLoading = true,
                authMode = AuthMode.LOGIN,
                email = "user@example.com",
                password = "password123",
                passwordVisible = false
            ),
            actions = object : AuthUIActions {
                override fun onLogin(email: String, password: String) {}
                override fun onRegister(email: String, password: String, name: String) {}
                override fun onResetPassword(email: String) {}
                override fun onGoogleLogin(activity: ComponentActivity) {}
                override fun onLoginSuccess() {}
                override fun clearError() {}
            }
        )
    }
}

@Preview(name = "Error State")
@Composable
private fun ErrorScreenPreview() {
    AppTheme {
        LoginScreenContent(
            uiState = AuthUIState(
                authState = AuthState.Error("Invalid credentials. Please try again."),
                isLoading = false,
                authMode = AuthMode.LOGIN,
                email = "user@example.com",
                password = "wrongpassword",
                passwordVisible = false
            ),
            actions = object : AuthUIActions {
                override fun onLogin(email: String, password: String) {}
                override fun onRegister(email: String, password: String, name: String) {}
                override fun onResetPassword(email: String) {}
                override fun onGoogleLogin(activity: ComponentActivity) {}
                override fun onLoginSuccess() {}
                override fun clearError() {}
            }
        )
    }
}

@Preview(name = "Forgot Password Mode")
@Composable
private fun ForgotPasswordScreenPreview() {
    AppTheme {
        LoginScreenContent(
            uiState = AuthUIState(
                authState = AuthState.Initial,
                isLoading = false,
                authMode = AuthMode.FORGOT_PASSWORD,
                email = "user@example.com",
                passwordVisible = false
            ),
            actions = object : AuthUIActions {
                override fun onLogin(email: String, password: String) {}
                override fun onRegister(email: String, password: String, name: String) {}
                override fun onResetPassword(email: String) {}
                override fun onGoogleLogin(activity: ComponentActivity) {}
                override fun onLoginSuccess() {}
                override fun clearError() {}
            }
        )
    }
}