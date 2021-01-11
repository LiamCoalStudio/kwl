package com.liamcoalstudio.kettle.kwl.inventory

import com.liamcoalstudio.kettle.kwl.Block
import java.io.DataInputStream

@Suppress("unused")
enum class Item(val id: Int, val craft: Int, val maxDurability: Int = 0, val block: Block? = null) {
    Air(id = 0, craft = 0),
    Cobblestone(id = 1, craft = 14, block = Block.Cobblestone),

    ;

    companion object {
        private val map = mutableMapOf<Int, Item>()

        init {
            values().forEach { map[it.id] = it }
        }

        fun read(reader: DataInputStream): Item {
            return map[reader.readShort().toInt()]!!
        }
    }
}