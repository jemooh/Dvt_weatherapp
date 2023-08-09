
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tech.dvtweatherapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tech.dvtweatherapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            versionNameSuffix = " - debug"
        }

        getByName("release") {
            isMinifyEnabled = true
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation(enforcedPlatform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(enforcedPlatform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Koin MVVM
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.1.2")
    // Koin testing tools
    testImplementation("io.insert-koin:koin-test:3.1.2")
    // Needed JUnit version
    testImplementation("io.insert-koin:koin-test-junit4:3.1.2")

    // Room
    implementation("androidx.room:room-runtime:2.4.2")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.2")

    implementation("com.jakewharton.timber:timber:5.0.0")

    // Location service
    implementation("com.google.android.gms:play-services-location:17.0.0")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.1.1")
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.foundation:foundation:1.1.1")
    implementation("androidx.compose.foundation:foundation-layout:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation("com.google.android.material:compose-theme-adapter:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")

    // navigation
    implementation("androidx.navigation:navigation-fragment:2.5.0-alpha04")
    implementation("androidx.navigation:navigation-ui:2.5.0-alpha04")
    implementation("androidx.navigation:navigation-compose:2.4.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("com.google.accompanist:accompanist-drawablepainter:0.23.1")

    // Google maps
    implementation("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.4.0")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    // implementation")com.google.maps.android:maps-v3-ktx:2.2.0")
    implementation("androidx.fragment:fragment:1.4.1")

    implementation("com.google.maps.android:maps-ktx:3.4.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.4.0")
    implementation("com.google.android.gms:play-services-maps:18.0.2")

    // Testing
    // leak canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    // UI Test - Compose
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.0-alpha01")
    // Needed for createComposeRule, but not createAndroidComposeRule:
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-alpha01")

    // spek
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.18")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.18")

    // spek requires kotlin-reflect, omit when already in classpath
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")

    testImplementation("io.insert-koin:koin-test-junit5:3.1.2")
    testImplementation("io.insert-koin:koin-test:3.1.2")

    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("io.mockk:mockk-agent-jvm:1.12.0") {
        because(
            "This dependency resolves the NoClassDefFoundError when using spek " +
                    "https://github.com/mockk/mockk/issues/605," +
                    "https://github.com/spekframework/spek/issues/968"
        )
    }

    // JUnit5 dependencies
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.6.0") {
        because(
            "This allows us to run JUnit4 tests as well as Spek tests (JUnit5) from the command line." +
                    "Found in: https://github.com/spekframework/spek/issues/232#issuecomment-610732158"
        )
    }

    // Mock Web Server
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.2")

    // RoomDB Test
    testImplementation("androidx.room:room-testing:2.4.0-alpha04")

    testImplementation("androidx.test:core:1.4.1-alpha01")

    testImplementation("androidx.test:runner:1.4.0")

    // Truth assertion lib
    testImplementation("com.google.truth:truth:1.1.3")

    // Coroutines test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    // For InstantTaskExecutorRule
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // test kotlinx.coroutines Flow
    testImplementation("app.cash.turbine:turbine:0.6.0")

    // Roboelectric - Testing Room
    testImplementation("org.robolectric:robolectric:4.6.1")

    testImplementation("app.cash.turbine:turbine:0.7.0")
}