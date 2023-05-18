package com.phamnhantucode.uitestenouvo.domain.validate

object ValidateNumOfApproval {
    fun execute(numOfApproval: Int, numOfApprover: Int): ValidationResult {
        if (numOfApproval == 0) {
            return ValidationResult(false, "You must fill Number of Approval field")
        }
        if (numOfApproval > numOfApprover) {
            return ValidationResult(false, "No. of approval in matrix list is more than number of approver in your company")
        }
        return ValidationResult(true)
    }
}