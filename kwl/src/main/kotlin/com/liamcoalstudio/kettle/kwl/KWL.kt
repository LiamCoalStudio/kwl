package com.liamcoalstudio.kettle.kwl

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
    const val VERSION: Int = 0
}