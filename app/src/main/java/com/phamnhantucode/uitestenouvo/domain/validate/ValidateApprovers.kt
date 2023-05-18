package com.phamnhantucode.uitestenouvo.domain.validate

import com.phamnhantucode.uitestenouvo.domain.model.Approver

object ValidateApprovers {
    fun execute(approvers: List<Approver>, numOfApproval: Int): ValidationResult {
        if (approvers.size < numOfApproval)
            return ValidationResult(
                successful = false,
                errorMessage = "These is exist an empty approver field"
            )
        return ValidationResult(true)
    }
}