package de.l.oklab.gentri.buildings

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer
import org.openstreetmap.osmosis.core.domain.v0_6.Entity
import org.openstreetmap.osmosis.core.domain.v0_6.Way
import org.openstreetmap.osmosis.core.task.v0_6.Sink

class WaySink(
    private val nodeIds: List<Long>,
    private val coords: Map<Long, Coord>,
    private val ways: MutableList<List<Coord>>,
    private val tags: MutableList<Map<String, String>>
) : Sink {

    override fun process(entityContainer: EntityContainer) {
        // if (ways.size > 10) {
        //    return
        // }
        val entity = entityContainer.getEntity()
        if (entity is Way) {
            val buildingTag = entity.tags.firstOrNull { it.key.equals("building") }
            if (buildingTag != null) {
                val wayOutOfBounds = entity.wayNodes.firstOrNull { wayNode -> nodeIds.contains(wayNode.nodeId) }
                if (wayOutOfBounds != null) {
                    val wayCoordsList = entity.wayNodes.map { coords.get(it.nodeId) }.filterNotNull()
                    ways.add(wayCoordsList)
                    tags.add(toMap(entity))
                    if (ways.size % 25 == 0) {
                        println("Found " + ways.size.toString() + " ways in bounds")
                    }
                }
            }
        }
    }

    private fun toMap(entity: Entity): Map<String, String> {
        val map = HashMap<String, String>()
        map.put("id", entity.id.toString())
        map.put("version", entity.version.toString())
        map.put("changeSetId", entity.changesetId.toString())
        map.put("timestamp", entity.timestamp.time.toString())
        entity.tags.forEach { tag -> map.put(tag.key, tag.value) }
        return map
    }

    override fun initialize(prop: MutableMap<String, Any>?) {}
    override fun complete() {}
    override fun close() {}
}