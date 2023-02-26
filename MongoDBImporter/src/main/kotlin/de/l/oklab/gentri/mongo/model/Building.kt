package de.l.oklab.gentri.mongo.model

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Property
import dev.morphia.geo.Geometry
import org.bson.types.ObjectId

open class Building(
    @Id var id: ObjectId? = null,
    @Property("properties") var props: Map<String, String> = emptyMap(),
    @Property("geometry") var geometry: Geometry? = null
)

@Entity("osm-leipzig-20140101")
class Building2014(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20150101")
class Building2015(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20160101")
class Building2016(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20170101")
class Building2017(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20180101")
class Building2018(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20190101")
class Building2019(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20200101")
class Building2020(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20210101")
class Building2021(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20220101")
class Building2022(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)

@Entity("osm-leipzig-20230101")
class Building2023(id: ObjectId?, props: Map<String, String>, geometry: Geometry?): Building(id, props, geometry)