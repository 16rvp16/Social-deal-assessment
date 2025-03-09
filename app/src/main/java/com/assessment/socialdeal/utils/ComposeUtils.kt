package com.assessment.socialdeal.utils

import androidx.compose.ui.Modifier

/**
 * Extension function on Modifier to allow for conditional changes
 */
inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    then(ifTrue(Modifier))
} else {
    then(ifFalse(Modifier))
}