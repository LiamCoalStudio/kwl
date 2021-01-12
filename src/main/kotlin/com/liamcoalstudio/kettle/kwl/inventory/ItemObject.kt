package com.liamcoalstudio.kettle.kwl.inventory

import com.liamcoalstudio.kettle.kwl.inventory.Item

data class ItemObject(var item: Item, var count: Byte, val state: Short = 0) {
    companion object {
        val NONE = ItemObject(Item.Air, 0)
    }
}