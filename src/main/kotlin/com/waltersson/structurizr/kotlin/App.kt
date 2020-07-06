package com.waltersson.structurizr.kotlin

import com.structurizr.api.StructurizrClient
import com.structurizr.model.CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy

const val WORKSPACE_ID: Long = 123

/**
 * An example of using the DSL. Won't actually output anything or communicate with the
 * structurizr website.
 */
fun main(args: Array<String>) {
    val s = structurizr {
        workspace("Getting Started", "This is a model of my software system.") {
            impliedRelationshipsStrategy<CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy>()
            val softwareSystem = softwareSystem("Software System", "My software system.") {
                val container = addContainer("", "", "")

            }
            val app = container(softwareSystem, "name", "description", "Technology") {
                tags += "Some tag"
            }

            val user = person("User", "A user of my software system.") {
                uses(softwareSystem, "Uses")
                uses(app, "Uses on phone")
            }


            views {
                systemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.") {
                    addAllSoftwareSystems()
                    addAllPeople()
                }
            }
        }
    }
    val client = StructurizrClient()
    client.putWorkspace(WORKSPACE_ID, s.evaluate())
}



