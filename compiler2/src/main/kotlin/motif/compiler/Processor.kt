/*
 * Copyright (c) 2018-2019 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package motif.compiler

import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.SetMultimap
import com.squareup.javapoet.JavaFile
import motif.Scope
import motif.ast.compiler.CompilerClass
import motif.core.ResolvedGraph
import motif.errormessage.ErrorMessage
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.tools.Diagnostic

class Processor : BasicAnnotationProcessor() {

    lateinit var graph: ResolvedGraph

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun initSteps(): Iterable<ProcessingStep> {
        return listOf<ProcessingStep>(Step())
    }

    private inner class Step : ProcessingStep {

        override fun annotations(): Set<Class<out Annotation>> {
            return setOf(motif.Scope::class.java)
        }

        override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): Set<Element> {
            val scopeElements = elementsByAnnotation[Scope::class.java]
            val initialScopeClasses = scopeElements
                    .map { CompilerClass(processingEnv, it.asType() as DeclaredType) }
            if (initialScopeClasses.isEmpty()) {
                return emptySet()
            }

            this@Processor.graph = ResolvedGraph.create(initialScopeClasses)

            if (graph.errors.isNotEmpty()) {
                val errorMessage = ErrorMessage.toString(graph)
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, errorMessage)
                return emptySet()
            }

            val scopeImpls = ScopeImpl.create(processingEnv, graph)

            scopeImpls.forEach { scopeImpl ->
                val spec = scopeImpl.spec ?: return@forEach
                JavaFile.builder(scopeImpl.packageName, spec).build().writeTo(processingEnv.filer)
            }

            return emptySet()
        }
    }
}
