package connectfour

import connectfour.Const.BOARD_COLUMNS_INVALID_WARNING
import connectfour.Const.BOARD_ROWS_INVALID_WARNING
import connectfour.Const.INVALID_INPUT_WARNING
import connectfour.Const.REQUEST_DIMENSION_MESSAGE

class Board {
    private val defaultRows = 6
    private val defaultColumns = 7
    val tableGame = mutableListOf<MutableList<String>>()
    val rows: Int
        get() = tableGame.size

    val columns: Int
        get() = tableGame[0].size


    init {
        checkDimensionCorrection()
    }

    private fun initializeGameTable(rows: Int, columns: Int, list: MutableList<MutableList<String>>) {
        repeat(rows) {
            list.add(MutableList(columns) { "" })
        }
    }

    private fun checkDimensionCorrection() {
        while (true) {
            println(REQUEST_DIMENSION_MESSAGE)
            val dimension = readln()
                .uppercase()
                .replace("\t", "")
                .replace(" ", "")
            when {
                dimension.isEmpty() -> {
                    initializeGameTable(defaultRows, defaultColumns, tableGame)
                    break
                }
                dimension.matches(Patterns.dimensionPattern) -> {
                    val rows = dimension.split("x", ignoreCase = true).map { it.trim().toInt() }[0]
                    val columns = dimension.split("x", ignoreCase = true).map { it.trim().toInt() }[1]
                    if (rows !in 5..9) {
                        println(BOARD_ROWS_INVALID_WARNING)
                        continue
                    }
                    if (columns !in 5..9) {
                        println(BOARD_COLUMNS_INVALID_WARNING)
                        continue
                    }
                    initializeGameTable(rows, columns, tableGame)
                    break
                }
                else -> {
                    println(INVALID_INPUT_WARNING)
                    continue
                }
            }
        }
    }

    fun drawBoard() {
        val cells = tableGame[0].size
        repeat(cells) { column ->
            print(" ${column + 1}")
        }
        println()
        for (row in tableGame) {
            for (cell in row.indices) {
                print("║${row[cell].ifEmpty { " " }}")
                if (cell == row.lastIndex) {
                    print("║")
                }
            }
            println()
        }
        // Draw bottom of borad
        repeat(cells) { column ->
            // Bottom left edge of board
            if (column == 0) {
                print("╚")
            } else {
                print("═╩")
            }
            // Bottom right edge of board
            if (column + 1 == cells) println("═╝")
        }
    }

    private fun isFullColumn(column: Int): Boolean {
        return getLastEmptyRow(column) < 0
    }

    fun isFullBoard(): Boolean {
        var result = true
        for (col in tableGame[0].indices) {
            result = result && isFullColumn(col + 1)
        }
        return result
    }

    private fun getLastEmptyRow(column: Int): Int {
        for (row in tableGame.size - 1 downTo 0) {
            if (tableGame[row][column - 1].isEmpty()) return row
        }
        // column is full
        return -1
    }

    fun checkSelectedColumn(selectedColumn: String) =
        when {
            selectedColumn == Const.END_GAME -> Const.END_GAME
            !selectedColumn.all { it.isDigit() } -> Const.INCORRECT_COLUMN_NUMBER_WARNING
            selectedColumn.toInt() !in 1..columns -> Const.COLUMN_OUT_OF_RANGE_WARNING
            isFullColumn(selectedColumn.toInt()) -> Const.FULL_COLUMN
            else -> Const.CORRECT_COLUMN_NUMBER
        }

    fun putDisc(disc: Char, column: Int) {
        val lastEmptyRow = getLastEmptyRow(column)
        tableGame[lastEmptyRow][column - 1] = disc.toString()

    }

    fun clean() {
        val tableRows = rows
        val tableColumns = columns
        tableGame.apply {
            clear()
            initializeGameTable(tableRows, tableColumns, this)
        }
    }

    fun transposeTable(list: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        val transposedList = mutableListOf<MutableList<String>>()
        initializeGameTable(list[0].size, list.size, transposedList)
        for (i in 0 until list.size) {
            for (j in 0 until list[0].size) {
                transposedList[j][i] = list[i][j]
            }
        }
        return transposedList
    }
}