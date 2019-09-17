package com.airwallex.codechallenge.input

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Alert(val timestamp: Instant,
                 val currencyPair: String,
                 val alert: AlertType,
                 val seconds: Int?) {

    override fun toString(): String {
        val sec = if(seconds != null) ", seconds: $seconds" else ""
        return "{ \"timestamp\": $timestamp, \"currencyPair\": $currencyPair, \"alert\": $alert$sec }"

    }
}