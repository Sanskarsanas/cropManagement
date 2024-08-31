plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
//    id("com.android.application")
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.crop_management"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.crop_management"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.ads.lite)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //dependancy for splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //dependancy for ModalBottomSheet
    implementation("androidx.compose.material:material:1.6.8")

    //dependancy for googleSign In
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    //dependancy for lottie animation
    implementation("com.airbnb.android:lottie-compose:6.4.1")


    //dependancy for Location Accessing
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    implementation ("com.google.android.gms:play-services-auth:20.2.0")
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    //dependancy for firebase
    implementation("com.google.firebase:firebase-bom:33.1.2")
    implementation("com.google.firebase:firebase-auth:23.0.0")


    implementation("com.google.firebase:firebase-bom:33.1.2")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    //dependancy for image Picker and coil for image retreiving efficiently asnd fast
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("io.coil-kt:coil-compose:2.7.0")

    //dependancy for search bar
    implementation("androidx.compose.material3:material3:1.1.2")

    //Dependency for retrieving the data from Firebase
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    //dependancy for glide image uploading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.coil-kt:coil:2.7.0")
    implementation(kotlin("script-runtime"))

    //dependancy for retrofit to weather forecast api
    val retrogfitversion="2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrogfitversion")
    implementation("com.squareup.retrofit2:converter-gson:$retrogfitversion")

        //dependancy for live data weather forecast
    implementation("androidx.compose.runtime:runtime:1.6.8")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.6.8")

    //dependancies for google authentiation
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))


    //dependancies for disease detection application
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    // CameraX
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")
    // ML Kit or TensorFlow Lite
    implementation("org.tensorflow:tensorflow-lite:2.12.0")

    //dependancy for lottie animation
    val lottieVersion="6.5.0"
    implementation("com.airbnb.android:lottie:${lottieVersion}")

    //dependancy for auto image Slider
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    //dependancy for Notification Sender
    implementation("androidx.work:work-runtime:2.3.1")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.activity:activity-compose:1.5.0")
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

    //dependency for live farm product price
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //dependency for FCM Firebase Cloud Notification
    implementation("com.google.gms:google-services:4.3.14")
    implementation("com.google.firebase:firebase-bom:32.1.0") // Use the latest BOM version
    implementation("com.google.firebase:firebase-messaging")

    //dependency for Plant Disease Detector API
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.serialization)
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation(libs.androidx.activity.compose.v172)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("io.ktor:ktor-client-core:2.2.4")
    implementation("io.ktor:ktor-client-cio:2.2.4")
    implementation("io.ktor:ktor-client-serialization:2.2.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //Dependancy for WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")

    //Dependencies for authentication
    implementation(libs.com.google.android.gms.play.services.auth2)
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation(libs.com.google.android.gms.play.services.auth2)
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.firebaseui:firebase-ui-auth:8.0.0") // Check for the latest version

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.activity:activity-ktx:1.4.0")
}