package de.l.oklab.gentri.mongo

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import de.l.oklab.gentri.mongo.model.District
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.aggregation.stages.Projection
import java.io.File

const val outputPath = "/home/joerg/Schreibtisch/geojsons/"

fun main() {
    val datastore = Morphia.createDatastore(MongoClients.create(ConnectionString("mongodb://admin:admin@localhost:27017")), "joerg")
    datastore.mapper.mapPackage("de.l.oklab.gentri.mongo.model")
    storeGeojsonFile(datastore)
}

fun storeGeojsonFile(datastore: Datastore) {
    val mongoClient = MongoClients.create()
    val database = mongoClient.getDatabase("joerg")
    val objectMapper = ObjectMapper()
    for (year in 2020 until 2021) {
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

fun getDistrictNames(datastore: Datastore): List<String> = datastore.aggregate(District::class.java)
        .project(Projection.project().include("properties.Name")).execute(String::class.java).toList()
