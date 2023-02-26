package de.l.oklab.gentri.mongo.model

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Property
import dev.morphia.geo.Geometry
import org.bson.types.ObjectId

@Entity("osm-leipzig-ortsteile")
data class District(
    @Id var id: ObjectId? = null,
    @Property("properties") var props: Map<String, String> = emptyMap(),
    @Property("geometry") var geometry: Geometry? = null
) {

    fun getName(): String?  = props["Name"]
    fun getIndex(): String?  = props["OT"]
}