package com.example.nocket.models

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val avatar: String = "https://instagram.fsgn19-1.fna.fbcdn.net/v/t51.2885-19/523033914_18314847469243062_492430163421338442_n.jpg?stp=dst-jpg_s150x150_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6InByb2ZpbGVfcGljLmRqYW5nby4xMDgwLmMyIn0&_nc_ht=instagram.fsgn19-1.fna.fbcdn.net&_nc_cat=106&_nc_oc=Q6cZ2QEgYUyKbvgNLW-TFy7xiPb1E83dCvJmCsbjKntMeX_KwN_GFlvXa2-ViuRv5s2crdg&_nc_ohc=BD5vPfc5MwYQ7kNvwGNQauM&_nc_gid=c8Dq3ccbGQN_yJ4FrLIZSg&edm=AEYEu-QBAAAA&ccb=7-5&oh=00_AfUiy9uyQS7AfF5AlE7Juae8eZSidoJ9ZOJeQ-NksG1SRA&oe=689748E9&_nc_sid=ead929",
)

val userList = listOf(
    User(username = "hclw"),
    User(username = "jane_smith"),
    User(username = "alex_chen"),
    User(username = "maria_garcia"),
    User(username = "david_kim"),
    User(username = "sarah_wilson"),
    User(username = "mike_jones"),
    User(username = "emma_brown"),
    User(username = "lucas_taylor"),
    User(username = "sophia_lee")
)