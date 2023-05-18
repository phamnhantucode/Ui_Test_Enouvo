package com.phamnhantucode.uitestenouvo.domain.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phamnhantucode.uitestenouvo.data.repository.ApprovalRepositoryImpl
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Approver
import com.phamnhantucode.uitestenouvo.domain.model.Feature
import com.phamnhantucode.uitestenouvo.domain.validate.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ApprovalMatrixScreenViewModel @Inject constructor(
    val approvalRepository: ApprovalRepositoryImpl
) : ViewModel() {

    private val _approvalView: MutableState<ApprovalView> = mutableStateOf(ApprovalView())
    val approvalView: State<ApprovalView> = _approvalView

    var alias = mutableStateOf("")
    var featureSelected = mutableStateOf<Feature?>(null)
    var approverSelected = MutableStateFlow(mutableMapOf<Int, Approver>())
    var minimum = mutableStateOf("")
    var maximum = mutableStateOf("")
    var numOfApproval = mutableStateOf(0)

    var onShowListOption = mutableStateOf(false)
    var saveListOption = emptyList<String>()
    var listStringOption = MutableStateFlow(mutableListOf<String>())
    var onSelectOption: (String) -> Unit = {}
    var listOptionSelected = MutableStateFlow(mutableListOf<String>())
    var filterOption = mutableStateOf("")


    val error = mutableStateOf(ErrorValidateInput())

    val isShowConfirmDialog = mutableStateOf(false)
    val isShowErrorDialog = mutableStateOf(false)

    var features = emptyList<Feature>()
    var approvers = emptyList<Approver>()

    init {
        getFeatures()
        getApprovers()
    }

    private fun getFeatures() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                features = approvalRepository.getAllFeatures()
            }
        }
    }

    private fun getApprovers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                approvers = approvalRepository.getAllApprovers()
            }
        }
    }

    fun getApproval(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (id >= 0) {
                    _approvalView.value =
                        approvalRepository.getApproval(id = id)?.toApprovalView(approvalRepository)?.also {av ->
                            alias.value = av.alias
                            featureSelected.value = av.feature
                            minimum.value = av.minimum.toString()
                            maximum.value = av.maximum.toString()
                            numOfApproval.value = av.num_of_approver
                            val tempMap = mutableMapOf<Int, Approver>()
                            for (i in 0 until numOfApproval.value) {
                                 tempMap[i] = av.approvers[i]
                            }
                            approverSelected.emit(tempMap)
                        } ?: ApprovalView()
                }

            }
        }
    }

    fun showListOptionFeature() {
        viewModelScope.launch {
            onShowListOption.value = true
            saveListOption = features.map { it.feature }
            listStringOption.emit(features.map { it.feature }.toMutableList())
            listOptionSelected.emit(
                if (featureSelected.value == null) mutableListOf() else mutableListOf(
                    featureSelected.value!!.feature
                )
            )
            onSelectOption = { option ->
                Log.e("asda", "asdasd")
                featureSelected.value = features.find { it.feature == option }
                viewModelScope.launch {
                    listOptionSelected.emit(mutableListOf(option))
                }
            }
        }
    }

    fun showListOptionApprover(index: Int) {
        viewModelScope.launch {
            onShowListOption.value = true
            saveListOption = approvers.map { it.approver }
            listStringOption.emit(approvers.map { it.approver }.toMutableList())
            listOptionSelected.emit(
                approverSelected.value.values.map { it.approver }.toMutableList()
            )
            onSelectOption = { option ->

                viewModelScope.launch {

                    approverSelected.emit(approverSelected.value.toMutableMap().apply {
                        this[index] = approvers.find { it.approver == option }!!
                    })
                    listOptionSelected.emit(
                        approverSelected.value.values.map { it.approver }.toMutableList()
                    )
                }
            }
        }
    }

    fun selectApprover(index: Int, approver: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val approverSelect = approvers.find { it.approver == approver }
                if (approverSelect != null) {
                    approverSelected.value[index] = approverSelect
                }
            }
        }
    }

    fun checkInputApprovalMatrix() {
        val aliasResult = ValidateApprovalAlias.execute(alias = alias.value)
        val featureResult = ValidateFeature.execute(featureSelected.value)
        val rangeOfApprovalResult = ValidateRangeOfApproval.execute(
            minimum = if (minimum.value.isBlank()) -1 else minimum.value.toLong(),
            maximum = if (maximum.value.isBlank()) -1 else maximum.value.toLong()
        )
        val numOfApproverResult = ValidateNumOfApproval.execute(
            numOfApproval = numOfApproval.value,
            numOfApprover = approvers.size
        )

        val approversResult = ValidateApprovers.execute(
            approverSelected.value.map { it.value },
            numOfApproval = numOfApproval.value
        )

        val hasError = listOf(
            aliasResult,
            featureResult,
            rangeOfApprovalResult,
            numOfApproverResult,
            approversResult
        ).any { !it.successful }

        if (hasError) {
            error.value = error.value.copy(
                aliasResult = aliasResult.errorMessage ?: "",
                featureResult = featureResult.errorMessage ?: "",
                rangeOfApprovalResult = rangeOfApprovalResult.errorMessage ?: "",
                numOfApproverResult = numOfApproverResult.errorMessage ?: "",
                approversResult = approversResult.errorMessage ?: ""
            )
            isShowErrorDialog.value = true
            return
        } else {
            isShowConfirmDialog.value = true
        }
    }

    fun addNewApprovalMatrix() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addNewApprovalMatrix(
                    ApprovalView(
                        alias = alias.value,
                        minimum = minimum.value.toLong(),
                        maximum = maximum.value.toLong(),
                        num_of_approver = numOfApproval.value,
                        feature = featureSelected.value,
                        approvers = approverSelected.value.map { it.value }
                    )
                )
            }
        }
    }

    private suspend fun addNewApprovalMatrix(approvalView: ApprovalView) {
        approvalRepository.addNewApprovalMatrix(approvalView)
    }

    fun resetValue() {
        alias.value = ""
        featureSelected.value = null
        approverSelected.value =  mutableMapOf()
        minimum.value = ""
        maximum.value = ""
        numOfApproval.value = 0
    }

    fun updateApprovalMatrix() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                approvalRepository.updateApprovalView(
                    approvalView.value.copy(
                        alias = alias.value,
                        minimum = minimum.value.toLong(),
                        maximum = maximum.value.toLong(),
                        num_of_approver = numOfApproval.value,
                        feature = featureSelected.value,
                        approvers = approverSelected.value.map { it.value }
                    )
                )
            }
        }
    }

    fun deleteApprovalMatrix() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                approvalRepository.deleteApprovalView(
                    approvalView.value.copy(
                        alias = alias.value,
                        minimum = minimum.value.toLong(),
                        maximum = maximum.value.toLong(),
                        num_of_approver = numOfApproval.value,
                        feature = featureSelected.value,
                        approvers = approverSelected.value.map { it.value }
                    )
                )
            }
        }
    }

    fun onEvent(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.FilterOption -> {
                    listStringOption.emit(saveListOption.filter { it.lowercase().contains(filterOption.value) }.toMutableList())
                }
            }
        }
    }

    sealed class Event {
        object FilterOption : Event()
    }


    data class ErrorValidateInput(
        val aliasResult: String = "",
        val featureResult: String = "",
        val rangeOfApprovalResult: String = "",
        val numOfApproverResult: String = "",
        val approversResult: String = ""
    )
}
