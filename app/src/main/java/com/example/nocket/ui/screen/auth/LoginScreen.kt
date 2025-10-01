/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.ui.screen.auth

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.nocket.R
import com.example.nocket.models.auth.AuthState
import com.example.nocket.viewmodels.AuthViewModel

enum class AuthMode {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
}

val cornerShape: Dp = 12.dp

// Data class to hold the UI state
data class AuthUIState(
    val authState: AuthState = AuthState.Initial,
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val authMode: AuthMode = AuthMode.LOGIN,
    val passwordVisible: Boolean = false,
)

// Callback interface for handling UI actions
interface AuthUIActions {
    fun onLogin(email: String, password: String)
    fun onRegister(email: String, password: String, name: String)
    fun onResetPassword(email: String)
    fun onGoogleLogin(activity: ComponentActivity)
    fun onLoginSuccess()
    fun clearError()
}

// Main LoginScreen composable with ViewModel
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val authState by viewModel.authState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val uiState = AuthUIState(
        authState = authState,
        isLoading = isLoading,
    )

    val actions = object : AuthUIActions {
        override fun onLogin(email: String, password: String) {
            viewModel.login(email, password)
        }

        override fun onRegister(email: String, password: String, name: String) {
            viewModel.register(email, password, name)
        }

        override fun onResetPassword(email: String) {
            viewModel.resetPassword(email)
        }

        override fun onGoogleLogin(activity: ComponentActivity) {
            viewModel.loginWithGoogle(activity)
        }

        override fun onLoginSuccess() {
            onLoginSuccess()
        }

        override fun clearError() {
            viewModel.clearError()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        actions = actions,
        modifier = modifier,
    )
}

// Pure UI composable without dependencies
@Composable
fun LoginScreenContent(
    uiState: AuthUIState,
    actions: AuthUIActions,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    var authMode by rememberSaveable { mutableStateOf(uiState.authMode) }
    var email by rememberSaveable { mutableStateOf(uiState.email) }
    var password by rememberSaveable { mutableStateOf(uiState.password) }
    var name by rememberSaveable { mutableStateOf(uiState.name) }
    var passwordVisible by rememberSaveable { mutableStateOf(uiState.passwordVisible) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Handle auth state changes
    LaunchedEffect(uiState.authState) {
        when (uiState.authState) {
            is AuthState.Authenticated -> {
                actions.onLoginSuccess()
            }

            is AuthState.Error -> {
                snackbarHostState.showSnackbar(
                    message = (uiState.authState as AuthState.Error).message,
                    actionLabel = "Dismiss",
                )
                actions.clearError()
            }

            is AuthState.PasswordResetSent -> {
                snackbarHostState.showSnackbar(
                    message = "Password reset link sent to your email",
                    actionLabel = "OK",
                )
                authMode = AuthMode.LOGIN
            }

            else -> {
                /* No action needed */
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background,
                        ),
                    ),
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // App Logo/Branding
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(60.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = R.mipmap.ic_launcher,
                            contentDescription = "Nocket Logo",
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App Title
                Text(
                    text = "Welcome to Nocket",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Share moments with your friends",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Auth Card with different content based on authMode
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Title changes based on auth mode
                        Text(
                            text = when (authMode) {
                                AuthMode.LOGIN -> "Sign in to continue"
                                AuthMode.REGISTER -> "Create your account"
                                AuthMode.FORGOT_PASSWORD -> "Reset password"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Email Field - common for all modes
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Icon",
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = if (authMode == AuthMode.FORGOT_PASSWORD) ImeAction.Done else ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (authMode == AuthMode.REGISTER) {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    } else {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                },
                                onDone = {
                                    if (authMode == AuthMode.FORGOT_PASSWORD) {
                                        actions.onResetPassword(email)
                                        focusManager.clearFocus()
                                    }
                                },
                            ),
                            shape = RoundedCornerShape(cornerShape),
                            singleLine = true,
                        )

                        // Name field - only for register
                        if (authMode == AuthMode.REGISTER) {
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Full Name") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Name Icon",
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                ),
                                shape = RoundedCornerShape(cornerShape),
                                singleLine = true,
                            )
                        }

                        // Password field - not for forgot password
                        if (authMode != AuthMode.FORGOT_PASSWORD) {
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Password") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Password Icon",
                                    )
                                },
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                        )
                                    }
                                },
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        when (authMode) {
                                            AuthMode.LOGIN -> {
                                                actions.onLogin(email, password)
                                                focusManager.clearFocus()
                                            }

                                            AuthMode.REGISTER -> {
                                                actions.onRegister(email, password, name)
                                                focusManager.clearFocus()
                                            }

                                            else -> {
                                                /* Not used in forgot password */
                                            }
                                        }
                                    },
                                ),
                                shape = RoundedCornerShape(cornerShape),
                                singleLine = true,
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Primary Button - behavior changes based on mode
                        Button(
                            onClick = {
                                when (authMode) {
                                    AuthMode.LOGIN -> actions.onLogin(email, password)
                                    AuthMode.REGISTER -> actions.onRegister(email, password, name)
                                    AuthMode.FORGOT_PASSWORD -> actions.onResetPassword(email)
                                }
                                focusManager.clearFocus()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !uiState.isLoading &&
                                uiState.authState !is AuthState.Loading &&
                                when (authMode) {
                                    AuthMode.LOGIN -> email.isNotBlank() && password.isNotBlank()
                                    AuthMode.REGISTER -> email.isNotBlank() && password.isNotBlank() && name.isNotBlank()
                                    AuthMode.FORGOT_PASSWORD -> email.isNotBlank()
                                },
                            shape = RoundedCornerShape(cornerShape),
                        ) {
                            if (uiState.isLoading || uiState.authState is AuthState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            } else {
                                Text(
                                    text = when (authMode) {
                                        AuthMode.LOGIN -> "Sign In"
                                        AuthMode.REGISTER -> "Create Account"
                                        AuthMode.FORGOT_PASSWORD -> "Reset Password"
                                    },
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }

                        // Helper links - different based on mode
                        Spacer(modifier = Modifier.height(16.dp))

                        when (authMode) {
                            AuthMode.LOGIN -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    TextButton(onClick = { authMode = AuthMode.FORGOT_PASSWORD }) {
                                        Text("Forgot Password?")
                                    }

                                    TextButton(onClick = { authMode = AuthMode.REGISTER }) {
                                        Text("Sign Up", color = Color(0xFFFFD700))
                                    }
                                }
                            }

                            AuthMode.REGISTER -> {
                                TextButton(onClick = { authMode = AuthMode.LOGIN }) {
                                    Text("Already have an account? Sign In")
                                }
                            }

                            AuthMode.FORGOT_PASSWORD -> {
                                TextButton(onClick = { authMode = AuthMode.LOGIN }) {
                                    Text("Back to Login")
                                }
                            }
                        }

                        if (authMode != AuthMode.FORGOT_PASSWORD) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier.weight(1f),
                                    thickness = DividerDefaults.Thickness,
                                    color = DividerDefaults.color,
                                )
                                Text(
                                    text = "OR",
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                HorizontalDivider(
                                    modifier = Modifier.weight(1f),
                                    thickness = DividerDefaults.Thickness,
                                    color = DividerDefaults.color,
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Google Login Button
                            Button(
                                onClick = {
                                    activity?.let { actions.onGoogleLogin(it) }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                enabled = !uiState.isLoading && uiState.authState !is AuthState.Loading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black,
                                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 2.dp,
                                    pressedElevation = 8.dp,
                                ),
                                shape = RoundedCornerShape(cornerShape),
                            ) {
                                if (uiState.isLoading || uiState.authState is AuthState.Loading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        // Google Icon
                                        Icon(
                                            painter = painterResource(id = R.drawable.google),
                                            contentDescription = "Google",
                                            modifier = Modifier.size(20.dp),
                                            tint = Color.Unspecified,
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Continue with Google",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    }
                                }
                            }
                        }

                        // Error state display
                        if (uiState.authState is AuthState.Error) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = "Error",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = (uiState.authState as AuthState.Error).message,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Privacy Notice
                Text(
                    text = "By continuing, you agree to our Terms of Service and Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
            }
        }
    }
}
