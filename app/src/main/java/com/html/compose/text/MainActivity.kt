package com.html.compose.text

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.html.compose.text.ui.theme.HTMLComposeTextTheme

/**
 * Created by Rehan Sarwar on 18/03/2022.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HTMLComposeTextTheme {
                // A surface container using the 'background' color from the theme
                ScreenContent()
            }
        }
    }
}
@Composable
fun ScreenContent() {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(R.string.title)) })
    }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val htmlTags = HtmlTag.values().toList().sortedBy { it.name }
            LazyColumn(modifier = Modifier.padding(bottom = 12.dp)) {
                item {
                    HtmlText(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        text = stringResource(id = com.html.compose.text.R.string.html_string),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primaryVariant,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                        URLSpanStyle = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                itemsIndexed(htmlTags) { _, item ->
                    ItemHTMLPreview(tag = item.tag, htmlString = item.content)
                }
            }
        }
    }
}