package com.liamcoalstudio.kettle.kwl.exceptions

class VersionMismatchException(expected: Int, got: Int) : Exception("This version of KWL ($expected) doesnt support $got. [$expected < $got]")