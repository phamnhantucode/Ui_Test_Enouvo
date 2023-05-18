package com.phamnhantucode.uitestenouvo.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.phamnhantucode.uitestenouvo.*
import com.phamnhantucode.uitestenouvo.R
import com.phamnhantucode.uitestenouvo.domain.viewmodel.ApprovalMatrixScreenViewModel
import com.phamnhantucode.uitestenouvo.ui.components.ConfirmDialog
import com.phamnhantucode.uitestenouvo.ui.components.FailedDialog
import com.phamnhantucode.uitestenouvo.ui.theme.BrandingGrey
import com.phamnhantucode.uitestenouvo.ui.theme.LightGrey0
import com.phamnhantucode.uitestenouvo.ui.theme.LightGrey1

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApprovalMatrixScreen(
    navController: NavHostController,
    argument: Bundle?,
    viewModel: ApprovalMatrixScreenViewModel = hiltViewModel()
) {
    val type = argument?.getString("type") ?: "add"
    argument?.getInt("id")?.let {
        viewModel.getApproval(it)
    }
    Scaffold(topBar = {
        TopAppBarApprovalMatrixScreen() {
            navController.popBackStack()
        }
    }, content = {
        when (type) {
            "add" -> AddScreen(viewModel = viewModel, navController = navController)
            "edit" -> EditScreen(viewModel = viewModel, navController = navController)
        }

    })
}

@Composable
fun EditScreen(
    viewModel: ApprovalMatrixScreenViewModel,
    navController: NavHostController
) {
    Surface(
        color = MaterialTheme.colors.primary
    ) {
        var onShowListOption = remember {
            viewModel.onShowListOption
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(horizontal = 25.dp, vertical = 15.dp)
        ) {
            GroupInputField(
                onShowListOption, modifier = Modifier.weight(Float.MAX_VALUE)
            )

            //Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtonApprovalMatrixScreen(
                    textColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    title = stringResource(id = R.string.update).uppercase()
                ) {

                }
                ButtonApprovalMatrixScreen(
                    textColor = MaterialTheme.colors.primaryVariant,
                    backgroundColor = Color.White,
                    title = stringResource(id = R.string.delete).uppercase()
                ) {

                }
            }
        }
        //Bottom sheet list option
        BottomSheetSelection(
            onShowListOption,
            "Select Feature",
            onSelected = viewModel.onSelectOption
        )
    }
}

@Composable
fun AddScreen(
    viewModel: ApprovalMatrixScreenViewModel,
    navController: NavHostController
) {
    Surface(
        color = MaterialTheme.colors.primary
    ) {
        var listOptions =
            viewModel.listStringOption

        var onShowListOption = remember {
            viewModel.onShowListOption
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(horizontal = 25.dp, vertical = 15.dp)
        ) {
            GroupInputField(
                onShowListOption, modifier = Modifier.weight(Float.MAX_VALUE)
            )

            //Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtonApprovalMatrixScreen(
                    textColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    title = stringResource(id = R.string.add_to_list).uppercase()
                ) {
                    viewModel.checkInputApprovalMatrix()
                }
                ButtonApprovalMatrixScreen(
                    textColor = MaterialTheme.colors.primaryVariant,
                    backgroundColor = Color.White,
                    title = stringResource(id = R.string.reset).uppercase()
                ) {
                    viewModel.resetValue()
                }
            }
        }
        //Bottom sheet list option
        BottomSheetSelection(
            onShowListOption, "Select Feature"
        ) {
            viewModel.onSelectOption(it)
        }

        //show dialog
        val isShowErrorDialog = viewModel.isShowErrorDialog
        val isShowConfirmDialog = viewModel.isShowConfirmDialog
        if (isShowConfirmDialog.value) {
            ConfirmDialog(
                message = stringResource(id = R.string.confirm_add),
                title = stringResource(id = R.string.confirm_to_add),
                onConfirm = {
                    viewModel.addNewApprovalMatrix()
                    navController.popBackStack()
                }) {
                isShowConfirmDialog.value = false
            }
        }
        if (isShowErrorDialog.value) {
            FailedDialog(
                message = listOf(
                    viewModel.error.value.aliasResult,
                    viewModel.error.value.approversResult,
                    viewModel.error.value.featureResult,
                    viewModel.error.value.numOfApproverResult,
                    viewModel.error.value.rangeOfApprovalResult,
                ),
                title = stringResource(id = R.string.failed)
            ) {
                isShowErrorDialog.value = false
            }
        }

    }
}


@Composable
fun GroupInputField(
    onShowListOption: MutableState<Boolean>,
    modifier: Modifier,
    viewModel: ApprovalMatrixScreenViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {

        HeaderBar("Create New Approval Matrix")
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            val error = viewModel.error.value
            TopFieldBar(
                error = error,
                onClickSelectionField = {
                    viewModel.showListOptionFeature()
                }
            )
            Divider()
            BottomFieldBar(
                error = error,
            )
        }
    }
}


@Composable
fun BottomSheetSelection(
    onShowListOption: MutableState<Boolean>,
    title: String,
    onSelected: (String) -> Unit
) {
    AnimatedVisibility(
        visible = onShowListOption.value,
        enter = slideInVertically(initialOffsetY = {
            it
        }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = {
            it
        }) + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color(0x80000000))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 25.dp)
                ) {
                    //Header bottom sheet
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //Header
                        Text(
                            text = title, style = TextStyle(
                                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp
                            ), modifier = Modifier.align(Alignment.Center)
                        )
                        //Icon exit
                        IconButton(
                            onClick = {
                                onShowListOption.value = false
                            }, modifier = Modifier.align(Alignment.CenterEnd)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF444444))
                                    .padding(5.dp)
                            )


                        }
                    }

                    //Search bottom sheet
                    SearchField(value = "", onValueChange = {

                    })
                }
                //list selection
                CheckBoxGroup(
                    onSelected = onSelected
                )
            }
        }
    }
}

@Composable
private fun CheckBoxGroup(
    onSelected: (String) -> Unit,
    viewModel: ApprovalMatrixScreenViewModel = hiltViewModel()
) {

    val listSelected = viewModel.listOptionSelected.collectAsState()
    val listOption = viewModel.listStringOption.collectAsState()
    Column(
        Modifier
            .selectableGroup()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        listOption.value.forEach { text ->
            SelectOption(
                text = text,
                isSelectedOption = listSelected.value.contains(text),
                onSelectOption = {
                    onSelected(it)
                }
            )
        }
    }
}

@Composable
fun SelectOption(
    text: String, isSelectedOption: Boolean, onSelectOption: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrey0)
            .padding(vertical = 20.dp, horizontal = 25.dp)
            .clickable { onSelectOption(text) }) {
        Text(
            text = text, style = TextStyle(
                fontSize = 15.sp,
                color = Color.Black,
            )
        )
        Box(
            modifier = Modifier
                .size(15.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(Color.White)
                .border(
                    color = MaterialTheme.colors.primaryVariant,
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(1.dp)
        ) {
            if (isSelectedOption) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_stroke),
                    contentDescription = "icon stroke",
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 15.sp,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colors.primaryVariant
                )
                Box(
                    modifier = Modifier
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.search_keyword),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else innerTextField()
                }
            }
        })
}

@Composable
fun BottomFieldBar(
    viewModel: ApprovalMatrixScreenViewModel = hiltViewModel(),
    error: ApprovalMatrixScreenViewModel.ErrorValidateInput,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        var minimum = remember {
            viewModel.minimum
        }
        var maximum = remember {
            viewModel.maximum
        }
        var numOfApproval = remember {
            viewModel.numOfApproval
        }
        RangeOfApprovalField(type = "Minimum", value = minimum.value, error.rangeOfApprovalResult) {
            minimum.value = it
        }
        RangeOfApprovalField(type = "Maxmum", value = maximum.value, error.rangeOfApprovalResult) {
            maximum.value = it
        }
        NumOfApprovalField(value = if (numOfApproval.value == 0) "" else numOfApproval.value.toString(),
            onValueChange = {
                numOfApproval.value = if (it.isBlank()) 0 else it.toInt()
            },
        error = error.numOfApproverResult)

        val approverSelected =
            viewModel.approverSelected.collectAsState()
        //Approver selection
        for (i in 1..numOfApproval.value) {
            val value =
                if (approverSelected.value[i - 1] == null) stringResource(id = R.string.select_approver) else approverSelected.value[i - 1]!!.approver
            SelectionField(
                label = "${stringResource(id = R.string.approver)} (${stringResource(id = R.string.sequence)} $i)",

                modifier = Modifier.clickable { viewModel.showListOptionApprover(i - 1) },
                value = value,
                error = error.approversResult
            )
        }
    }

}

@Composable
fun NumOfApprovalField(
    value: String,
    error: String,
    onValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.number_of_approval),
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        BasicTextField(value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_number),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else innerTextField()
                }
            })
        //error text field
        Text(
            text = error, style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }
}

@Composable
fun RangeOfApprovalField(
    type: String, value: String, error: String, onValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.range_of_approval) + " ($type)",
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        BasicTextField(value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.currency),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_text_here),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else innerTextField()
                }
            })
        //error text field
        Text(
            text = error, style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }
}

@Composable
fun TopFieldBar(
    onClickSelectionField: () -> Unit,
    viewModel: ApprovalMatrixScreenViewModel = hiltViewModel(),
    error: ApprovalMatrixScreenViewModel.ErrorValidateInput
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        var alias = remember {
            viewModel.alias
        }
        //Approval matrix alias
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.approval_matrix_alias),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                ),
            )
            BasicTextField(value = alias.value, onValueChange = {
                alias.value = it
            }, singleLine = true, maxLines = 1, textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ), decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                ) {
                    if (alias.value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.holder_field_input_matrix_name),
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 15.sp,
                            ),
                        )
                    } else {
                        innerTextField()
                    }
                }
            })
            //error text field
            Text(
                text = error.aliasResult, style = TextStyle(
                    fontSize = 10.sp,
                    color = Color.Red,
                )
            )
        }

        val featureSelected = remember {
            viewModel.featureSelected
        }

        //FeatureField
        SelectionField(
            modifier = Modifier.clickable { onClickSelectionField.invoke() },
            label = stringResource(id = R.string.feature),
            value = if (featureSelected.value == null) stringResource(id = R.string.select_feature) else featureSelected.value!!.feature,
            error = error.featureResult
        )
    }

}

@Composable
fun SelectionField(
    modifier: Modifier,
    label: String,
    value: String,
    error: String
) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
            ),
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp)
                )
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value, style = TextStyle(
                    color = if (value == stringResource(id = R.string.select_approver) || value == stringResource(
                            id = R.string.select_feature
                        )
                    ) Color.LightGray else Color.Black,
                    fontSize = 15.sp,
                ), modifier = Modifier
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "KeyboardArrowDown",
                modifier = Modifier,
                tint = Color(0xFF919192)
            )
        }
        //error text field
        Text(
            text = error, style = TextStyle(
                fontSize = 10.sp,
                color = Color.Red,
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarApprovalMatrixScreen(
    onBack: () -> Unit,
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
    }, navigationIcon = {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onBack.invoke() },
            tint = MaterialTheme.colors.primary
        )
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colors.primary
    )
    )
}

@Composable
fun ButtonApprovalMatrixScreen(
    textColor: Color,
    backgroundColor: Color,
    title: String,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enable) backgroundColor else LightGrey1,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(
            text = title, style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (enable) textColor else BrandingGrey
            )
        )
    }
}

@Composable
fun HeaderBar(
    title: String,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = title, style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Divider(color = Color(0xFFD1D2D4))
    }

}

