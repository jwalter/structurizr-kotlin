package com.waltersson.structurizr.kotlin

import com.structurizr.model.CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy

/**
 * An example of using the DSL. Won't actually output anything or communicate with the
 * structurizr website.
 */
fun main(args: Array<String>) {
    structurizr {
        val workspace = workspace("Getting Started", "This is a model of my software system.") {
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
}



