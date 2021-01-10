package com.liamcoalstudio.kettle.kwl

/**
 * Simple data class holding [x], [y], and [z] values in a single `class`.
 * Only really exists for convenience reading and writing things in [Chunk]
 * and [Dimension].
 */
data class Position(val x: Int, val y: Int, val z: Int)
