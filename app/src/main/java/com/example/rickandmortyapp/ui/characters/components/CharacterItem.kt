package com.example.rickandmortyapp.ui.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.ui.characters.utils.createCharacterForPreview

@Composable
fun CharacterItem(
    character: Character,
    onFavoriteClick: (Character) -> Unit,
    onItemClick: (Int, String) -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(character.id, character.name) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    error = BrushPainter(
                        Brush.linearGradient(
                            listOf(
                                Color.Red,
                                Color.Blue,
                            )
                        )
                    ),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = character.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                IconButton(onClick = {
                    onFavoriteClick(character)
                }) {
                    Icon(
                        imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (character.isFavorite) "Favorite" else "Not Favorite",
                        modifier = Modifier.testTag("FavoriteIcon_${character.name}")
                    )
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun CharacterItemPreview() {
    CharacterItem(
            character = createCharacterForPreview(),
            onFavoriteClick = {},
            onItemClick = {_, _ -> }
    )
}
