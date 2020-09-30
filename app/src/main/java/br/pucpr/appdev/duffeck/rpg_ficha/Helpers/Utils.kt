package br.pucpr.appdev.duffeck.rpg_ficha.Helpers

import kotlin.reflect.KProperty1

object Utils {
    fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
        val property = instance::class.members
            .first { it.name == propertyName } as KProperty1<Any, *>
        return property.get(instance) as R
    }

    fun getValueStringWithSignal(numero: Int): String {
        if (numero > 0) {
            return "+$numero"
        }
        return numero.toString()
    }
}