package com.free.tvtracker.core.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ResImage(res: DrawableResource, contentDescription: String, modifier: Modifier = Modifier, tint: Color? = null) {
    if (!LocalInspectionMode.current) { //not in @Preview, previews break with this
        Icon(
            painter = painterResource(res),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint ?: LocalContentColor.current
        )
    } else {
        Icon(Icons.Default.ThumbUp, "", modifier = modifier)
    }
}
