package com.example.nocket.ui.screen.post

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nocket.Screen
import com.example.nocket.components.bottombar.MainBottomBar
import com.example.nocket.components.bottombar.sampleItems2
import com.example.nocket.components.grid.PostGrid
import com.example.nocket.components.topbar.MainTopBar
import com.example.nocket.models.Post
import com.example.nocket.models.User
import com.example.nocket.models.auth.AuthState
import com.example.nocket.ui.screen.postdetail.PostDetailScreen
import com.example.nocket.utils.mapToUser
import com.example.nocket.viewmodels.AppwriteViewModel
import com.example.nocket.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    appwriteViewModel: AppwriteViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val posts by appwriteViewModel.posts.collectAsState()
    val friends by appwriteViewModel.friends.collectAsState()
    
    var selectedPost by remember { mutableStateOf<Post?>(null) }
    var showNotifications by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Track the selected user for filtering posts
    var selectedUser by remember {
        mutableStateOf<User?>(
            User(
                id = "everyone",
                username = "Everyone",
                avatar = ""
            )
        )
    }

    // Get current user from auth state
    val currentUser by appwriteViewModel.currentUser.collectAsState()
    val data = mapToUser(currentUser)

    // Fetch posts and friends when authenticated
    LaunchedEffect(authState) {
        when (authState){
            is AuthState.Authenticated -> {
                Log.d("UserDebug", "Auth State: Authenticated")
                isLoading = true
                val user = (authState as AuthState.Authenticated).user
                appwriteViewModel.getAllPostsOfUserAndFriends(user)
                appwriteViewModel.fetchFriendsOfUser(user)
                appwriteViewModel.fetchCurrentUser()
                isLoading = false
            } else -> Log.d("PostScreen", "User is not authenticated, skipping post fetch")
        }
    }

    when {
        selectedPost != null -> {
            PostDetailScreen(
                post = selectedPost!!,
                onBack = { selectedPost = null },
                navController = navController,
                friends = friends
            )
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {

                Scaffold(
                    topBar = {
                        MainTopBar(
                            navController = navController,
                            user = data,
                            friends = friends,
                            onMessageClick = { navController.navigate(Screen.Message.route) },
                            onProfileClick = { 
                                data?.id?.let { userId ->
                                    navController.navigate("profile?userId=$userId")
                                } ?: navController.navigate("profile")
                            },
                            onNotificationClick = { showNotifications = true },
                            onUserSelected = { user -> 
                                selectedUser = user
                                // Null safety: only proceed if user is not null
                                user?.let { nonNullUser ->
                                    // Fetch posts for selected user if it's not "everyone"
                                    if (nonNullUser.id != "everyone" && nonNullUser.id != "you" && authState is AuthState.Authenticated) {
                                        appwriteViewModel.getPostsForUser(nonNullUser.id)
                                    } else if (nonNullUser.id == "you") {
                                        // Fetch current user's posts
                                        appwriteViewModel.getPostsForUser(data.id)
                                    } else if (authState is AuthState.Authenticated) {
                                        // Fetch all posts
                                        val authUser = (authState as AuthState.Authenticated).user
                                        appwriteViewModel.getAllPostsOfUserAndFriends(authUser)
                                    }
                                } ?: run {
                                    // Handle case where user is null - default to showing all posts
                                    if (authState is AuthState.Authenticated) {
                                        val authUser = (authState as AuthState.Authenticated).user
                                        appwriteViewModel.getAllPostsOfUserAndFriends(authUser)
                                    }
                                }
                            }
                        )
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        // Post Grid as main content
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator()
                            } else if (posts.isEmpty()) {
                                // Show empty state
                                Text(
                                    text = "No posts yet.\nPosts from you and your friends will appear here.",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            } else {
                                // Filter posts based on selected user
                                val filteredPosts = when (selectedUser?.id) {
                                    "everyone" -> posts // Show all posts
                                    "you" -> posts.filter { it.user.id == currentUser?.id } // Show only current user's posts
                                    null -> posts // Show all posts if selectedUser is null
                                    else -> posts.filter { it.user.id == selectedUser!!.id } // Show selected user's posts
                                }

                                PostGrid(
                                    posts = filteredPosts,
                                    onPostClick = { post -> selectedPost = post },
                                )
                            }
                        }
                    }
                }

                MainBottomBar(navController, modifier = Modifier.align(Alignment.BottomCenter), items = sampleItems2)
            }
        }
    }
}