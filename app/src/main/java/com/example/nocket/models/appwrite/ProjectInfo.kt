package io.appwrite.starterkit.data.models

import androidx.annotation.RestrictTo

/**
 * A data model for holding appwrite project information.
 */
data class ProjectInfo(
    val endpoint: String,
    val projectId: String,
    val projectName: String,
    val version: String,
)

/**
 * A mock `ProjectInfo` model, just for **previews**.
 */
@RestrictTo(RestrictTo.Scope.TESTS)
internal val mockProjectInfo = ProjectInfo(
    endpoint = "https://mock.api/v1",
    projectId = "sample-project-id",
    projectName = "AppwriteStarter",
    version = "1.6.0",
)