package com.liamcoalstudio.kettle.kwl

import java.io.DataInputStream

/**
 * Creates a palette of blocks, allowing the use of smaller numbers
 * corresponding to larger numbers in a [BlockPalette]. Palettes are
 * used by the Minecraft Protocol in `ChunkDataPacket [S->C]` to
 * shorten the packet a little bit.
 *
 * @param blocks The list of blocks to use as a palette. You can use
 * [Block.fromCraftId] to convert Minecraft's id to a [Block].
 *
 * @see Block
 */
class BlockPalette(private val blocks: List<Block>) {
    /**
     * Maps an [IntArray] to its [Block] values in [blocks].
     *
     * @param array The array to map to [Block] values.
     */
    fun map(array: IntArray) = array.map { blocks[it] }

    /**
     * Verifies that all blocks in the palette are valid, using
     * [validityChecker]. This can be used in implementations with a
     * restricted block set to make sure no KWL files that have
     * invalid blocks are loaded.
     *
     * @return `true` if everything is OK, `false` otherwise.
     *
     * @see validityChecker
     */
    fun verify() = blocks.all { validityChecker(blocks.indexOf(it), it) }

    companion object {
        /**
         * Reads a palette from a [DataInputStream]. Converts a block
         * from binary form into a [Block] using [Block.fromCraftId].
         *
         * __Not usable with Minecraft's Protocol! Only use this when
         * reading KWL files!__
         *
         * @param reader The [DataInputStream] to read the palette from.
         * @return A [BlockPalette] read from [reader]
         *
         * @see Block.fromCraftId
         */
        fun read(reader: DataInputStream): BlockPalette {
            val list = mutableListOf<Block>()
            val size = reader.readInt()
            for (i in 0 until size)
                list.add(Block.fromCraftId(reader.readInt())!!)
            return BlockPalette(list.toList())
        }

        // You may expect me to provide a [read] function compatible
        // with Minecraft's Protocol, but that's not going to happen.
        //
        // Implement it yourself.

        /**
         * Checks if a block is allowed in the palette. Defaults to
         * making sure that the block actually exists, but it should
         * never actually be called with an invalid block, because
         * an exception would be thrown before then.
         *
         * The first argument ([Int]) is the index of that block in
         * the palette, and the second argument ([Block]) is the
         * block that was read.
         *
         * @see verify
         */
        var validityChecker: (Int, Block) -> Boolean = { _, block -> Block.values().contains(block) }
    }
}
