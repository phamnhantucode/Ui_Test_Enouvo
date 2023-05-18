package com.phamnhantucode.uitestenouvo.domain.validate

import com.phamnhantucode.uitestenouvo.domain.model.Feature

object ValidateRangeOfApproval {
    fun execute(minimum: Long, maximum: Long): ValidationResult {
        if (minimum == -1L) {
            return ValidationResult(false, "You must fill Minimum field")
        }
        if (maximum == -1L) {
            return ValidationResult(false, "You must fill Maximum field")
        }
        if (minimum > maximum) {
            return ValidationResult(false, "Range limit can’t be overlapping")
        }
        return ValidationResult(true)
    }
}