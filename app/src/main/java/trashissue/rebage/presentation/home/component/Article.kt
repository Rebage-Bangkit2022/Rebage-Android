package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import trashissue.rebage.presentation.theme3.RebageTheme3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Article(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    photo: String?,
    onClick: (Int) -> Unit = { }
) {
    Card(
        modifier = modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.clickable(onClick = { onClick(id) })) {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Gray)
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    RebageTheme3 {
        Article(
            id = 0,
            title = "Du du du Du du du Du du du Du du du Du du du Du du du Du du du",
            photo = "https://i.pinimg.com/564x/d7/f8/5e/d7f85e8343547676774a4ffdffc96143.jpg"
        )
    }
}
