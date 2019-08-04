package de.l.oklab.gentri.mongo

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.MongoClient
import com.mongodb.client.MongoClients
import de.l.oklab.gentri.mongo.model.District
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.aggregation.Projection
import java.io.File

const val outputPath = "/home/joerg/Schreibtisch/geojsons/"

fun main() {
    val morphia = Morphia()
    morphia.mapPackage("de.l.oklab.gentri.mongo.model");
    val datastore = morphia.createDatastore(MongoClient(), "joerg")
    storeGeojsonFile(datastore)

}

fun storeGeojsonFile(datastore: Datastore) {
    val mongoClient = MongoClients.create()
    val database = mongoClient.getDatabase("joerg")
    val objectMapper = ObjectMapper()
    for (year in 2014 until 2020) {
        for (districtName in getDistrictNames(datastore)) {
            val collection = database.getCollection("""buildings-$year-$districtName""")
            val features = collection.find().asIterable().map { it.remove("_id"); it.toJson() }
            val content = featureCollection(features)
            val root = objectMapper.readTree(content)
            val normalizedDistrictName = normalizeName(districtName)
            val file = File("""$outputPath/$normalizedDistrictName-$year-buildings.geojson""")
            objectMapper.writeValue(file, root)
            println(""""${file.absolutePath} written""")
        }
    }
}

fun normalizeName(name: String): String = name
        .replace("ä", "ae")
        .replace("ö", "oe")
        .replace("ü", "ue")
        .replace("ß", "ss")

fun featureCollection(features: List<String>): String {
    return """{
      "type": "FeatureCollection",
      "features": [
         ${features.joinToString(",")}
      ]
    }"""
}

fun getDistrictNames(datastore: Datastore): List<String>  = datastore.createAggregation(District::class.java)
        .project(Projection.projection("properties.Name")).aggregate(District::class.java)
        .asSequence().asIterable().map { it.getName() }.filterNotNull()
