package com.waltersson.structurizr.kotlin

import com.structurizr.Workspace
import com.structurizr.model.CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy
import com.structurizr.model.ImpliedRelationshipsStrategy
import com.structurizr.model.Person
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.SystemContextView

fun main(args: Array<String>) {
  val workspace = workspace("Getting Started", "This is a model of my software system.") {
    impliedRelationshipsStrategy<CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy>()
    val softwareSystem = softwareSystem("Software System", "My software system.")
    person("User", "A user of my software system.") {
      uses(softwareSystem, "Uses")
    }

    systemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.") {
      addAllSoftwareSystems()
      addAllPeople()
    }
  }
}

inline fun <reified T: ImpliedRelationshipsStrategy> Workspace.impliedRelationshipsStrategy() {
  model.impliedRelationshipsStrategy = T::class.java.getDeclaredConstructor().newInstance()
}

fun workspace(name: String, description: String, block: Workspace.() -> Unit): Workspace {
  val w = Workspace(name, description)
  w.block()
  return w
}

fun Workspace.softwareSystem(name: String, description: String, block: SoftwareSystem.() -> Unit = {}): SoftwareSystem {
  val softwareSystem = model.addSoftwareSystem(name, description)
  softwareSystem.block()
  return softwareSystem
}

fun Workspace.person(name: String, description: String, block: Person.() -> Unit): Person {
  val p = model.addPerson(name, description)
  p.block()
  return p
}

fun Workspace.systemContextView(softwareSystem: SoftwareSystem, key: String, description: String, block: SystemContextView.() -> Unit): SystemContextView {
  val view = views.createSystemContextView(softwareSystem, key, description)
  view.block()
  return view
}
