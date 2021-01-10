package com.liamcoalstudio.kettle.kwl

import java.io.DataInputStream

/**
 * A 16x16x16 area of blocks. _Technically_ a chunk section, and
 * be treated as such when talking to the Client. Chunks are
 * _usually_ not associated with a palette in KWL files.
 *
 * @param blocks The [Block] values in this chunk, mapped by
 * [Position].
 *
 * @see Position
 * @see Block
 */
data class Chunk(val blocks: MutableMap<Position, Block>) {
    /**
     * The distinct [Block] values in this chunk. The count of
     * these is used in the Minecraft Protocol for "lighting
     * purposes." It is also used in [palette].
     *
     * It gets distinct values by [Block.craft], then sorts them
     * by [Block.id].
     *
     * @see Block
     */
    val distinct get() = blocks.values.distinctBy { it.craft }.sortedBy { it.id }

    /**
     * Creates a new [BlockPalette] based on the [distinct] values of
     * this chunk. It can be used as a Palette generator in Minecraft's
     * Protocol.
     *
     * @see distinct
     * @see BlockPalette
     */
    val palette get() = BlockPalette(distinct.toMutableList())

    /**
     * Sets a block in this chunk to the [block] value. __There is no
     * protection against values over `15`,__ so be careful of what
     * you're passing to it!
     *
     * @param x The X coordinate of the [Block] to [set]
     * @param y The Y coordinate of the [Block] to [set]
     * @param z The Z coordinate of the [Block] to [set]
     * @param block The block to set {[x], [y], [z]} to.
     *
     * @see Block
     * @see get
     */
    operator fun set(x: Int, y: Int, z: Int, block: Block) { blocks[Position(x, y, z)] = block }

    /**
     * Gets the [Block] value at {[x], [y], [z]}. __There is no
     * protection against values over `15`,__ so be careful of what
     * you're passing to it!
     *
     * @param x The X coordinate of the [Block] to [get]
     * @param y The Y coordinate of the [Block] to [get]
     * @param z The Z coordinate of the [Block] to [get]
     *
     * @see Block
     * @see set
     */
    operator fun get(x: Int, y: Int, z: Int) = blocks[Position(x, y, z)]

    companion object {
        /**
         * Reads a chunk from a [DataInputStream]. Uses [Block.read]
         * to read the blocks.
         *
         * Blocks are stored in the Z > Y > X format, but the Protocol
         * uses X > Z > Y, so you would have to do some conversion.
         *
         * @see Block.read
         */
        fun read(reader: DataInputStream): Chunk {
            val map = mutableMapOf<Position, Block>()
            for(x in 0..15) for(y in 0..15) for(z in 0..15)
                map[Position(x, y, z)] = Block.read(reader)
            return Chunk(map)
        }
    }
}
