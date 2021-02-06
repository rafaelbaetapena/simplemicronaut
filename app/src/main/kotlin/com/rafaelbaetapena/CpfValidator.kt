package com.rafaelbaetapena

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CpfValidator::class])
@MustBeDocumented
annotation class ValidCpf(val message : String = "CPF is invalid")

@Singleton
class CpfValidator : ConstraintValidator<ValidCpf, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<ValidCpf>,
        context: ConstraintValidatorContext
    ): Boolean {

        if(value.isNullOrEmpty()) return false

        val valueWithDigitsOnly = onlyDigit(value.orEmpty())

        if(!hasTheCorrectSize(valueWithDigitsOnly))
            return false

        if(hasAllTheSameNumbers(valueWithDigitsOnly))
            return false

        if(!firstCheckDigitIsValid(valueWithDigitsOnly))
            return false

        if(!secondCheckDigitIsValid(valueWithDigitsOnly))
            return false

        return true
    }

    private fun onlyDigit(value: String) : String = value.filter { it.isDigit() }

    private fun hasTheCorrectSize(value: String) : Boolean = value.length == 11

    private fun hasAllTheSameNumbers(value: String) : Boolean = !value.any { value[0] != it }

    private fun firstCheckDigitIsValid(value: String) : Boolean {

        val sum = sumOfTheDigits(value, 9)
        val checkDigit = calculateTheCheckDigit(sum)
        return checkDigit == value[9].toString().toInt()
    }

    private fun secondCheckDigitIsValid(value: String) : Boolean {

        val sum = sumOfTheDigits(value, 10)
        val checkDigit = calculateTheCheckDigit(sum)
        return checkDigit == value[10].toString().toInt()
    }

    private fun sumOfTheDigits(value: String, quantityOfDigits: Int) : Int {

        var sum = 0
        var currentWeight = quantityOfDigits + 1

        (0 until quantityOfDigits).forEach {
            val currentDigit = value[it].toString().toInt()
            sum += currentDigit * currentWeight
            currentWeight--
        }

        return sum
    }

    private fun calculateTheCheckDigit(sum: Int) : Int {

        val remainder = (sum % 11)
        return if (remainder < 2) 0 else 11 - remainder
    }
}
