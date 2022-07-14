package connectfour

object Patterns {
    val winPattern1 = "o{4}".toRegex()
    val winPattern2 = "\\*{4}".toRegex()
    val dimensionPattern = "\\d+[xX]\\d+".toRegex()
    val totalRoundPattern = "[1-9]+".toRegex()

}