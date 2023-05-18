package com.phamnhantucode.uitestenouvo.domain.validate

import com.phamnhantucode.uitestenouvo.domain.model.ApprovalView
import com.phamnhantucode.uitestenouvo.domain.model.Feature

object ValidateFeature {
    fun execute(feature: Feature?): ValidationResult {
        if (feature == null) {
            return ValidationResult(false, "You must select feature field")
        }
        return ValidationResult(true)
    }
}