package com.example.green_connect

import android.os.Parcel
import android.os.Parcelable

data class Planta(
    var Id_da_planta: Long = 0L,
    var Umidade_do_ar: Float = 0f,
    var Umidade_do_solo: Float = 0f,
    var max_temperatura: Float = 0f,
    var max_umidade: Float = 0f,
    var min_temperatura: Float = 0f,
    var min_umidade: Float = 0f,
    var nome_planta: String = "",
    var temperatura: Float = 0f
) : Parcelable {
    // Construtor necessário para o Firebase
    constructor() : this(
        0L, 0f, 0f, 0f, 0f, 0f, 0f, "", 0f
    )

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(Id_da_planta)
        parcel.writeFloat(Umidade_do_ar)
        parcel.writeFloat(Umidade_do_solo)
        parcel.writeFloat(max_temperatura)
        parcel.writeFloat(max_umidade)
        parcel.writeFloat(min_temperatura)
        parcel.writeFloat(min_umidade)
        parcel.writeString(nome_planta)
        parcel.writeFloat(temperatura)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Planta> {
        override fun createFromParcel(parcel: Parcel): Planta = Planta(parcel)
        override fun newArray(size: Int): Array<Planta?> = arrayOfNulls(size)
    }
}
