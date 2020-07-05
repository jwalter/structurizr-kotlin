package com.waltersson.structurizr.kotlin

import com.structurizr.Workspace
import com.structurizr.model.*
import com.structurizr.view.SystemContextView
import com.structurizr.view.View
import com.structurizr.view.ViewSet

@DslMarker
annotation class StructurizrDslMarker

fun structurizr(init: StructurizrDsl.() -> Unit) = StructurizrDsl(init)

@StructurizrDslMarker
open class StructurizrDsl internal constructor(private val init: StructurizrDsl.() -> Unit) {

    fun workspace(name: String, description: String, block: WorkspaceAdapter.() -> Unit): WorkspaceAdapter {
        val w = WorkspaceAdapter(name, description)
        w.block()
        return w
    }
}

@StructurizrDslMarker
class WorkspaceAdapter(name: String, description: String) {
    private val adaptee = Workspace(name, description)
    val model: Model = adaptee.model
    val views: ViewSet = adaptee.views

    inline fun <reified T : ImpliedRelationshipsStrategy> impliedRelationshipsStrategy() {
        model.impliedRelationshipsStrategy = T::class.java.getDeclaredConstructor().newInstance()
    }

    fun enterprise(name: String, init: Enterprise.() -> Unit = {}): Enterprise {
        val enterprise = Enterprise(name)
        model.enterprise = enterprise
        enterprise.init()
        return enterprise
    }

    fun softwareSystem(name: String, description: String, block: SoftwareSystem.() -> Unit = {}): SoftwareSystem {
        val softwareSystem = model.addSoftwareSystem(name, description)
        softwareSystem.block()
        return softwareSystem
    }

    fun person(name: String, description: String, block: PersonAdapter.() -> Unit): PersonAdapter {
        val p = PersonAdapter(model, name, description)
        p.block()
        return p
    }

    fun views(init: ViewSetAdapter.() -> Unit): ViewSetAdapter {
        val v = ViewSetAdapter(this)
        v.init()
        return v
    }


}

@StructurizrDslMarker
class PersonAdapter(model: Model, name: String, description: String? = null) {
    private val adaptee = model.addPerson(name, description)

    fun uses(softwareSystem: SoftwareSystem, description: String) {
        adaptee.uses(softwareSystem, description)
    }
}

@StructurizrDslMarker
class ViewSetAdapter(workspace: WorkspaceAdapter) {
    private val adaptee = workspace.views

    fun systemContextView(softwareSystem: SoftwareSystem, key: String, description: String, block: SystemContextView.() -> Unit): SystemContextView {
        val view = adaptee.createSystemContextView(softwareSystem, key, description)
        view.block()
        return view
    }

}