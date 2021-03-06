-keep class com.support.** { *; }

#Retrofit Start
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
#if using @Header or @Query
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#GSON
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keepclassmembernames interface * {
    @retrofit.http.* <methods>;
}
-keep interface retrofit.** { *;}
-keep interface com.squareup.** { *; }
-dontwarn rx.**
-dontwarn retrofit.**

#retrofit models
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.support.retrofit.model.** { *; }

-keepattributes InnerClasses
-keepattributes Deprecated
-keepattributes EnclosingMethod
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
#Retrofit End

-keep public class * extends java.lang.Exception

#Fabric
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#Glide
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.**

#Glide Module
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

#Support Library
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#DataBinding
-dontwarn android.databinding.**
-keep class <com.bamberg>.databinding.** {
    <fields>;
    <methods>;
}
