package com.phamnhantucode.uitestenouvo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.ui.components.FailedActionDialog
import com.phamnhantucode.uitestenouvo.ui.screens.ApprovalMatrixScreen
import com.phamnhantucode.uitestenouvo.ui.screens.MainScreen
import com.phamnhantucode.uitestenouvo.ui.screens.NavigationGraph
import com.phamnhantucode.uitestenouvo.ui.screens.TopFieldBar
import com.phamnhantucode.uitestenouvo.ui.theme.UITestENOUVOTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITestENOUVOTheme {
                // A surface container using the 'background' color from the theme
                NavigationGraph()
//                Scaffold(
//                    topBar = {
//                        TopAppBarMainScreen()
//                    }, content = {
//                        Surface(
//                            color = MaterialTheme.colors.primary
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
//                                    .background(Color.White)
//                                    .padding(horizontal = 25.dp, vertical = 15.dp)
//                            ) {
//                                Column(
//                                    verticalArrangement = Arrangement.spacedBy(10.dp)
//                                ) {
//                                    NewMatrixBar()
//                                    Divider()
//                                    ListFilterBar(filter = listOf())
//                                    ListMatrixBar()
//                                    Divider()
//                                }
//
//                            }
//                        }
//                    })
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    UITestENOUVOTheme {
//        TopAppBarMainScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    UITestENOUVOTheme {
//        NewMatrixBar()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewCardMatrix() {
//    UITestENOUVOTheme {
//        CardApprovalMatrix(
//
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewFilterBar() {
    UITestENOUVOTheme {
        FailedActionDialog(message = listOf("asdasdadasd", "asdsadasdasdasdadasdasd"), onConfirm = {}, title = "Confirm dialog") {

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTopFieldMt() {
//    UITestENOUVOTheme {
//        var value by remember {
//            mutableStateOf("")
//        }
//        TopFieldBar(value = value, onValueChange = {
//            value = it
//        }) {
//            onShowListOption = true
//        }
//    }
//}

