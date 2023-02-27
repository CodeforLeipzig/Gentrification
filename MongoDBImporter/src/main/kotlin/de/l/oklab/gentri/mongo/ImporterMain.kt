package de.l.oklab.gentri.mongo
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.mongodb.client.MongoClients
import org.bson.Document
import java.io.File

const val sourcePath = "/media/daten/"

fun main() {
    val mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017")
    val database = mongoClient.getDatabase("joerg")
    for (year in (14..23)) {
        val collection = database.getCollection("""osm-leipzig-20${year}0101""")
        val objectMapper = ObjectMapper().configure(
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(),
                true
        )
        val rootNode = objectMapper.readValue(File("""$sourcePath/sachsen-${year}0101-buildings-props.geojson"""),
                JsonNode::class.java)
        val featuresNode = rootNode.get("features") as ArrayNode
        for (i in 1 until featuresNode.size()) {
            val featureJson = featuresNode.get(i).toString()
                    .replace("addr.source:housenumber", "addr-source-housenumber")
            try {
                collection.insertOne(Document.parse(featureJson))
            } catch (e: Exception) {
                println("$year-$i-$featureJson: $e")
            }
        }
    }
}