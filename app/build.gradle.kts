plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gardeningskillsapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gardeningskillsapp"
        minSdk = 24
        targetSdk = 36
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("renameGardeningSkills") {
    doLast {
        val resDir = file("src/main/res/drawable")
        val oldFile = File(resDir, "gardeningSkills.webp")
        val newFile = File(resDir, "gardening_skills.webp")
        if (oldFile.exists()) {
            if (oldFile.renameTo(newFile)) {
                println("Successfully renamed gardeningSkills.webp to gardening_skills.webp")
            } else {
                throw GradleException("Failed to rename gardeningSkills.webp")
            }
        }
    }
}
