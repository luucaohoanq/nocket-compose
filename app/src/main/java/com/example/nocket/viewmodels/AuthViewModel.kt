package com.example.nocket.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nocket.models.auth.AuthState
import com.example.nocket.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing authentication state and operations
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        checkAuthStatus()
    }

    /**
     * Check current authentication status
     */
    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val user = authRepository.getCurrentUser()
                _authState.value = if (user != null) {
                    AuthState.Authenticated(user)
                } else {
                    AuthState.Unauthenticated
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    /**
     * Initiate Google OAuth login
     */
    fun loginWithGoogle(activity: androidx.activity.ComponentActivity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authState.value = AuthState.Loading
                authRepository.loginWithGoogle(activity)
                // After OAuth flow completes, check auth status
                checkAuthStatus()
            } catch (e: Exception) {
                _isLoading.value = false
                _authState.value = AuthState.Error("Failed to start login: ${e.message}")
            }
        }
    }

    /**
     * Handle OAuth callback from browser
     */
    fun handleOAuthCallback() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val user = authRepository.handleOAuthCallback()
                if (user != null) {
                    _authState.value = AuthState.Authenticated(user)
                } else {
                    _authState.value = AuthState.Error("Authentication failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Authentication failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Logout current user
     */
    fun logout() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val success = authRepository.logout()
                if (success) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Error("Failed to logout")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Logout failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Logout from all devices
     */
    fun logoutFromAllDevices() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val success = authRepository.logoutFromAllDevices()
                if (success) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Error("Failed to logout from all devices")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Logout failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Refresh authentication state
     */
    fun refreshAuth() {
        checkAuthStatus()
    }

    /**
     * Clear error state
     */
    fun clearError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Unauthenticated
        }
    }
}
