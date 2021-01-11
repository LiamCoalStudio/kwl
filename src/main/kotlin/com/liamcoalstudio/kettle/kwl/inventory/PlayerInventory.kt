package com.liamcoalstudio.kettle.kwl.inventory

import java.util.*

class PlayerInventory : Inventory<PlayerInventorySlot> {
    override val enumValues = PlayerInventorySlot.values()
    private val enumMap = EnumMap<PlayerInventorySlot, Item>(PlayerInventorySlot::class.java)

    init {
        enumValues.forEach { this[it] = Item.Air }
    }

    override fun get(slot: PlayerInventorySlot) = enumMap[slot]!!
    override fun set(slot: PlayerInventorySlot, item: Item) { enumMap[slot] = item }
}