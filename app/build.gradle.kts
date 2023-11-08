plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.crxapplications.morsy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crxapplications.morsy"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val androidMaterial = "1.10.0"
    val roomVersion = "2.6.0"
    val coreKtxVersion = "1.12.0"
    val kotlinCoroutinesVersion = "1.7.3"
    val lifecycleRuntimeKtxVersion = "2.6.2"
    val activityComposeVersion = "1.8.0"
    val composeBomVersion = "2023.03.00"
    val navVersion = "2.7.4"
    val firebaseBomVersion = "32.2.0"
    val hiltVersion = "2.45"
    val hiltNavigationComposeVersion = "1.0.0"
    val retrofitVersion = "2.9.0"
    val moshiVersion = "1.14.0"
    val viewModelComposeVersion = "2.6.2"

    val jUnitVersion = "4.13.2"
    val extJUnitVersion = "1.1.5"
    val espressoCoreVersion = "3.5.1"
    val mockitoVersion = "4.2.0"
    val mockitoKotlinVersion = "4.0.0"
    val androidXTestVersion = "1.5.0"
    val kotlinxCoroutinesTestVersion = "1.7.3"


    implementation("com.google.android.material:material:$androidMaterial")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelComposeVersion")

    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    androidTestImplementation("androidx.test.ext:junit:$extJUnitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("androidx.test:core:$androidXTestVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesTestVersion")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}