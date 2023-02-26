package de.l.oklab.gentri.buildings

import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader
import java.io.File

fun main() {
    val osmFile = File("/media/daten/sachsen-200101.osm.pbf")
    val reader = PbfReader(osmFile, 5)

    val nodeIds: MutableList<Long> = mutableListOf()
    val coords: MutableMap<Long, Coord> = mutableMapOf()
    val tags: MutableList<Map<String, String>> = mutableListOf()
    val ways: MutableList<List<Coord>> = mutableListOf()

    reader.setSink(NodeSink(nodeIds, coords))
    reader.run()

    reader.setSink(WaySink(nodeIds, coords, ways, tags))
    reader.run()

    val fileContent = featureCollection(tags, ways)
    File("/media/daten/sachsen-200101.geojson").writeText(fileContent)
}

fun featureCollection(propertiesList: List<Map<String, String>>, coordsList: List<List<Coord>>): String {
    val range = 0 until propertiesList.size
    return """{
  "type": "FeatureCollection",
  "features": [
     ${range.map { feature(propertiesList.get(it), coordsList.get(it)) }.joinToString(",\n")}
  ]
}"""
}

fun feature(props: Map<String, String>, coords: List<Coord>): String {
    return """
        {
          "type": "Feature",
          "properties": {
            ${(props.map {(k, v) -> "\"${replaceQuotes(k)}\": \"${replaceQuotes(v)}\""}).joinToString(",")}
          },
          "geometry": {
            "type": "Polygon",
            "coordinates": [[ 
                ${(sanatizedCoords(coords).map { coord -> "[" + coord.lon + "," + coord.lat + "]" }).joinToString(",")} 
            ]]
          }
        }"""
}

fun replaceQuotes(str: String): String {
    return str.replace("\"", "'")
}

fun sanatizedCoords(way: List<Coord>): List<Coord> {
    if (!way.isEmpty()) {
        val first = way.get(0)
        if (way.size < 4 || first != way.get(way.size - 1)) {
            val newWay = ArrayList<Coord>(way)
            newWay.add(first)
            return newWay
        }
    }
    return way
}