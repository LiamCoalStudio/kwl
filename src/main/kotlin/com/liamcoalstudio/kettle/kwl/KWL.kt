package com.liamcoalstudio.kettle.kwl

import com.liamcoalstudio.kettle.kwl.entity.LivingEntity
import java.util.*

/**
 * Just an `object` with some globally used values that are required. Not
 * too special.
 */
object KWL {
    /**
     * The current version of KWL. Used in [UniverseFile] to make sure that
     * this version of KWL can read the file given.
     *
     * @see UniverseFile
     */
    const val VERSION: Int = 1

    /**
     * Handles the deaths of [LivingEntity]. You probably want to override
     * this in your own implementation, to handle entity death.
     *
     * Arguments:
     * 1. universe: [UniverseFile]
     * 2. dimension: [Dimension]
     * 3. entity: [LivingEntity]
     *
     * Default: Removes entity from [UniverseFile].
     */
    var entityDeathHandler: (UniverseFile, Dimension, LivingEntity) -> Unit = { u, _, e ->
        e.destroy()
        u.entities.remove(e.uuid)
    }
}