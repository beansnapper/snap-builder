package io.beansnapper.builder


import com.squareup.kotlinpoet.*
import io.beansnapper.annotations.SnapBuilder
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.*

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
        System.err.println("GeneratedDir = $kaptKotlinGeneratedDir")

        for (element in annotatedElements) {
            System.err.println("Annotated Element=", )
            if (element.kind != ElementKind.CLASS) continue
            if (element is TypeElement) {
                val packageName = findPackageName(element)
                val className = element.simpleName


            }
        }
        return true
    }

    private fun findPackageName(typeElement: TypeElement) :String {
        val enclosing = typeElement.enclosingElement
        if (enclosing is PackageElement) {
            return enclosing.qualifiedName.toString()
        } else throw Exception("currently only handle top level packages")
    }

    fun generateBuilder(typeElement: TypeElement) : FileSpec {
        val packageName  = findPackageName(typeElement)
        val name = typeElement.simpleName.toString()
        val target = ClassName("", name)

        val genName = name + "Builder"
        val genClassName = ClassName(packageName, genName)



        return FileSpec.builder(packageName, genName)
            .addType(
                TypeSpec.classBuilder(genName)
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                    .addParameter("name", String::class)
                    .build())
                .addProperty(
                    PropertySpec.builder("name", String::class)
                    .initializer("name")
                    .build())
                .addFunction(genBuildMethod(typeElement, target))
                .build())
            .build()

    }

    fun genBuildMethod(typeElement: TypeElement, target: ClassName) : FunSpec {
        val funBuilder = FunSpec.builder("build")
            .returns(target)
            .addStatement("return %T(", target)

        for (element in typeElement.enclosedElements) {
            println("Element=$element")
        }

        return funBuilder.build()
    }


}
