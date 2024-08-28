package com.github.obask.buildoutputreproplugin

import com.intellij.build.output.BuildOutputParser
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId
import com.intellij.openapi.externalSystem.service.execution.ExternalSystemOutputParserProvider


class GradleOutputParserProvider : ExternalSystemOutputParserProvider {
    override fun getExternalSystemId(): ProjectSystemId = id

    override fun getBuildOutputParsers(taskId: ExternalSystemTaskId): List<BuildOutputParser> {
//        return emptyList()
        return listOf(BuildOutputParserWrapper())
    }
}

private val id = ProjectSystemId("GRADLE")
