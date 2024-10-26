package com.tenyitamas.kip.presentation.news.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tenyitamas.kip.presentation.shared.Orange
import com.tenyitamas.kip.presentation.shared.TextWhite
import java.awt.event.KeyEvent

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var searchInput by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {

        BasicTextField(
            value = searchInput,
            onValueChange = {
                searchInput = it
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = Orange,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
                .onKeyEvent {
                    if (it.key == Key(KeyEvent.VK_ENTER)) {
                        onSearch(searchInput)
                    }
                    true
                }
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = TextWhite
            )
        }
    }
}
