package com.liamcoalstudio.kettle.kwl.entity

import java.io.DataInputStream

data class Location(val x: Double, val y: Double, val z: Double) {
    companion object {
        fun read(reader: DataInputStream)
            = Location(reader.readDouble(), reader.readDouble(), reader.readDouble())
    }
}