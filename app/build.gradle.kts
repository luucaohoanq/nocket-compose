import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
    id("com.google.devtools.ksp")
    //id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.nocket"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nocket"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        manifestPlaceholders["appAuthRedirectScheme"] = "com.example.nocket"

        // Read from local.properties
        val properties = Properties()
        if (rootProject.file("local.properties").exists()) {
            properties.load(project.rootProject.file("local.properties").inputStream())
        } else {
            throw RuntimeException("local.properties file not found")
        }

        val error = "variable not found in local.properties"

        // Define BuildConfig fields without revealing fallback values
        buildConfigField("String", "APPWRITE_VERSION",
        "\"${properties.getProperty("appwrite.version") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "APPWRITE_PROJECT_ID",
        "\"${properties.getProperty("appwrite.project.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "APPWRITE_PROJECT_NAME",
        "\"${properties.getProperty("appwrite.project.name") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "APPWRITE_PUBLIC_ENDPOINT",
        "\"${properties.getProperty("appwrite.endpoint") ?: throw RuntimeException(error)}\"")

        buildConfigField("String", "DATABASE_ID",
            "\"${properties.getProperty("appwrite.database.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "SETTINGS_COLLECTION_ID",
            "\"${properties.getProperty("appwrite.settings.collection.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "NOTIFICATIONS_COLLECTION_ID",
            "\"${properties.getProperty("appwrite.notifications.collection.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "MESSAGES_COLLECTION_ID",
            "\"${properties.getProperty("appwrite.messages.collection.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "FRIENDSHIPS_COLLECTION_ID",
            "\"${properties.getProperty("appwrite.friendships.collection.id") ?: throw RuntimeException(error)}\"")
        buildConfigField("String", "POSTS_COLLECTION_ID",
            "\"${properties.getProperty("appwrite.posts.collection.id") ?: throw RuntimeException(error)}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation (libs.play.services.auth) // hoặc bản mới nhất
    implementation(libs.kotlinx.coroutines.play.services)

    // compose platform
    implementation(platform(libs.androidx.compose.bom))

    // ui, preview & material
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.logging.interceptor)
    implementation(libs.androidx.work.runtime.ktx)

    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.composeIcons.fontAwesome)

    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)

    implementation(libs.okhttp)

    implementation(libs.gson)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.okhttp.urlconnection)

    // accompanist
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.pager)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.coil.compose)

    implementation(libs.coil.network.okhttp)

    // QR Code Scanning
    implementation(libs.androidx.camera.view)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view.v131)
    implementation (libs.barcode.scanning)

    // appwrite
    implementation(libs.appwrite)

    // splashscreen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.appauth)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.animation)
    implementation("com.stripe:stripe-android:20.42.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // debug libraries
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}