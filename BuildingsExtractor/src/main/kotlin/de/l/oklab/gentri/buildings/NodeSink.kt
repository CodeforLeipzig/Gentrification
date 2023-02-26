package de.l.oklab.gentri.buildings

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Node
import org.openstreetmap.osmosis.core.task.v0_6.Sink

class NodeSink(
    private val nodeIds: MutableList<Long>,
    private val coords: MutableMap<Long, Coord>
) : Sink {

    override fun process(entityContainer: EntityContainer) {
        val entity = entityContainer.entity
        if (entity is Node) {
            if (nodeIsInBounds(entity)) {
                nodeIds.add(entity.id)
                coords[entity.id] = Coord(entity.latitude, entity.longitude)
                if (nodeIds.size % 25 == 0) {
                    println("Found " + nodeIds.size.toString() + " nodes in bounds.")
                }
            }
        }
    }

    private fun nodeIsInBounds(node: Node): Boolean {
        return !latLonNotInBounds(node.latitude, node.longitude)
    }

    private fun latLonNotInBounds(latitude: Double, longitude: Double): Boolean {
        return latitudeNotInBounds(latitude) || longitudeNotInBounds(longitude)
    }

    private fun latitudeNotInBounds(latitude: Double): Boolean {
        return latitude < 51.23817 || latitude > 51.448114
    }

    private fun longitudeNotInBounds(longitude: Double): Boolean {
        return longitude < 12.236652 || longitude > 12.542441
    }

    override fun initialize(prop: MutableMap<String, Any>?) {}
    override fun complete() {}
    override fun close() {}
}