package com.liamcoalstudio.kettle.kwl.entity

import com.liamcoalstudio.kettle.kwl.Dimension
import com.liamcoalstudio.kettle.kwl.UniverseFile
import com.liamcoalstudio.kettle.kwl.inventory.PlayerInventory

class PlayerEntity(universe: UniverseFile, dimension: Dimension) : LivingEntity(universe, dimension) {
    val inventory = PlayerInventory()

    override fun tick() {
        // TODO: hunger?
    }

    override fun destroy() {

    }
}