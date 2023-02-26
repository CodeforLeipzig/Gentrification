package de.l.oklab.gentri.mongo
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.mongodb.client.MongoClients
import org.bson.Document
import java.io.File

const val sourcePath = "/media/Daten/"

fun main() {
    val mongoClient = MongoClients.create()
    val database = mongoClient.getDatabase("joerg")
    for (year in 14 until 24) {
        val collection = database.getCollection("""osm-leipzig-20${year}0101""")
        val objectMapper = ObjectMapper()
        val rootNode = objectMapper.readValue(File("""$sourcePath/sachsen-${year}0101-buildings-props.geojson"""),
                JsonNode::class.java)
        val featuresNode = rootNode.get("features") as ArrayNode
        for (i in 1 until featuresNode.size()) {
            val featureJson = featuresNode.get(i).toString()
                    .replace("addr.source:housenumber", "addr-source-housenumber")
            try {
                collection.insertOne(Document.parse(featureJson))
            } catch (e: Exception) {
                println("""$year-$i-$featureJson: $e""")
            }
        }
    }
}