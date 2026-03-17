# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

############################################
# KEEP ATTRIBUTES
############################################

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

############################################
# KOTLIN
############################################

-keep class kotlin.Metadata { *; }

############################################
# RETROFIT
############################################

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

############################################
# OKHTTP
############################################

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

############################################
# GSON
############################################

-keep class com.google.gson.** { *; }
-keepattributes Signature

# manter campos usados em JSON
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# manter construtores
-keepclassmembers class * {
    public <init>(...);
}

############################################
# MODELS DO APP
############################################

-keep class br.com.fiap.esg_ecoal.** { *; }

############################################
# MPAndroidChart
############################################

-keep class com.github.mikephil.charting.** { *; }

############################################
# DATASTORE
############################################

-keep class androidx.datastore.** { *; }

############################################
# COMPOSE
############################################

-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

############################################
# NAVIGATION
############################################

-keep class androidx.navigation.** { *; }

############################################
# COROUTINES
############################################

-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }