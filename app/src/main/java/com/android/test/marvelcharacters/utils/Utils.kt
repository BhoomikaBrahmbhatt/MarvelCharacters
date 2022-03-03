package com.android.test.marvelcharacters.utils

object Utils {

    //https://gateway.marvel.com:443/v1/public/characters?apikey=5bd1eb18d414cff0fbe03da663d3fd2c&ts=1&hash=2b58aaee8278f900219dfb64d0eb6dc5
    const val value_ts=1
    const val value_hash="2b58aaee8278f900219dfb64d0eb6dc5"
    const val value_apikey="5bd1eb18d414cff0fbe03da663d3fd2c"

    infix fun <T> Boolean.then(value: T?) = TernaryExpression(this, value)

    class TernaryExpression<out T>(val flag: Boolean, val truly: T?) {
        infix fun <T> or(falsy: T?) = if (flag) truly else falsy
    }
}