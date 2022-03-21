package com.html.compose.text

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Rehan Sarwar on 19/03/2022.
 */

@Composable
fun ItemHTMLPreview(tag:String, htmlString:String) {
    val context= LocalContext.current
    Card(
        shape = CutCornerShape(topEnd = 24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tag,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold
                )
            )
            HtmlText(
                text = htmlString,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
//                linkClicked = { link ->
//                    Toast.makeText(context,link, Toast.LENGTH_SHORT).show()
//                },
                URLSpanStyle = SpanStyle(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}