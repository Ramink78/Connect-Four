package connectfour

fun main() {
    println(Const.GAME_TITLE)
    println(Const.FIRST_PLAYER_NAME_MESSAGE)
    val firstPlayer = Player(readln(), 'o')
    println(Const.Second_PLAYER_NAME_MESSAGE)
    val secondPlayer = Player(readln(), '*')
    val gameBoard = Board()
    val game = Game(
        board = gameBoard,
        firstPlayer = firstPlayer,
        secondPlayer = secondPlayer
    )
    game.start()
}
