plugins {
    alias(libs.plugins.android.application)
}

android {

    namespace = "com.reminder.app"

    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {

        applicationId = "com.reminder.app"

        minSdk = 26
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {

        release {

            optimization {
                enable = false
            }

        }

    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }

}

dependencies {

    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // ==========================
    // Volley
    // ==========================
    implementation("com.android.volley:volley:1.2.1")

    // ==========================
    // Google Location Services
    // ==========================
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // ==========================
    // GridLayout
    // ==========================
    implementation("androidx.gridlayout:gridlayout:1.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)

}