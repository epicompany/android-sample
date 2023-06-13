// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.application") version ("8.0.2") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.21") apply false
}
true // Needed to make the Suppress annotation work for the plugins block
