/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
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
