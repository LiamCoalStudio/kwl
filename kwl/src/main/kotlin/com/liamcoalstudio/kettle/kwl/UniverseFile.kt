package com.liamcoalstudio.kettle.kwl

import com.liamcoalstudio.kettle.kwl.entity.Entity
import com.liamcoalstudio.kettle.kwl.exceptions.VersionMismatchException
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.HashMap

/**
 * A file containing everything. This is the `.kwl` file. It contains:
 * - Some [dimensions] ([Dimension])
 * - Some [entities] ([Entity])
 * - A [name] ([String])
 * - A [palette] ([BlockPalette])
 * - A [reader] ([DataInputStream])
 *
 * Set file to `/dev/zero` (or your system's equivalent) for an empty
 * universe.
 */
class UniverseFile(file: File) {
    /**
     * A [DataInputStream] for the [File] argument passed to the
     * constructor of [UniverseFile]. It is passed everywhere
     * that needs some binary data, otherwise the data is just read
     * here.
     *
     * Initialized on construction.
     */
    private val reader = DataInputStream(FileInputStream(file))

    /**
     * A [BlockPalette], currently unused.
     *
     * If `null`, default to just reading the direct IDs.
     * If not `null`, map through it to get the real block.
     *
     * Default: `null`
     */
    private var palette: BlockPalette? = null

    /**
     * The name for this universe.
     *
     * Default: `"Another in the pile"`
     */
    private var name: String = "Another in the pile..."

    /**
     * The entities in this universe, currently unused.
     *
     * Default: `HashMap<UUID, Entity>()`
     */
    val entities = HashMap<UUID, Entity>()

    /**
     * Every dimension in this universe. This is a map of [Dimension],
     * by [Dimension.Type].
     *
     * Default: `EnumMap<Dimension.Type, Dimension>(Dimension.Type::class.java)`
     */
    val dimensions = EnumMap<Dimension.Type, Dimension>(Dimension.Type::class.java)

    /*
     * Refer to class documentation ([UniverseFile]), as there can't be
     * documentation on `init`, for some reason.
     */
    init {
        val readVersion = reader.readUnsignedShort()
        if(KWL.VERSION != readVersion)
            throw VersionMismatchException(KWL.VERSION, readVersion)
        while(reader.available() > 0) {
            when(reader.readUnsignedByte()) {
                END_EARLY -> break
                SET_NAME -> name = String(reader.readNBytes(reader.readShort().toInt()))
                SET_PALETTE -> palette = BlockPalette.read(reader)
                CLEAR_PALETTE -> palette = null
                ADD_ENTITY -> entities[readUUID(reader)] = Entity.read(reader)!!
                ADD_DIMENSION -> readDimension(reader)
            }
        }
    }

    /**
     * Reads a [Dimension] from [reader], and adds it to [dimensions].
     *
     * @throws NullPointerException Type read was null. May also be thrown
     * if anything used was not initialized.
     */
    private fun readDimension(reader: DataInputStream) {
        val type = when(reader.readUnsignedByte()) {
            0 -> Dimension.Type.Overworld
            1 -> Dimension.Type.Nether
            2 -> Dimension.Type.End
            else -> null
        }
        val dimension = Dimension(requireNotNull(type, {"type was null. please check your file."}))
        while(true) {
            when(reader.readUnsignedByte()) {
                0x00 -> break
                0x01 -> dimension[reader.readInt(), reader.readInt(), reader.readInt()] = Chunk.read(reader)
            }
        }
        dimensions[type] = dimension
    }

    companion object {
        /** Ends universe reading early. */
        const val END_EARLY = 0x00

        /** Sets the name of the universe. */
        const val SET_NAME = 0x01

        /** Sets the palette of the universe to the following value. */
        const val SET_PALETTE = 0x02

        /** Sets the palette of the universe to `null` */
        const val CLEAR_PALETTE = 0x03

        /** Adds an [Entity] to the universe */
        const val ADD_ENTITY = 0x04

        /** Adds a [Dimension] to the universe */
        const val ADD_DIMENSION = 0x05

        /**
         * Reads a UUID from 2 [Long] values, most significant bits first.
         *
         * @param reader The reader to read from.
         */
        private fun readUUID(reader: DataInputStream) = UUID(reader.readLong(), reader.readLong())
    }
}
