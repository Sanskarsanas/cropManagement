package com.example.crop_management

import android.util.Log
import com.google.android.gms.common.api.Response
import com.google.gson.annotations.SerializedName

data class DiseaseResponse(
    @SerializedName("disease_name") val diseaseName: String,
    @SerializedName("description") val description: String,
    @SerializedName("treatment") val treatment: String
)