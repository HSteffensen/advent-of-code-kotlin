package com.hsteffensen

typealias Pos2d = Pair<Long, Long>

class Grid<T>(
    val grid: Map<Pos2d, T>,
) {
    val minX = grid.keys.minOf { it.first }
    val maxX = grid.keys.maxOf { it.first }
    val minY = grid.keys.minOf { it.second }
    val maxY = grid.keys.maxOf { it.second }

    fun neighbors8(pos: Pos2d): Set<Pos2d> =
        (-1..1)
            .flatMap { dx ->
                (-1..1).mapNotNull { dy ->
                    ((pos.first + dx) to (pos.second + dy))
                        .takeIf { dx != 0 || dy != 0 }
                }
            }.toSet()

    companion object {
        fun <T> build(f: Builder<T>.() -> Unit) = Builder<T>().apply(f).build()
    }

    class Builder<T> {
        private val grid: MutableMap<Pos2d, T> = mutableMapOf()

        fun set(
            x: Long,
            y: Long,
            value: T,
        ) {
            grid[x to y] = value
        }

        fun set(
            x: Int,
            y: Int,
            value: T,
        ) {
            grid[x.toLong() to y.toLong()] = value
        }

        fun set(
            pos: Pos2d,
            value: T,
        ) {
            grid[pos] = value
        }

        fun build() = Grid(grid)
    }
}
