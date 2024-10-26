package com.tenyitamas.kip.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import com.tenyitamas.kip.domain.model.Article

@Composable
fun NewsItem(
    article: Article,
    onArticleClick: () -> Unit,
    modifier: Modifier = Modifier,
    additionalContent: @Composable()((ColumnScope) -> Unit)? = null
) {

    val placeholderPainter = painterResource("drawable/placeholder.png")
    val painter =
        if (article.urlToImage != null) {
            rememberImagePainter(url = article.urlToImage, placeholderPainter = {
                placeholderPainter
            }, errorPainter = { placeholderPainter })
        } else {
            placeholderPainter
        }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .padding(4.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(color = MediumGray)
            .clickable { onArticleClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        article.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h6,
                maxLines = 3,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Orange
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        article.description?.let {
            val descriptionDisplayText = it
            Text(
                text = descriptionDisplayText,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 3,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = TextWhite
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        additionalContent?.invoke(this)
    }
}
