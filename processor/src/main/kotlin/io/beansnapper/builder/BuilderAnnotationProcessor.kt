package io.beansnapper.builder


import com.squareup.kotlinpoet.*
import io.beansnapper.annotations.SnapBuilder
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic.Kind.ERROR

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("io.beansnapper.annotations.SnapBuilder")
@SupportedOptions("kapt.kotlin.generated")
class BuilderAnnotationProcessor : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        System.err.println(">>>>>>>>>>>>>>>>>>>>> builder processor <<<<<<<<<<<<<<<<<<<<<<")
        val annotatedElements = roundEnv.getElementsAnnotatedWith(SnapBuilder::class.java)
        if (annotatedElements.isEmpty()) return false

        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"] ?: run {
            processingEnv.messager.printMessage(ERROR, "Can't find the target directory for generated Kotlin files.")
            return false
        }
        val genDir = File(kaptKotlinGeneratedDir)
        genDir.mkdirs()
        System.err.println("GeneratedDir = $kaptKotlinGeneratedDir")

        for (element in annotatedElements) {
            System.err.println("Annotated Element=$element")
            if (element.kind != ElementKind.CLASS) continue
            if (element is TypeElement) {
                val fileSpec = generateBuilder(element)
                fileSpec.writeTo(genDir)
            }
        }
        return true
    }

    private fun findPackageName(typeElement: TypeElement): String {
        val enclosing = typeElement.enclosingElement
        if (enclosing is PackageElement) {
            return enclosing.qualifiedName.toString()
        } else throw Exception("currently only handle top level packages")
    }

    private fun generateBuilder(typeElement: TypeElement): FileSpec {
        val packageName = findPackageName(typeElement)
        System.err.println("PackageName=$packageName")
        val name = typeElement.simpleName.toString()
        val target = ClassName(packageName, name)
        System.err.println("target=$target")

        val genName = name + "Builder"

        System.err.println("Fields: ")
        val fields = typeElement.enclosedElements
            .filter { it.kind == ElementKind.FIELD }
            .map { it as VariableElement }
            .toList()

        val params = fields.map {
            ParameterSpec.builder(it.simpleName.toString(), it.asType().asTypeName()).build()
        }.toList()

        val properties = fields.map {
            PropertySpec.builder(it.simpleName.toString(), it.asType().asTypeName())
                .mutable()
                .initializer(it.simpleName.toString())
                .build()
        }.toList()

        return FileSpec.builder(packageName, genName)
            .addType(
                TypeSpec.classBuilder(genName)
                    .primaryConstructor(
                        FunSpec.constructorBuilder().addParameters(params).build()
                    )
                    .addProperties(properties)
                    .addFunction(genBuildMethod(target, fields))
                    .build()
            )
            .build()
    }

    private fun genBuildMethod(target: ClassName, fields: List<VariableElement>): FunSpec {

        val buildIt = StringBuilder("return ")
            .append(target.simpleName)
            .append("(")
        fields.forEach {
            buildIt.append("${it.simpleName.toString()},")
        }
        buildIt.append(")")

        return FunSpec.builder("build")
            .returns(target)
            .addStatement(buildIt.toString())
            .build()

    }


}
