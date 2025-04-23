package com.example.lemon_juice_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemon_juice_app.ui.theme.Lemon_juice_appTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lemon_juice_appTheme {
        LemonJuiceApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonJuiceApp() {
  // remember use to save the state of the variable
  var currentStep by remember { mutableIntStateOf(1) }
  var squeezeCount by remember { mutableIntStateOf(0) }

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = stringResource(R.string.title),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
          )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer
        )
      )
    }) { it: PaddingValues ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .background(MaterialTheme.colorScheme.tertiaryContainer),
      color = MaterialTheme.colorScheme.background
    ) {
      when (currentStep) {
        1 -> {
          MakingLemonJuiceItemUi(
            imageResourceId = R.drawable.lemon_tree,
            textResourceId = R.string.step_1,
            onImageClick = {
              currentStep = 2
              squeezeCount = (2..4).random()
            })
        }

        2 -> {
          MakingLemonJuiceItemUi(
            imageResourceId = R.drawable.lemon_squeeze,
            textResourceId = R.string.step_2,
            squeezeCount = squeezeCount,
            onImageClick = {
              squeezeCount--
              if (squeezeCount == 0) currentStep = 3
            })
        }

        3 -> {
          MakingLemonJuiceItemUi(
            imageResourceId = R.drawable.lemon_drink,
            textResourceId = R.string.step_3,
            onImageClick = {
              currentStep = 4
            })
        }

        4 -> {
          MakingLemonJuiceItemUi(
            imageResourceId = R.drawable.lemon_restart,
            textResourceId = R.string.step_4,
            onImageClick = {
              currentStep = 1
            })
        }
      }
    }
  }
}

@Composable
private fun MakingLemonJuiceItemUi(
  imageResourceId: Int, textResourceId: Int, squeezeCount: Int = 0, onImageClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Button(
      onClick = onImageClick,
      shape = RoundedCornerShape(10.dp),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
      Image(
        painter = painterResource(imageResourceId),
        contentDescription = imageResourceId.toString(),
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    val text = if (squeezeCount == 0) {
      stringResource(textResourceId)
    } else {
      "${stringResource(textResourceId)} ($squeezeCount taps)"
    }
    Text(text = text, fontSize = 18.sp)
  }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
  Lemon_juice_appTheme {
    LemonJuiceApp()
  }
}
