package com.liamcoalstudio.kettle.kwl

import kotlin.math.floor

/**
 * Simple data class holding [x], [y], and [z] values in a single `class`.
 * Only really exists for convenience reading and writing things in [Chunk]
 * and [Dimension].
 */
data class Position(val x: Int, val y: Int, val z: Int) {
    /**
     * Creates a [Position] from non-[Int] numbers. Calls [Number.toInt].
     */
    constructor(x: Number, y: Number, z: Number) : this(x.toInt(), y.toInt(), z.toInt())

    // Operators don't really need documentation, unless they do
    // something special.
    operator fun plus(a: Position) = Position(x + a.x, y + a.y, z + a.z)
    operator fun plus(a: Number): Position = plus(Position(a, a, a))
    operator fun minus(a: Position) = Position(x - a.x, y - a.y, z - a.z)
    operator fun minus(a: Number): Position = minus(Position(a, a, a))
    operator fun times(a: Position) = Position(x * a.x, y * a.y, z * a.z)
    operator fun times(a: Number): Position = times(Position(a, a, a))
    operator fun div(a: Position) = Position(x / a.x, y / a.y, z / a.z)
    operator fun div(a: Number): Position = div(Position(a, a, a))
    operator fun rem(a: Position) = Position(x % a.x, y % a.y, z % a.z)
    operator fun rem(a: Number): Position = rem(Position(a, a, a))

    fun flooredDivide(a: Position) = Position(
        floor(x.toDouble() / a.x.toDouble()).toInt(),
        floor(y.toDouble() / a.y.toDouble()).toInt(),
        floor(z.toDouble() / a.z.toDouble()).toInt())

    fun flooredDivide(int: Int): Position = flooredDivide(Position(int, int, int))

    fun toLong() =
        (x.toLong() and 0x3FFFFFFL shl 38 or (z.toLong() and 0x3FFFFFFL shl 12) or (y.toLong() and 0xFFFL))

    companion object {
        fun fromLong(long: Long): Position {
            val x = long shr 38
            val y = long and 0xFFF
            val z = long shl 26 shr 38
            return Position(x, y, z)
        }

    }
}
