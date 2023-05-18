package com.phamnhantucode.uitestenouvo.domain.validate

object ValidateApprovalAlias {
    fun execute(alias: String): ValidationResult {
        if (alias.isBlank())
            return ValidationResult(
                successful = false,
                errorMessage = "Alias field is empty!"
            )
        return ValidationResult(true)
    }
}