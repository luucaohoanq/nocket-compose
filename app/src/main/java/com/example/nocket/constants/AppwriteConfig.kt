package com.example.nocket.constants

import com.example.nocket.BuildConfig

/**
 * Appwrite integration constants.
 *
 * This object holds values related to the Appwrite server setup,
 * including version, project details, and API endpoint.
 */
object AppwriteConfig {
    /**
     * Appwrite Server version.
     */
    const val APPWRITE_VERSION = BuildConfig.APPWRITE_VERSION

    /**
     * Appwrite project id.
     */
    const val APPWRITE_PROJECT_ID = BuildConfig.APPWRITE_PROJECT_ID

    /**
     * Appwrite project name.
     */
    const val APPWRITE_PROJECT_NAME = BuildConfig.APPWRITE_PROJECT_NAME

    /**
     * Appwrite server endpoint url.
     */
    const val APPWRITE_PUBLIC_ENDPOINT = BuildConfig.APPWRITE_PUBLIC_ENDPOINT
}
