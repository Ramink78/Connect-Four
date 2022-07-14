package connectfour

class Player(val name: String, val disc: Char) {
    var score: Int = 0

    fun hasWon(board: Board) = checkHorizontally(board) ||
            checkVertically(board) ||
            checkDiagonalWinner(board) ||
            checkReverseDiagonalWinner(board)


    private fun checkHorizontally(board: Board): Boolean {

        board.tableGame.forEach { row ->
            if (Patterns.winPattern1.containsMatchIn(row.joinToString("")) ||
                Patterns.winPattern2.containsMatchIn(row.joinToString(""))
            ) {
                // A player has won
                return true
            }
        }
        return false
    }

    private fun checkVertically(board: Board): Boolean {
        board.transposeTable(board.tableGame).forEach { row ->
            if (Patterns.winPattern2.containsMatchIn(row.joinToString("")) ||
                Patterns.winPattern1.containsMatchIn(row.joinToString(""))
            ) {
                // A player has won
                return true
            }
        }
        return false
    }

    private fun checkDiagonalWinner(board: Board): Boolean {
        val diagonals = board.rows + board.columns - 1
        for (i in 0 until diagonals) {
            var count1 = 0
            var count2 = 0
            var currentDiscIsO = true
            if (i < board.rows) {
                var row = i
                var column = 0
                while (row >= 0) {
                    when (board.tableGame[row--][column++]) {
                        " " -> continue
                        "o" -> if (currentDiscIsO) count1++
                        else {
                            count1++
                            count2 = 0
                            currentDiscIsO = true
                        }
                        "*" -> if (!currentDiscIsO) count2++
                        else {
                            count2++
                            count1 = 0
                            currentDiscIsO = false
                        }
                    }
                    if (count1 == 4 || count2 == 4)
                        return true
                }
            } else {
                var row = board.rows - 1
                var colum = i - board.rows + 1
                while (colum < board.columns && row >= 0) {
                    when (board.tableGame[row--][colum++]) {
                        " " -> continue
                        "o" -> if (currentDiscIsO) count1++
                        else {
                            count1++
                            count2 = 0
                            currentDiscIsO = true
                        }
                        "*" -> if (!currentDiscIsO) count2++
                        else {
                            count2++
                            count1 = 0
                            currentDiscIsO = false
                        }
                    }
                    if (count1 == 4 || count2 == 4)
                        return true
                }
            }
        }
        return false
    }

    private fun checkReverseDiagonalWinner(board: Board): Boolean {

        val diagonals = board.rows + board.columns - 1
        for (i in 0 until diagonals) {
            var count1 = 0
            var count2 = 0
            var currentDiscIsO = true
            if (i < board.rows) {
                var row = i
                var column = board.columns - 1
                while (row >= 0 && column >= 0) {
                    when (board.tableGame[row--][column--]) {
                        " " -> continue
                        "o" -> if (currentDiscIsO) count1++
                        else {
                            count1++
                            count2 = 0
                            currentDiscIsO = true
                        }
                        "*" -> if (!currentDiscIsO) count2++
                        else {
                            count2++
                            count1 = 0
                            currentDiscIsO = false
                        }
                    }
                    if (count1 == 4 || count2 == 4)
                        return true
                }
            } else {
                var row = board.rows - 1
                var colum = diagonals - i - 1
                while (colum >= 0 && board.rows > 0) {
                    when (board.tableGame[row--][colum--]) {
                        " " -> continue
                        "o" -> if (currentDiscIsO) count1++
                        else {
                            count1++
                            count2 = 0
                            currentDiscIsO = true
                        }
                        "*" -> if (!currentDiscIsO) count2++
                        else {
                            count2++
                            count1 = 0
                            currentDiscIsO = false
                        }
                    }
                    if (count1 == 4 || count2 == 4)
                        return true
                }
            }
        }
        return false
    }

}

