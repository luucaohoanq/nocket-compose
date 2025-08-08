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

object DBConfig {

    /**
     * Appwrite database id.
     */
    const val DATABASE_ID = BuildConfig.DATABASE_ID

    /**
     * Appwrite collection id for settings.
     */
    const val SETTINGS_COLLECTION_ID = BuildConfig.SETTINGS_COLLECTION_ID

    /**
     * Appwrite collection id for settings.
     */
    const val NOTIFICATIONS_COLLECTION_ID = BuildConfig.NOTIFICATIONS_COLLECTION_ID

    /**
     * Appwrite collection id for settings.
     */
    const val MESSAGES_COLLECTION_ID = BuildConfig.MESSAGES_COLLECTION_ID

    /**
     * Appwrite collection id for settings.
     */
    const val FRIENDSHIPS_COLLECTION_ID = BuildConfig.FRIENDSHIPS_COLLECTION_ID

    /**
     * Appwrite collection id for posts.
     */
    const val POSTS_COLLECTION_ID = BuildConfig.POSTS_COLLECTION_ID


}