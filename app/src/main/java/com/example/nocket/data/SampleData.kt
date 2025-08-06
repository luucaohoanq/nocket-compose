package com.example.nocket.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nocket.models.Message
import com.example.nocket.models.Post
import com.example.nocket.models.PostType
import com.example.nocket.models.Setting
import com.example.nocket.models.SettingType
import com.example.nocket.models.User
import java.time.LocalDateTime

/**
 * Sample data for the Nocket app
 * Contains mock data for users, messages, posts, and settings
 */
object SampleData {

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
            sender = users[0],
            recipient = users[14],
            previewContent = "Hey! How are you doing today? 😊",
            timeSent = LocalDateTime.now().minusMinutes(5).toString()
        ),
        Message(
            sender = users[1],
            recipient = users[14],
            previewContent = "Are you coming to the party this weekend? It's going to be amazing! 🎉",
            timeSent = LocalDateTime.now().minusMinutes(30).toString()
        ),
        Message(
            sender = users[2],
            recipient = users[14],
            previewContent = "Let's catch up soon! I have so much to tell you about my new job 💼",
            timeSent = LocalDateTime.now().minusHours(2).toString()
        ),
        Message(
            sender = users[3],
            recipient = users[14],
            previewContent = "Thanks for the help with the project! You're a lifesaver 🙏",
            timeSent = LocalDateTime.now().minusHours(5).toString()
        ),
        Message(
            sender = users[4],
            recipient = users[14],
            previewContent = "Did you see the new movie that came out? We should watch it together! 🍿",
            timeSent = LocalDateTime.now().minusDays(1).toString()
        ),
        Message(
            sender = users[5],
            recipient = users[14],
            previewContent = "The weather is amazing today! Perfect for a walk in the park ☀️",
            timeSent = LocalDateTime.now().minusDays(2).toString()
        ),
        Message(
            sender = users[6],
            recipient = users[14],
            previewContent = "Happy birthday! Hope you have a wonderful day 🎂🎈",
            timeSent = LocalDateTime.now().minusDays(3).toString()
        ),
        Message(
            sender = users[7],
            recipient = users[14],
            previewContent = "Just finished my workout. Feeling great! 💪 Want to join me next time?",
            timeSent = LocalDateTime.now().minusDays(4).toString()
        ),
        Message(
            sender = users[8],
            recipient = users[14],
            previewContent = "Check out this cool article I found about space exploration 🚀",
            timeSent = LocalDateTime.now().minusDays(5).toString()
        )
    )

    // Sample posts with rich content
    val samplePosts = listOf(
        Post(
            user = users[0],
            postType = PostType.IMAGE,
            caption = "Beautiful sunset at the beach! 🌅 Nothing beats the golden hour vibes. #sunset #beach #nature #photography",
            thumbnailUrl = "https://picsum.photos/400/300?random=1"
        ),
        Post(
            user = users[1],
            postType = PostType.TEXT,
            caption = "Just finished reading an amazing book about space exploration! 🚀 The universe is truly fascinating and there's so much we still don't know. What's your favorite science book? ⭐️📚 #reading #science #space",
            thumbnailUrl = "https://picsum.photos/400/300?random=101"
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
            thumbnailUrl = "https://picsum.photos/400/300?random=11"
        )
    )

    // Comprehensive settings list
    val settings = listOf(
        Setting(type = SettingType.WIDGET, title = "Widget Settings", description = "Customize your home screen widget"),

        Setting(type = SettingType.CUSTOMIZE, title = "App Icon", icon = "ICON_APP", description = "Choose from 12 beautiful app icons"),
        Setting(type = SettingType.CUSTOMIZE, title = "Theme", icon = "ICON_THEME", description = "Switch between light, dark, or auto mode"),
        Setting(type = SettingType.CUSTOMIZE, title = "Color Scheme", icon = "ICON_COLOR", description = "Personalize with your favorite colors"),

        Setting(type = SettingType.GENERAL, title = "Edit Profile Picture", description = "Update your profile photo"),
        Setting(type = SettingType.GENERAL, title = "Edit Name", description = "Change your display name"),
        Setting(type = SettingType.GENERAL, title = "Edit Birthday", description = "Set or update your birth date"),
        Setting(type = SettingType.GENERAL, title = "Change Phone Number", description = "Update your contact number"),
        Setting(type = SettingType.GENERAL, title = "Change Email Address", description = "Update your email address"),
        Setting(type = SettingType.GENERAL, title = "Notification Settings", description = "Manage push notifications"),
        Setting(type = SettingType.GENERAL, title = "Language & Region", description = "Change app language"),
        Setting(type = SettingType.GENERAL, title = "Storage & Data", description = "Manage app storage usage"),
        Setting(type = SettingType.GENERAL, title = "How to Add Widget", description = "Step-by-step widget setup guide"),
        Setting(type = SettingType.GENERAL, title = "Restore Purchases", description = "Restore previous premium features"),

        Setting(type = SettingType.PRIVACY_SAFETY, title = "Blocked Accounts", description = "View and manage blocked users"),
        Setting(type = SettingType.PRIVACY_SAFETY, title = "Account Visibility", description = "Control who can find your profile"),
        Setting(type = SettingType.PRIVACY_SAFETY, title = "Two-Factor Authentication", description = "Add extra security to your account"),
        Setting(type = SettingType.PRIVACY_SAFETY, title = "Privacy Choices", description = "Manage data sharing preferences"),
        Setting(type = SettingType.PRIVACY_SAFETY, title = "Download Your Data", description = "Export your personal data"),

        Setting(type = SettingType.SUPPORT, title = "Report a Problem", description = "Get help with technical issues"),
        Setting(type = SettingType.SUPPORT, title = "Make a Suggestion", description = "Share ideas for new features"),
        Setting(type = SettingType.SUPPORT, title = "Contact Support", description = "Reach out to our support team"),
        Setting(type = SettingType.SUPPORT, title = "FAQ & Help Center", description = "Find answers to common questions"),

        Setting(type = SettingType.ABOUT, title = "Follow us on TikTok", description = "@nocketapp - Latest updates & tips"),
        Setting(type = SettingType.ABOUT, title = "Follow us on Instagram", description = "@nocketapp - Behind the scenes"),
        Setting(type = SettingType.ABOUT, title = "Follow us on Twitter", description = "@nocketapp - News & announcements"),
        Setting(type = SettingType.ABOUT, title = "Share Nocket", description = "Invite friends to join Nocket"),
        Setting(type = SettingType.ABOUT, title = "Rate Nocket", description = "Leave a review on your app store"),
        Setting(type = SettingType.ABOUT, title = "What's New", description = "Check out latest features"),
        Setting(type = SettingType.ABOUT, title = "Terms of Service", description = "Read our terms and conditions"),
        Setting(type = SettingType.ABOUT, title = "Privacy Policy", description = "Understand how we protect your data"),
        Setting(type = SettingType.ABOUT, title = "Open Source Licenses", description = "View third-party licenses"),

        Setting(type = SettingType.DANGER_ZONE, title = "Delete Account", description = "Permanently delete your account and all data"),
        Setting(type = SettingType.DANGER_ZONE, title = "Sign Out", description = "Sign out from all devices"),
    )

    // Fun statistics for the app
    @RequiresApi(Build.VERSION_CODES.O)
    val stats = mapOf(
        "totalUsers" to users.size,
        "totalMessages" to messages.size,
        "totalPosts" to samplePosts.size,
        "totalSettings" to settings.size,
        "appName" to "Nocket",
        "version" to "1.0.0",
        "lastUpdated" to "August 2025"
    )
}
