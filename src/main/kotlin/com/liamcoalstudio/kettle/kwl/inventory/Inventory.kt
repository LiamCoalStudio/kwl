package com.liamcoalstudio.kettle.kwl.inventory

import java.io.DataInputStream

interface Inventory<T> {
    operator fun get(slot: T): Item
    operator fun set(slot: T, item: Item)

    fun read(reader: DataInputStream) {
        enumValues.forEach {
            set(it, Item.read(reader))
        }
    }

    val enumValues: Array<T>
}