package com.phamnhantucode.uitestenouvo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.phamnhantucode.uitestenouvo.ui.screens.NavigationGraph
import com.phamnhantucode.uitestenouvo.ui.theme.UITestENOUVOTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITestENOUVOTheme {
                // A surface container using the 'background' color from the theme
                NavigationGraph()
            }
        }
    }
}
