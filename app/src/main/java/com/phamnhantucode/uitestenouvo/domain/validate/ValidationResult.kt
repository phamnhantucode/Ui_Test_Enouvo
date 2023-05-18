package com.phamnhantucode.uitestenouvo.domain.validate

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)