package de.l.oklab.gentri.buildings

import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val osmFile = File("D:/sachsen-latest.osm.pbf")
    val reader = PbfReader(osmFile, 5)

    val nodeIds: MutableList<Long> = mutableListOf()
    val coords: MutableMap<Long, Coord> = mutableMapOf()
    val ways: MutableList<List<Coord>> = mutableListOf()

    reader.setSink(NodeSink(nodeIds, coords))
    reader.run()

    reader.setSink(WaySink(nodeIds, coords, ways))
    reader.run()

    val featureStart = """
        {
          "type": "Feature",
          "properties": {},
          "geometry": {
            "type": "Polygon",
            "coordinates": [[
    """.trimIndent()
    val featureEnd = "]]}}"

    val geoJsonStart = """
        {
          "type": "FeatureCollection",
          "features": [
    """.trimIndent()
    val geoJsonEnd = "]}"

    val fileContent = geoJsonStart + ways.map { way ->
        featureStart + way.map { coord ->
            "[" + coord.lon + "," + coord.lat + "]"
        }.joinToString(", ") + featureEnd
    }.joinToString(", ") + geoJsonEnd
    File("D:/sachsen-latest-buildings.geojson").writeText(fileContent)
}