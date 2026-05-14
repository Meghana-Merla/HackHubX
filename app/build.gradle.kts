plugins {
    alias(libs.plugins.android.application)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.meghana.hackhubx"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.meghana.hackhubx"

        minSdk = 24

        targetSdk = 36

        versionCode = 1

        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),

                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_17

        targetCompatibility =
            JavaVersion.VERSION_17
    }

    buildFeatures {

        viewBinding = true
    }
}

dependencies {

    implementation(
        "com.google.firebase:firebase-auth-ktx:23.1.0"
    )

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.appcompat)

    implementation(libs.material)

    implementation(libs.androidx.activity)

    implementation(libs.androidx.constraintlayout)

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3"
    )

    implementation(
        "androidx.lifecycle:lifecycle-livedata-ktx:2.8.3"
    )

    implementation(
        "androidx.navigation:navigation-fragment-ktx:2.8.0"
    )

    implementation(
        "androidx.navigation:navigation-ui-ktx:2.8.0"
    )

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(
        libs.androidx.espresso.core
    )
}