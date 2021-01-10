package com.liamcoalstudio.kettle.kwl

/**
 * A dimension full of [Chunk]s. All it is is basically just a
 * container for [Chunk]s, mapped by [Position]. It also has a [type]
 * value, which can be used to determine which dimension type this
 * represents.
 *
 * @see Dimension.Type
 * @see Chunk
 */
class Dimension(val type: Type) {
    /**
     * A [MutableMap] of every chunk in this dimension.
     *
     * @see Chunk
     * @see Position
     */
    private val chunks = mutableMapOf<Position, Chunk>()

    /**
     * Gets a [Chunk] (from [chunks]) by [Position].
     *
     * @see Chunk
     * @see Position
     * @see chunks
     */
    operator fun get(position: Position) = chunks[position]

    /**
     * Gets a [Chunk] (from [chunks]) by {[x], [y], [z]} value. This
     * implementation uses the other [get] internally, by converting
     * {[x], [y], [z]} to a [Position].
     *
     * @see Chunk
     * @see get
     */
    operator fun get(x: Int, y: Int, z: Int) = this[Position(x, y, z)]

    /**
     * Sets a [Chunk] (in [chunks]) to [chunk] using a [Position] value.
     *
     * @see Chunk
     * @see Position
     * @see chunks
     */
    operator fun set(position: Position, chunk: Chunk) { chunks[position] = chunk }

    /**
     * Sets a [Chunk] (in [chunks]) to [chunk] using {[x], [y], [z]}. Uses
     * the other [set] internally, by converting {[x], [y], [z]}
     * to a [Position].
     *
     * @see Chunk
     * @see set
     */
    operator fun set(x: Int, y: Int, z: Int, chunk: Chunk) { this[Position(x, y, z)] = chunk }

    /**
     * A dimension type. Can be either [Overworld], [Nether], or [End].
     *
     * Network protocol implementations and clients can use this value to
     * determine which dimension it's currently in. _However_, it provides
     * no info about the dimension, as that must be provided by the
     * implementation.
     *
     * @see Dimension.type
     */
    enum class Type { Overworld, Nether, End }
}