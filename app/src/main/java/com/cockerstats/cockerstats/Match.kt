package com.cockerstats.cockerstats

enum class Color {
    DARK_RED,
    LIGHT_RED,
    WHITE,
    GOLD,
    HENNIE,
    DOME,
    HATCH_GRAY,
    BLUE_MUG,
    BLACK_MUG,
    OTHER
}

enum class Leg {
    DARK,
    WHITE,
    YELLOW,
    RED,
    OTHER
}

enum class Tail {
    BLACK,
    WHITE,
    WHITE_BLACK,
    BLACK_WHITE,
    MIX,
    OTHER
}

enum class Betting {
    DEHADO,
    LLAMADO,
}

enum class ResultSide {
    MERON,
    WALA,
    DRAW
}

data class Match (val meron: CockAttributes,
                  val wala: CockAttributes,
                  val winner: ResultSide,
                  val time: String) {

}

data class CockAttributes (val color: Color? = null,
                           val leg: Leg? = null,
                           val tail: Tail? = null,
                           val betting: Betting? = null,
                           val withComb: Boolean? = null) {



}