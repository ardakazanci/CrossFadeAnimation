package com.ardakazanci.crossfadeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ardakazanci.crossfadeanimation.ui.theme.CrossFadeAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrossFadeAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CrossFadeSample(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CrossFadeSample(modifier: Modifier = Modifier) {
    val imageList = listOf(
        R.drawable.image_4,
        R.drawable.image_2,
        R.drawable.image_3
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var animationSpec by remember { mutableStateOf<FiniteAnimationSpec<Float>>(tween(durationMillis = 1000)) }
    var animationDuration by remember { mutableIntStateOf(1000) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Crossfade(
            targetState = currentIndex,
            animationSpec = animationSpec,
            label = ""
        ) { index ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = imageList[index]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Text("Animation Duration: ${animationDuration}ms")
        Slider(
            value = animationDuration.toFloat(),
            onValueChange = { animationDuration = it.toInt() },
            valueRange = 300f..2000f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    animationSpec = tween(durationMillis = animationDuration)
                    if (currentIndex > 0) currentIndex--
                },
                enabled = currentIndex > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("<")
            }

            Button(
                onClick = {
                    animationSpec = tween(durationMillis = animationDuration)
                    if (currentIndex < imageList.size - 1) currentIndex++
                },
                enabled = currentIndex < imageList.size - 1,
                modifier = Modifier.weight(1f)
            ) {
                Text(">")
            }
        }

        Button(
            onClick = {
                animationSpec = keyframes {
                    durationMillis = animationDuration
                    0.4f at (animationDuration / 3)
                    0.8f at (2 * animationDuration / 3)
                }
                currentIndex = (imageList.indices).random()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Random Transition")
        }
    }
}



