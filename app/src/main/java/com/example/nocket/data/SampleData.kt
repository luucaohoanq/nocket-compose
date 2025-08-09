package com.example.nocket.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import com.example.nocket.models.Friendship
import com.example.nocket.models.FriendshipStatus
import com.example.nocket.models.Message
import com.example.nocket.models.Notification
import com.example.nocket.models.NotificationType
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType
import com.example.nocket.models.User
import java.time.LocalDateTime

/**
 * Sample data for the Nocket app
 * Contains mock data for users, messages, posts, settings, notifications, and friendships
 */
object SampleData {

    val imageNotAvailable = "https://images.unsplash.com/photo-1610513320995-1ad4bbf25e55?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

    // Enhanced user sample data with diverse usernames
    val users = listOf(
        User(username = "hclw"),
        User(username = "jane_smith"),
        User(username = "alex_chen"),
        User(username = "maria_garcia"),
        User(username = "david_kim"),
        User(username = "sarah_wilson"),
        User(username = "mike_jones"),
        User(username = "emma_brown"),
        User(username = "lucas_taylor"),
        User(username = "sophia_lee"),
        User(username = "ahmed_hassan"),
        User(username = "priya_patel"),
        User(username = "james_wright"),
        User(username = "nina_rodriguez"),
        User(username = "kai_tanaka")
    )

    // Sample messages with engaging content
    @RequiresApi(Build.VERSION_CODES.O)
    val messages = listOf(
        Message(
            senderId = users[0].id,
            recipientId = users[14].id,
            previewContent = "Hey! How are you doing today? 😊",
            timeSent = LocalDateTime.now().minusMinutes(5).toString(),
            isRead = true
        ),
        Message(
            senderId = users[1].id,
            recipientId = users[14].id,
            previewContent = "Are you coming to the party this weekend? It's going to be amazing! 🎉",
            timeSent = LocalDateTime.now().minusMinutes(30).toString(),
            isRead = true
        ),
        Message(
            senderId = users[2].id,
            recipientId = users[14].id,
            previewContent = "Let's catch up soon! I have so much to tell you about my new job 💼",
            timeSent = LocalDateTime.now().minusHours(2).toString(),
            isRead = false
        ),
        Message(
            senderId = users[3].id,
            recipientId = users[14].id,
            previewContent = "Thanks for the help with the project! You're a lifesaver 🙏",
            timeSent = LocalDateTime.now().minusHours(5).toString(),
            isRead = true
        ),
        Message(
            senderId = users[4].id,
            recipientId = users[14].id,
            previewContent = "Did you see the new movie that came out? We should watch it together! 🍿",
            timeSent = LocalDateTime.now().minusDays(1).toString(),
            isRead = false
        ),
        Message(
            senderId = users[5].id,
            recipientId = users[14].id,
            previewContent = "The weather is amazing today! Perfect for a walk in the park ☀️",
            timeSent = LocalDateTime.now().minusDays(2).toString(),
            isRead = true
        ),
        Message(
            senderId = users[6].id,
            recipientId = users[14].id,
            previewContent = "Happy birthday! Hope you have a wonderful day 🎂🎈",
            timeSent = LocalDateTime.now().minusDays(3).toString(),
            isRead = false
        ),
        Message(
            senderId = users[7].id,
            recipientId = users[14].id,
            previewContent = "Just finished my workout. Feeling great! 💪 Want to join me next time?",
            timeSent = LocalDateTime.now().minusDays(4).toString(),
            isRead = true
        ),
        Message(
            senderId = users[8].id,
            recipientId = users[14].id,
            previewContent = "Check out this cool article I found about space exploration 🚀",
            timeSent = LocalDateTime.now().minusDays(5).toString(),
            isRead = false
        )
    )

    // Sample posts with rich content
    @RequiresApi(Build.VERSION_CODES.O)
    val samplePosts = listOf(
        Post(
            user = users[0],
            postType = PostType.IMAGE,
            caption = "Beautiful sunset at the beach! 🌅 Nothing beats the golden hour vibes. #sunset #beach #nature #photography",
            thumbnailUrl = "https://picsum.photos/400/300?random=1",
            createdAt = LocalDateTime.now().minusDays(2).toString()
        ),
        Post(
            user = users[1],
            postType = PostType.TEXT,
            caption = "Just finished reading an amazing book about space exploration! 🚀 The universe is truly fascinating and there's so much we still don't know. What's your favorite science book? ⭐️📚 #reading #science #space",
            thumbnailUrl = "https://picsum.photos/400/300?random=101",
            createdAt = LocalDateTime.now().minusHours(5).toString()
        ),
        Post(
            user = users[2],
            postType = PostType.VIDEO,
            caption = "Check out this cool time-lapse of my art creation process! 🎨 Spent 5 hours on this piece and I'm really happy with how it turned out. Art is my passion! #art #timelapse #creative #painting",
            thumbnailUrl = "https://picsum.photos/400/300?random=2"
        ),
        Post(
            user = users[3],
            postType = PostType.IMAGE,
            caption = "Morning coffee and coding session ☕️💻 Starting the day right with some fresh code and caffeine. Working on a new mobile app! #developer #coffee #coding #morning #productivity",
            thumbnailUrl = "https://picsum.photos/400/300?random=3"
        ),
        Post(
            user = users[4],
            postType = PostType.TEXT,
            caption = "Grateful for all the wonderful people in my life! 💕 It's amazing how much brighter life becomes when you're surrounded by supportive friends and family. Thank you for being amazing! #gratitude #friendship #blessed #love",
            thumbnailUrl = "https://picsum.photos/400/300?random=102"
        ),
        Post(
            user = users[5],
            postType = PostType.IMAGE,
            caption = "Hiking adventure in the mountains! 🏔️ The view from the top was absolutely breathtaking. 10 miles round trip but totally worth every step! Nature is the best therapy. #hiking #mountains #adventure #nature #fitness",
            thumbnailUrl = "https://picsum.photos/400/300?random=4"
        ),
        Post(
            user = users[6],
            postType = PostType.VIDEO,
            caption = "Cooking experiment: homemade pasta from scratch! 🍝 First time making fresh pasta and it turned out better than expected. The secret is patience and good olive oil! #cooking #pasta #homemade #foodie",
            thumbnailUrl = "https://picsum.photos/400/300?random=5"
        ),
        Post(
            user = users[7],
            postType = PostType.IMAGE,
            caption = "New city, new adventures! 🏙️ Just moved here and already falling in love with the architecture and culture. Can't wait to explore more hidden gems! #travel #city #architecture #newbeginnings",
            thumbnailUrl = "https://picsum.photos/400/300?random=6"
        ),
        Post(
            user = users[8],
            postType = PostType.TEXT,
            caption = "Meditation has been a game-changer for my mental health! 🧘‍♀️ Started with just 5 minutes a day and now I can't imagine my routine without it. Peace and mindfulness are so important in our busy world. #meditation #mindfulness #mentalhealth #wellness #peace",
            thumbnailUrl = "https://picsum.photos/400/300?random=103"
        ),
        Post(
            user = users[9],
            postType = PostType.IMAGE,
            caption = "Late night gaming session with the crew! 🎮 Nothing beats some quality time with friends, even if it's virtual. We conquered another dungeon tonight! #gaming #friends #latenight #fun #teamwork",
            thumbnailUrl = "https://picsum.photos/400/300?random=7"
        ),
        Post(
            user = users[10],
            postType = PostType.VIDEO,
            caption = "Learning to play guitar! 🎸 It's never too late to pick up a new hobby. This song took me weeks to master but it was worth it! #music #guitar #learning #hobby #practice",
            thumbnailUrl = "https://picsum.photos/400/300?random=8"
        ),
        Post(
            user = users[11],
            postType = PostType.IMAGE,
            caption = "Homemade bread from scratch! 🍞 There's something magical about the smell of fresh bread in the morning. Recipe in comments! #baking #homemade #bread #cooking #yummy",
            thumbnailUrl = "https://picsum.photos/400/300?random=9"
        ),
        Post(
            user = users[12],
            postType = PostType.TEXT,
            caption = "Volunteering at the local shelter today. 🐶🐱 Every little bit helps and these animals deserve all the love they can get. Adopt, don't shop! #volunteering #animals #shelter #adoptdontshop",
            thumbnailUrl = "https://picsum.photos/400/300?random=104"
        ),
        Post(
            user = users[13],
            postType = PostType.VIDEO,
            caption = "Exploring the underwater world while scuba diving! 🌊🐠 The colors and life down there are absolutely mesmerizing. Can't wait to go back! #scubadiving #ocean #adventure #nature",
            thumbnailUrl = "https://picsum.photos/400/300?random=10"
        ),
        Post(
            user = users[14],
            postType = PostType.IMAGE,
            caption = "Weekend getaway to the countryside! 🌾🏡 Fresh air, beautiful landscapes, and a break from the city hustle. Perfect way to recharge! #getaway #countryside #nature #relaxation",
            thumbnailUrl = "https://picsum.photos/400/300?random=11",
            createdAt = LocalDateTime.now().minusMinutes(30).toString()
        ),
        // Add a very recent post from the current user (user 14)
        Post(
            user = users[14],
            postType = PostType.TEXT,
            caption = "Just posted this a few minutes ago! Testing the sorting functionality. #testing #newest",
            thumbnailUrl = "https://picsum.photos/400/300?random=12",
            createdAt = LocalDateTime.now().toString()
        )
    )

    // Comprehensive settings list
    val settingList = listOf<Setting>(
        Setting(
            type = SettingType.WIDGET,
            title = "Widget Settings",
            description = "Customize your home screen widget"
        ),

        Setting(
            type = SettingType.CUSTOMIZE,
            title = "App Icon",
            icon = "ICON_APP",
            description = "Choose from 12 beautiful app icons"
        ),
        Setting(
            type = SettingType.CUSTOMIZE,
            title = "Theme",
            icon = "ICON_THEME",
            description = "Switch between light, dark, or auto mode"
        ),
        Setting(
            type = SettingType.CUSTOMIZE,
            title = "Streak on widget",
            icon = "ICON_COLOR",
            isToggleable = true,
            isToggled = true
        ),

        Setting(
            type = SettingType.GENERAL,
            title = "Edit Name",
            description = "Change your display name"
        ),
        Setting(
            type = SettingType.GENERAL,
            title = "Edit Birthday",
            description = "Set or update your birth date"
        ),
        Setting(
            type = SettingType.GENERAL,
            title = "Change Phone Number",
            description = "Update your contact number"
        ),
        Setting(
            type = SettingType.GENERAL,
            title = "How to Add Widget",
            description = "Step-by-step widget setup guide"
        ),
        Setting(
            type = SettingType.GENERAL,
            title = "Restore Purchases",
            description = "Restore previous premium features"
        ),

        Setting(
            type = SettingType.PRIVACY_SAFETY,
            title = "Blocked Accounts",
            description = "View and manage blocked users"
        ),
        Setting(
            type = SettingType.PRIVACY_SAFETY,
            title = "Account Visibility",
            description = "Control who can find your profile"
        ),
        Setting(
            type = SettingType.PRIVACY_SAFETY,
            title = "Privacy Choices",
            description = "Manage data sharing preferences"
        ),

        Setting(
            type = SettingType.SUPPORT,
            title = "Report a Problem",
            description = "Get help with technical issues"
        ),
        Setting(
            type = SettingType.SUPPORT,
            title = "Make a Suggestion",
            description = "Share ideas for new features"
        ),

        Setting(
            type = SettingType.ABOUT,
            title = "TikTok",
            description = "@nocketapp - Latest updates & tips"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Instagram",
            description = "@nocketapp - Behind the scenes"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Twitter",
            description = "@nocketapp - News & announcements"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Share Nocket",
            description = "Invite friends to join Nocket"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Rate Nocket",
            description = "Leave a review on your app store"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Terms of Service",
            description = "Read our terms and conditions"
        ),
        Setting(
            type = SettingType.ABOUT,
            title = "Privacy Policy",
            description = "Understand how we protect your data"
        ),

        Setting(
            type = SettingType.DANGER_ZONE,
            title = "Delete Account",
            description = "Permanently delete your account and all data"
        ),
        Setting(
            type = SettingType.DANGER_ZONE,
            title = "Sign Out",
            description = "Sign out from all devices"
        ),
    )

    // Sample notifications data
    @RequiresApi(Build.VERSION_CODES.O)
    val notifications = listOf(
        Notification(
            type = NotificationType.LIKE,
            title = "New Like",
            description = "${users[1].username} liked your post",
            time = LocalDateTime.now().minusMinutes(5).toString(),
            isRead = false,
            icon = Icons.Default.Favorite,
            iconColor = Color(0xFFE91E63),
            userId = users[1].id
        ),
        Notification(
            type = NotificationType.COMMENT,
            title = "New Comment",
            description = "${users[2].username} commented on your post: 'This is amazing!'",
            time = LocalDateTime.now().minusHours(1).toString(),
            isRead = false,
            icon = Icons.AutoMirrored.Filled.Message,
            iconColor = Color(0xFF2196F3),
            userId = users[2].id
        ),
        Notification(
            type = NotificationType.FOLLOW,
            title = "New Follower",
            description = "${users[3].username} started following you",
            time = LocalDateTime.now().minusHours(3).toString(),
            isRead = true,
            icon = Icons.Default.Person,
            iconColor = Color(0xFF4CAF50),
            userId = users[3].id
        ),
        Notification(
            type = NotificationType.FRIEND_REQUEST,
            title = "Friend Request",
            description = "${users[4].username} sent you a friend request",
            time = LocalDateTime.now().minusHours(5).toString(),
            isRead = false,
            icon = Icons.Default.Person,
            iconColor = Color(0xFF9C27B0),
            userId = users[4].id
        ),
        Notification(
            type = NotificationType.MESSAGE,
            title = "New Message",
            description = "${users[5].username} sent you a message",
            time = LocalDateTime.now().minusHours(8).toString(),
            isRead = true,
            icon = Icons.AutoMirrored.Filled.Message,
            iconColor = Color(0xFF009688),
            userId = users[5].id
        ),
        Notification(
            type = NotificationType.SYSTEM_ALERT,
            title = "System Update",
            description = "Nocket has been updated to version 1.0.1",
            time = LocalDateTime.now().minusDays(1).toString(),
            isRead = false,
            icon = Icons.Default.Notifications,
            iconColor = Color(0xFFFF9800),
            userId = ""
        ),
        Notification(
            type = NotificationType.SYSTEM_ALERT,
            title = "Security Alert",
            description = "Your account was accessed from a new device",
            time = LocalDateTime.now().minusDays(2).toString(),
            isRead = true,
            icon = Icons.Default.Warning,
            iconColor = Color(0xFFF44336),
            userId = ""
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val messageList = listOf(
        Message(
            senderId = users[0].id,
            recipientId = users[9].id,
            previewContent = "Hey! How are you doing today? 😊",
            timeSent = LocalDateTime.now().minusMinutes(5).toString()
        ),
        Message(
            senderId = users[1].id,
            recipientId = users[9].id,
            previewContent = "Are you coming to the party this weekend? It's going to be amazing! 🎉",
            timeSent = LocalDateTime.now().minusMinutes(30).toString()
        ),
        Message(
            senderId = users[2].id,
            recipientId = users[9].id,
            previewContent = "Let's catch up soon! I have so much to tell you about my new job 💼",
            timeSent = LocalDateTime.now().minusHours(2).toString()
        ),
        Message(
            senderId = users[3].id,
            recipientId = users[9].id,
            previewContent = "Thanks for the help with the project! You're a lifesaver 🙏",
            timeSent = LocalDateTime.now().minusHours(5).toString()
        ),
        Message(
            senderId = users[4].id,
            recipientId = users[9].id,
            previewContent = "Did you see the new movie that came out? We should watch it together! 🍿",
            timeSent = LocalDateTime.now().minusDays(1).toString()
        ),
        Message(
            senderId = users[5].id,
            recipientId = users[9].id,
            previewContent = "The weather is amazing today! Perfect for a walk in the park ☀️",
            timeSent = LocalDateTime.now().minusDays(2).toString()
        ),
        Message(
            senderId = users[6].id,
            recipientId = users[9].id,
            previewContent = "Happy birthday! Hope you have a wonderful day 🎂🎈",
            timeSent = LocalDateTime.now().minusDays(3).toString()
        ),
        Message(
            senderId = users[7].id,
            recipientId = users[9].id,
            previewContent = "Just finished my workout. Feeling great! 💪 Want to join me next time?",
            timeSent = LocalDateTime.now().minusDays(4).toString()
        ),
        Message(
            senderId = users[8].id,
            recipientId = users[9].id,
            previewContent = "Check out this cool article I found about space exploration 🚀",
            timeSent = LocalDateTime.now().minusDays(5).toString()
        )
    )

    // Sample friendships data
    val friendships = listOf(
        Friendship(
            user1Id = users[0].id,
            user2Id = users[1].id,
            status = FriendshipStatus.ACCEPTED,
            requesterId = users[0].id,
            addresseeId = users[1].id
        ),
        Friendship(
            user1Id = users[0].id,
            user2Id = users[2].id,
            status = FriendshipStatus.ACCEPTED,
            requesterId = users[0].id,
            addresseeId = users[2].id
        ),
        Friendship(
            user1Id = users[3].id,
            user2Id = users[0].id,
            status = FriendshipStatus.ACCEPTED,
            requesterId = users[3].id,
            addresseeId = users[0].id
        ),
        Friendship(
            user1Id = users[4].id,
            user2Id = users[0].id,
            status = FriendshipStatus.PENDING,
            requesterId = users[4].id,
            addresseeId = users[0].id
        ),
        Friendship(
            user1Id = users[0].id,
            user2Id = users[5].id,
            status = FriendshipStatus.PENDING,
            requesterId = users[0].id,
            addresseeId = users[5].id
        ),
        Friendship(
            user1Id = users[6].id,
            user2Id = users[0].id,
            status = FriendshipStatus.DECLINED,
            requesterId = users[6].id,
            addresseeId = users[0].id
        ),
        Friendship(
            user1Id = users[0].id,
            user2Id = users[7].id,
            status = FriendshipStatus.BLOCKED,
            requesterId = users[0].id,
            addresseeId = users[7].id
        ),
        // Add friendships for user 14 (our current user in HomeScreen)
        Friendship(
            user1Id = users[14].id,
            user2Id = users[1].id,
            status = FriendshipStatus.ACCEPTED
        ),
        Friendship(
            user1Id = users[14].id,
            user2Id = users[2].id,
            status = FriendshipStatus.ACCEPTED
        ),
        Friendship(
            user1Id = users[14].id,
            user2Id = users[3].id,
            status = FriendshipStatus.ACCEPTED
        )
    )

    // Fun statistics for the app
    @RequiresApi(Build.VERSION_CODES.O)
    val stats = mapOf(
        "totalUsers" to users.size,
        "totalMessages" to messages.size,
        "totalPosts" to samplePosts.size,
        "totalSettings" to settingList.size,
        "totalFriendships" to friendships.size,
        "totalNotifications" to notifications.size,
        "appName" to "Nocket",
        "version" to "1.0.0",
        "lastUpdated" to "August 2025"
    )
}
