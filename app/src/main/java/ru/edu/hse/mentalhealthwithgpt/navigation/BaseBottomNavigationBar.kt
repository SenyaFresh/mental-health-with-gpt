package ru.edu.hse.mentalhealthwithgpt.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.mentalhealthwithgpt.R
import ru.edu.hse.themes.PrimaryColor
import ru.edu.hse.themes.SecondaryColor

@Composable
fun BaseBottomNavigationBar(
    items: List<BaseBottomNavigationItem>,
    selected: Int
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = PrimaryColor
    ) {
        items.forEachIndexed { index, baseBottomNavigationItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { baseBottomNavigationItem.onItemClick(baseBottomNavigationItem.screen) },
                icon = {
                    Icon(
                        painter = painterResource(id = baseBottomNavigationItem.icon),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.White,
                    indicatorColor = SecondaryColor
                )
            )
        }
    }

    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(2.dp)
        .background(color = Color.Black))
}

@Preview()
@Composable
fun BaseBottomNavigationBarPreview() {
    BaseBottomNavigationBar(
        items = listOf(
            BaseBottomNavigationItem(R.drawable.ic_question, Screen.AssistantScreen) { _ -> },
            BaseBottomNavigationItem(R.drawable.ic_home, Screen.HomeScreen) { _ -> },
            BaseBottomNavigationItem(R.drawable.ic_profile, Screen.ProfileScreen) { _ -> }
        ),
        selected = 1
    )
}

data class BaseBottomNavigationItem(
    @DrawableRes val icon: Int,
    val screen: Screen,
    val onItemClick: (Screen) -> Unit
)