@file:Suppress("UnstableApiUsage")

package com.github.obask.buildoutputreproplugin

import com.intellij.build.events.BuildEvent
import com.intellij.build.events.BuildIssueEvent
import com.intellij.build.events.MessageEvent
import com.intellij.build.events.impl.FileMessageEventImpl
import com.intellij.build.issue.BuildIssue
import com.intellij.build.issue.BuildIssueQuickFix
import com.intellij.build.output.BuildOutputInstantReader
import com.intellij.build.output.BuildOutputParser
import com.intellij.build.output.KotlincOutputParser
import com.intellij.openapi.project.Project
import com.intellij.pom.Navigatable
import java.util.function.Consumer


class BuildOutputParserWrapper : BuildOutputParser {

  private val myKotlinParser = KotlincOutputParser()

  override fun parse(line: String, reader: BuildOutputInstantReader, messageConsumer: Consumer<in BuildEvent>): Boolean {
    return myKotlinParser.parse(line, reader) { event ->
      val buildIssue = when (event) {
        is FileMessageEventImpl -> {
          object : BuildIssueEvent, MessageEvent by event {
            override fun getIssue(): BuildIssue = object : BuildIssue {
              override val description: String = "REPRO BUILD OUTPUT WRAPPER"
//              override val description: String = event.description.orEmpty()
              override val quickFixes: List<BuildIssueQuickFix> = emptyList()
              override val title: String = event.message
              override fun getNavigatable(project: Project): Navigatable? = null
            }
          }
        }
        else -> event
      }
      messageConsumer.accept(buildIssue)
    }
  }
}