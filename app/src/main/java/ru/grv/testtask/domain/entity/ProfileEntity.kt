package ru.grv.testtask.domain.entity

import androidx.room.*

@Entity(tableName = "profile")
data class ProfileEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "lastName") var lastName: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "birthDate") var birthDate: String,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "email") var email: String
)
