package com.easybug.plugint

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension
import com.easybug.plugint.PreClass

class AopLogPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension.class)
        android.registerTransform(new PreClass(project))

    }
}