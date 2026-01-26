package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import repositories.ImageAssetModel
import java.io.File

@Composable
fun RecentAssetsBar(
    assets: List<ImageAssetModel>,
    onAssetClick: (ImageAssetModel) -> Unit
) {
    var isMinimized by remember { mutableStateOf(false) }
    if (assets.isEmpty()) return

    Surface(
        color = Color.Black.copy(alpha = 0.7f), // Halb-transparent
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Text links, Button rechts
            ) {
                Text(
                    "Recently Used",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )

                IconButton(
                    onClick = { isMinimized = !isMinimized },
                    modifier = Modifier.size(32.dp) // Kleinerer Button spart Platz
                ) {
                    Icon(
                        imageVector = if (isMinimized) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        tint = Color.White,
                        contentDescription = "Toggle"
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = !isMinimized,
                enter = androidx.compose.animation.expandVertically(),
                exit = androidx.compose.animation.shrinkVertically()
            ) {
                LazyRow(
                    contentPadding = PaddingValues(bottom = 8.dp, top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(assets) { asset ->
                        RecentAssetItem(asset, onClick = { onAssetClick(asset) })
                    }
                }
            }
        }
    }
}

@Composable
fun RecentAssetItem(asset: ImageAssetModel, onClick: () -> Unit) {
    // Bild laden (deine Helper Funktion)
    val bitmap = remember(asset.path) {
        // Achtung: Imports prüfen, du hast loadImageBitmap in TableTopView definiert
        // Besser: In ImageUtils.kt verschieben
        loadImageBitmap(File(asset.path))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = asset.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            text = asset.name,
            color = Color.White,
            fontSize = 10.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}