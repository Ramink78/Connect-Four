package connectfour

import connectfour.Const.GAME_OVER
import connectfour.Const.SINGLE_ROUND_GAME


class Game(
    val board: Board,
    val firstPlayer: Player,
    val secondPlayer: Player
) {
    private var currentPlayer = firstPlayer
    private var _totalRound = 0
    val totalRound: Int
        get() = _totalRound

    init {
        askTotalRoundQuestion()
    }

    private fun askTotalRoundQuestion() {
        while (true) {
            println(Const.SINGLE_OR_MULTIPLE_QUESTION)
            val totalRound = readln()
            if (totalRound.matches(Patterns.totalRoundPattern) || totalRound.isEmpty()) {
                _totalRound = if (totalRound.isEmpty()) 1 else totalRound.toInt()
                showGameInfo(_totalRound)
                return
            } else {
                println(Const.INVALID_INPUT_WARNING)
                continue
            }
        }
    }

    private fun showGameInfo(totalRound: Int) {
        println("${firstPlayer.name} VS ${secondPlayer.name}")
        println("${board.rows} X ${board.columns} board")
        if (totalRound == SINGLE_ROUND_GAME) println(Const.SINGLE_GAME) else println("Total $totalRound games")
    }

    fun start() {
        repeatGame(_totalRound) { currentRound ->
            if (_totalRound > 1) println("Game #$currentRound")
            board.drawBoard()
            playTheRound()
            // End of round
            if (_totalRound > 1 && !board.isFullBoard()) finishRound()
        }
        println(GAME_OVER)

    }

    private fun finishRound() {
        println(Const.SCORE_MESSAGE)
        println("${firstPlayer.name}: ${firstPlayer.score} ${secondPlayer.name}: ${secondPlayer.score}")
    }

    private fun playTheRound() {

        while (true) {
            println("${currentPlayer.name}'s turn:")
            val selectedCol = readln()
            when (board.checkSelectedColumn(selectedCol)) {
                Const.END_GAME -> {
                    println(GAME_OVER)
                    return
                }
                Const.INCORRECT_COLUMN_NUMBER_WARNING -> {
                    println(Const.INCORRECT_COLUMN_NUMBER_WARNING)
                    continue
                }
                Const.COLUMN_OUT_OF_RANGE_WARNING -> {
                    println("${Const.COLUMN_OUT_OF_RANGE_WARNING} (1 - ${board.columns})")
                    continue
                }
                Const.FULL_COLUMN -> {
                    println("Column $selectedCol is full")
                    continue
                }
                Const.CORRECT_COLUMN_NUMBER -> {
                    board.putDisc(currentPlayer.disc, selectedCol.toInt())
                    board.drawBoard()
                    if (currentPlayer.hasWon(board)) {
                        currentPlayer.score += 2
                        println("Player ${currentPlayer.name} won")
                        board.clean()
                        nextPlayer()
                        return
                    }
                    nextPlayer()
                }
            }
            if (board.isFullBoard()) {
                println(Const.IT_IS_A_DRAW)
                firstPlayer.score += 1
                secondPlayer.score += 1
                board.clean()
                break
            }

        }

    }

    private fun repeatGame(totalRound: Int, block: (currentRound: Int) -> Unit) {
        repeat(totalRound) {
            block(it + 1)
        }

    }

    private fun nextPlayer() {
        currentPlayer = if (currentPlayer == firstPlayer) secondPlayer else firstPlayer
    }
}



