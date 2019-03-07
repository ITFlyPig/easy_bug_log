package com.easybug.plugint

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.wangyuelin.easybug.aop.AopClassAdapter
import org.gradle.api.Project
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.apache.commons.io.FileUtils
import com.wangyuelin.easybug.*
import org.objectweb.asm.Opcodes;

/**
 * PreClass
 * <p>
 * Created by lijiankun24 on 18/7/29.
 */
class PreClass extends Transform {

    Project project

    PreClass(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "PreClass"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                project.logger.error("输入的的文件夹路径：" + directoryInput.file.getAbsolutePath())
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class") && !(name == ("R.class"))
                                && !name.startsWith("R\$") && !(name == ("BuildConfig.class"))) {
//
                            project.logger.error("需要处理的Class文件：" + file.getAbsolutePath())
                            ClassReader reader = new ClassReader(file.bytes)
                            ClassWriter writer = new ClassWriter(reader , ClassWriter.COMPUTE_MAXS)
                            ClassVisitor visitor = new AopClassAdapter(Opcodes.ASM5, writer)
                            reader.accept(visitor, ClassReader.EXPAND_FRAMES)

                            byte[] code = writer.toByteArray()
                            def classPath = file.parentFile.absolutePath + File.separator + name
                            FileOutputStream fos = new FileOutputStream(classPath)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                project.logger.error("拷贝到的文件夹路径：" + dest.getAbsolutePath())

                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->
                project.logger.error("输入的的路径：" + jarInput.file.getAbsolutePath())
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                project.logger.error("拷贝到的路径：" + dest.getAbsolutePath())

                FileUtils.copyFile(jarInput.file, dest)
            }
        }
//        super.transform(transformInvocation)
    }
}