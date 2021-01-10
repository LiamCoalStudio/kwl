package com.liamcoalstudio.kettle.kwl

import java.io.DataInputStream

/**
 * An `enum` of every block allowed in KWL files. It uses [id] and [state]
 * to identify blocks in a file, and [craft] can be used to communicate
 * with the official Minecraft client / server.
 *
 * _Not every block is documented, because I will not go through every
 * single block in the game, and add a descriptive comment to it, when
 * an even better description is available
 * [here](https://minecraft.gamepedia.com)._
 *
 * @see Chunk
 * @see BlockPalette
 */
@Suppress("unused")
enum class Block(val id: Short, val state: Byte, val craft: Int) {
    Air(id = 0, state = 0, craft = 0),

    // Stones
    Stone(id = 1, state = 0, craft = 1),
    Granite(id = 1, state = 1, craft = 2),
    Diorite(id = 1, state = 2, craft = 4),
    Andesite(id = 1, state = 3, craft = 6),
    PolishedGranite(id = 2, state = 0, craft = 3),
    PolishedDiorite(id = 2, state = 1, craft = 5),
    PolishedAndesite(id = 2, state = 2, craft = 7),
    Cobblestone(id = 4, state = 0, craft = 14),
    Bedrock(id = 5, state = 0, craft = 33),

    // Dirts
    GrassBlock(id = 2, state = 0, craft = 9),
    SnowyGrassBlock(id = 2, state = 1, craft = 8),
    Dirt(id = 3, state = 0, craft = 10),
    CoarseDirt(id = 3, state = 1, craft = 11),

    // Planks
    OakPlanks(id = 6, state = 0, craft = 15),
    BirchPlanks(id = 6, state = 1, craft = 17),
    SprucePlanks(id = 6, state = 2, craft = 16),
    JunglePlanks(id = 6, state = 3, craft = 18),
    AcaciaPlanks(id = 6, state = 4, craft = 19),
    DarkOakPlanks(id = 6, state = 5, craft = 20),

    // Water [34->49]
    WaterLevel00(id = 7, state = 0, craft = 34+0),
    WaterLevel01(id = 7, state = 1, craft = 34+1),
    WaterLevel02(id = 7, state = 2, craft = 34+2),
    WaterLevel03(id = 7, state = 3, craft = 34+3),
    WaterLevel04(id = 7, state = 4, craft = 34+4),
    WaterLevel05(id = 7, state = 5, craft = 34+5),
    WaterLevel06(id = 7, state = 6, craft = 34+6),
    WaterLevel07(id = 7, state = 7, craft = 34+7),
    WaterLevel08(id = 7, state = 8, craft = 34+8),
    WaterLevel09(id = 7, state = 9, craft = 34+9),
    WaterLevel10(id = 7, state = 10, craft = 34+10),
    WaterLevel11(id = 7, state = 11, craft = 34+11),
    WaterLevel12(id = 7, state = 12, craft = 34+12),
    WaterLevel13(id = 7, state = 13, craft = 34+13),
    WaterLevel14(id = 7, state = 14, craft = 34+14),
    WaterLevel15(id = 7, state = 15, craft = 34+15),

    // Lava [50->65]
    LavaLevel00(id = 8, state = 0, craft = 50+0),
    LavaLevel01(id = 8, state = 1, craft = 50+1),
    LavaLevel02(id = 8, state = 2, craft = 50+2),
    LavaLevel03(id = 8, state = 3, craft = 50+3),
    LavaLevel04(id = 8, state = 4, craft = 50+4),
    LavaLevel05(id = 8, state = 5, craft = 50+5),
    LavaLevel06(id = 8, state = 6, craft = 50+6),
    LavaLevel07(id = 8, state = 7, craft = 50+7),
    LavaLevel08(id = 8, state = 8, craft = 50+8),
    LavaLevel09(id = 8, state = 9, craft = 50+9),
    LavaLevel10(id = 8, state = 10, craft = 50+10),
    LavaLevel11(id = 8, state = 11, craft = 50+11),
    LavaLevel12(id = 8, state = 12, craft = 50+12),
    LavaLevel13(id = 8, state = 13, craft = 50+13),
    LavaLevel14(id = 8, state = 14, craft = 50+14),
    LavaLevel15(id = 8, state = 15, craft = 50+15),

    ;

    companion object {
        /**
         * An array of every block [id] that has multiple [state]s associated
         * with it. Allows saving a byte assuming the state is `0` if it isn't
         * included here.
         *
         * @see read
         */
        private val multiStates = values().filter { it.state > 0 }.map { it.id }.distinct()

        /**
         * Finds a block based on it's [Block.craft] value. Allows retrieving a
         * vanilla block ID and converting to the KWL format.
         *
         * @param craft The [Block.craft] value to search for.
         */
        fun fromCraftId(craft: Int) = values().find { it.craft == craft }

        /**
         * Finds a block by it's [id] and [state]. If [id] is only associated
         * with a `0` [state], then it will not be read. This function uses
         * [multiStates] to achieve this.
         *
         * This override reads it from a [DataInputStream], used in
         * [Chunk.read].
         *
         * @param reader The [DataInputStream] to read the block from.
         *
         * @see multiStates
         * @see fromIdAndState
         */
        fun read(reader: DataInputStream): Block {
            val id = reader.readShort()
            return if(multiStates.contains(id)) {
                val state = reader.readByte()
                fromIdAndState(id, state)
            } else {
                fromIdAndState(id)
            }
        }

        /**
         * Finds a block by it's [Block.id] and [Block.state].
         *
         * This override reads it from the arguments, [id] and [state]
         *
         * @param id The [Block.id] to search for.
         * @param state The [Block.state] to search for. Defaults to `0` for
         * readability.
         */
        fun fromIdAndState(id: Short, state: Byte = 0) = values().find { it.id == id && it.state == state }!!
    }
}
