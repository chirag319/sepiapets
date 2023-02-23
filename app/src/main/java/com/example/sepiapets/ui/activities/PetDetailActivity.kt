package com.example.sepiapets.ui.activities

import android.os.Build.VERSION
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sepiapets.ui.theme.AppTheme
import com.example.sepiapets.R
import com.example.sepiapets.model.Pet
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

class PetDetailActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Returns Pet Object for Selected item from list
        val petData = if (VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("DATA", Pet::class.java)
        } else {
            intent.getParcelableExtra<Pet>("DATA")
        }
        val contentUrl: String = petData?.contentUrl ?: ""
        // Set The UI Content for Pet Details
        setContent {
            AppTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { petData?.title?.let { Text(it) } }) },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (petData != null) {
                                PuppyImage(petData)
                            }
                            PetContentView(contentUrl)
                        }
                    })
                SetStatusBarColor()
            }
        }
    }

    // @PetContentView Set the Pet details from content url
    @Composable
    fun PetContentView(contentUrl:String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            
            val state = rememberWebViewState(contentUrl)

            Box(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.padding(top = 50.dp).fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(
                        text = stringResource(id = R.string.message_wait),
                        fontWeight = FontWeight.Bold
                    )
                }
                
                WebView(
                    state = state,
                    onCreated = { it.settings.javaScriptEnabled = true }
                )
            }
        }
    }
}
