package com.example.nocket.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nocket.models.Post

enum class Month(
    val displayName: String
) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December")
}

data class MonthPosts(
    val month: Month,
    val year: Int,
    val posts: List<Post>
)

fun calculateDaysOfMonthInYear(
    month: Month,
    year: Int
): Int {
    return when (month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
    }
}

@Composable
fun UserProfile(
    navController: NavController
){

}