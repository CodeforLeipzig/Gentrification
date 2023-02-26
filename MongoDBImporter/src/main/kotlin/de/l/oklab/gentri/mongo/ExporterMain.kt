package de.l.oklab.gentri.mongo

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import com.mongodb.client.model.geojson.Polygon
import de.l.oklab.gentri.mongo.model.*
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.aggregation.stages.Out
import dev.morphia.query.filters.Filters

fun main() {
    val datastore = Morphia.createDatastore(MongoClients.create(ConnectionString("mongodb://admin:admin@localhost:27017")), "joerg")
    datastore.mapper.mapPackage("de.l.oklab.gentri.mongo.model")
    for (year in 2020 until 2021) {
        for (district in getDistricts(datastore)) {
            storeBuildungsByYearAndDistrict(datastore, year, district)
        }
    }
}

fun storeBuildungsByYearAndDistrict(datastore: Datastore, year: Int, district: District) {
    val building = when (year) {
        2014 -> Building2014::class.java
        2015 -> Building2015::class.java
        2016 -> Building2016::class.java
        2017 -> Building2017::class.java
        2018 -> Building2018::class.java
        2019 -> Building2019::class.java
        2020 -> Building2020::class.java
        2021 -> Building2021::class.java
        2022 -> Building2022::class.java
        else -> Building2023::class.java
    }
    val targetCollection = "buildings-$year-${district.getName()}"
    datastore.aggregate(building).match(
            Filters.geoWithin("geometry", district.geometry as Polygon)
    ).out(Out.to(targetCollection))
}

fun getDistricts(datastore: Datastore): List<District> {
    return datastore.find(District::class.java).toList()
}