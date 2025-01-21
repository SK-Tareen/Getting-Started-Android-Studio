package com.example.saniahw1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.saniahw1.ui.theme.SampleData
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.example.saniahw1.ui.theme.SaniaHW1Theme

// ...

// ...

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaniaHW1Theme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}
data class Message(val author: String, val body: String)

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Ensures LazyColumn uses the available screen space
            .padding(8.dp) // Add padding around the content
    ) {
        // Add the header with a blue background
        item {
            Surface(
                color = MaterialTheme.colorScheme.primary, // Set the blue background (primary color)
                modifier = Modifier.fillMaxWidth() // Make the surface span the full width
            ) {
                Text(
                    text = "A Conversation With Myself",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary // Ensure text is readable on the blue background
                    ),
                    modifier = Modifier.padding(10.dp) // Add padding around the text
                )
            }
        }
        // Display the messages
        items(messages) { message ->
            MessageCard(message)
        }
    }
}



@Preview(
    name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewConversation() {
    SaniaHW1Theme {
        Conversation(SampleData.conversationSample)
    }
}


@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier
        .padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.me),
            contentDescription = "Any picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        Column(modifier = Modifier
            .clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(2.dp)){
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}