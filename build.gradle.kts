// Top-level build file where you can add configuration options common to all sub-projects/modules.
//@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    //FIXME Find a way to reuse versions from the gradle catalog while allowing
    // the example project to be built in a standalone manner
    id("com.android.application") version ("8.3.1") apply false
    id("org.jetbrains.kotlin.android") version ("1.9.22") apply false
}
//true // Needed to make the Suppress annotation work for the plugins block
