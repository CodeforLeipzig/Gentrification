package de.l.oklab.gentri.mongo

import com.mongodb.MongoClient
import de.l.oklab.gentri.mongo.model.*
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.geo.Polygon

fun main() {
    val morphia = Morphia()
    morphia.mapPackage("de.l.oklab.gentri.mongo.model");
    val datastore = morphia.createDatastore(MongoClient(), "joerg")
    for (year in 2014 until 2024) {
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
    val query = datastore.find(building)
    query.criteria("geometry").within(district.geometry as Polygon)
    val targetCollection = """buildings-$year-${district.getName()}"""
    datastore.createAggregation(building).match(query).out(targetCollection, building)
}

fun getDistricts(datastore: Datastore): List<District> {
    return datastore.find(District::class.java).toList()
}