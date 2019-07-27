package de.l.oklab.gentri.buildings

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Way
import org.openstreetmap.osmosis.core.task.v0_6.Sink

class WaySink(
    private val nodeIds: List<Long>,
    private val coords: Map<Long, Coord>,
    private val ways: MutableList<List<Coord>>
) : Sink {

    override fun process(entityContainer: EntityContainer) {
        val entity = entityContainer.getEntity()
        if (entity is Way) {
            val buildingTag = entity.tags.firstOrNull { it.key.equals("building") }
            if (buildingTag != null) {
                val wayOutOfBounds = entity.wayNodes.firstOrNull { wayNode -> nodeIds.contains(wayNode.nodeId) }
                if (wayOutOfBounds != null) {
                    val wayCoordsList = entity.wayNodes.map { coords.get(it.nodeId) }.filterNotNull()
                    ways.add(wayCoordsList)
                    if (ways.size % 25 == 0) {
                        println("Found " + ways.size.toString() + " ways in bounds")
                    }
                }
            }
        }
    }

    override fun initialize(prop: MutableMap<String, Any>?) {}
    override fun complete() {}
    override fun close() {}
}