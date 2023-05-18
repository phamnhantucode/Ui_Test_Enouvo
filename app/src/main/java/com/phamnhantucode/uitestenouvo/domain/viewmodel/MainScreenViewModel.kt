package com.phamnhantucode.uitestenouvo.domain.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phamnhantucode.uitestenouvo.data.repository.ApprovalRepositoryImpl
import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.JdkConstants.InputEventMask
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val approvalRepository: ApprovalRepositoryImpl
) : ViewModel() {

    private val _approvalViews = MutableStateFlow(emptyList<ApprovalView>())
    val approvalViews = _approvalViews.asStateFlow()
    val approvalViewsFilter = mutableStateOf(emptyList<ApprovalView>())

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    var feature = emptyList<Feature>()

    init {
        getApprovalViews()
        getFeatures()
    }

    private fun getFeatures() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                feature = approvalRepository.getAllFeatures()
            }
        }
    }

    private fun getApprovalViews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                approvalRepository.getAllApproval().collectLatest { approvals ->
                    _approvalViews.emit(
                        approvals.map { approval ->
                            approval.toApprovalView(approvalRepository)
                        }.also {
                            delay(400)
                            approvalViewsFilter.value = it
                        }
                    )
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.FilterBy -> {
                if (event.feature.size > 0) {
                    approvalViewsFilter.value = approvalViews.value.filter {
                        event.feature.contains(it.feature?.feature)
                    }
                } else {
                    approvalViewsFilter.value = approvalViews.value
                }
            }
        }
    }


    sealed class Event {
        class FilterBy(val feature: List<String>) : Event()
    }
}