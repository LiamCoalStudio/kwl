package com.liamcoalstudio.kettle.kwl.entity

import com.liamcoalstudio.kettle.kwl.Dimension
import com.liamcoalstudio.kettle.kwl.UniverseFile
import com.liamcoalstudio.kettle.kwl.KWL
import java.io.DataInputStream
import java.util.*

/**
 * An entity, like a pig or a player.
 */
abstract class Entity(val universe: UniverseFile, val dimension: Dimension) {
    /**
     * The current location of the entity.
     */
    lateinit var location: Location protected set
    lateinit var uuid: UUID protected set

    /**
     * Reads an entity from a reader. Structure depends on the entity,
     * but should always initialize [location] and [uuid] in one way
     * or another.
     *
     * The implementations of Entities provided by KWL always calls
     * `super.read`.
     *
     * Default implementation is this structure:
     * 1. [location]: 3 [Double]s, read by [Location]
     * 2. [uuid]: 2 [Long]s
     */
    open fun read(reader: DataInputStream): Entity {
        location = Location.read(reader)
        uuid = UUID(reader.readLong(), reader.readLong())
        return this
    }

    /**
     * Ticks this entity. Called by your own implementation.
     */
    @Suppress("unused")
    abstract fun tick()

    /**
     * Destroys this entity. Called by the default implementation of
     * [KWL.entityDeathHandler].
     */
    @Suppress("unused")
    abstract fun destroy()

    companion object {
        /**
         * Reads an entire entity from a reader. Calls [Entity.read] to
         * read the actual entity, after reading the UUID and ID.
         *
         * @return `null` if entity is invalid, otherwise the entity
         * after calling [Entity.read]
         */
        fun read(reader: DataInputStream): Entity? {
            val id = reader.readUnsignedShort()
            val uuid = UUID(reader.readLong(), reader.readLong())
            return when(id) {
                else -> null
            }
        }
    }
}