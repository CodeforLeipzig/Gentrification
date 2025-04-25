package de.l.oklab.gentri.mongo

import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.mongodb.client.MongoClients
import org.bson.Document
import java.nio.file.Paths

fun main() {
    val mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017")
    val database = mongoClient.getDatabase("joerg")
    for (year in 20 until 21) {
        val collection = database.getCollection("""osm-leipzig-ortsteile""")
        val files = Paths.get(System.getProperty("user.dir") + "/../docs/geojsons/districts").toFile().listFiles()
        val objectMapper = ObjectMapper().configure(
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(),
                true
        )
        for (file in files!!) {
            val rootNode = objectMapper.readValue(file, JsonNode::class.java)
            val featuresNode = rootNode.get("features") as ArrayNode
            for (i in 0 until featuresNode.size()) {
                val featureJson = featuresNode.get(i).toString()
                try {
                    collection.insertOne(Document.parse(featureJson))
                } catch (e: Exception) {
                    println("$year-$i-$featureJson: $e")
                }
            }
        }
    }
}