plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
   // id("kotlin-kapt")         //tolto questo
}

android {
    namespace = "com.example.planeat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.planeat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
/*
    buildFeatures{
        dataBinding = true
    }
*/
}

dependencies {
    // Jetpack Compose
    implementation("androidx.compose.foundation:foundation-layout-android:1.6.2")
    implementation("androidx.compose.material:material:1.6.2")
    implementation("androidx.compose.ui:ui:1.6.2")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")    // To use Kotlin Symbol Processing (KSP)
    implementation("androidx.room:room-runtime:$roomVersion")
    // optional - RxJava2 support for Room: implementation("androidx.room:room-rxjava2:$room_version")
    // optional - RxJava3 support for Room: implementation("androidx.room:room-rxjava3:$room_version")
    // optional - Guava support for Room, including Optional and ListenableFuture: implementation("androidx.room:room-guava:$room_version")
    // optional - Test helpers: testImplementation("androidx.room:room-testing:$room_version")
    // optional - Paging 3 Integration: implementation("androidx.room:room-paging:$room_version")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.core:core-ktx:1.12.0")

    //Builder
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //CoreRoutines
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

}