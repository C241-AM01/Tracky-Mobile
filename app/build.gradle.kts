plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id ("kotlin-parcelize")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.megalogic.tracky"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.megalogic.tracky"
        minSdk = 28
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
        viewBinding = true
    }


}

dependencies {
    // AndroidX Core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.core.splashscreen)

    // AndroidX Lifecycle libraries
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // AndroidX Navigation libraries
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Material Design components
    implementation(libs.material)

    // Firebase libraries
    implementation(libs.firebase.auth)

    // Google Play Services libraries
    implementation(libs.play.services.maps)
    implementation(libs.play.services.vision.common)
    implementation(libs.play.services.vision)
    // (Versi spesifik untuk Play Services Maps)
    implementation(libs.play.services.maps.v1820)

    // Glide for image loading
    implementation(libs.glide)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    // Car UI Library
    implementation(libs.car.ui.lib)

    // Annotation processors
    annotationProcessor(libs.compiler)

    // Unit Testing libraries
    testImplementation(libs.junit)

    // Android Testing libraries
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}



