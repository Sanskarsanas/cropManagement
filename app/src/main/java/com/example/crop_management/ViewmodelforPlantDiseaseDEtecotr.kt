package com.example.crop_management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Disease(val name: String)

@Serializable
data class ApiResponse(val diseases: List<Disease>)
//
//class PlantDiseaseViewModel : ViewModel() {
//    private val client = HttpClient(CIO) {
//        // No need to install JsonFeature in latest Ktor versions
//        // Configure JSON serialization directly
//    }
//
//    private val _diseases = MutableStateFlow<List<Disease>>(emptyList())
//    val diseases: StateFlow<List<Disease>> = _diseases
//
//    fun fetchDiseases() {
//        viewModelScope.launch {
//            val base64Image="C:\\Users\\sanskar sanas\\OneDrive\\Pictures\\Screenshots"
//            try {
//                val url =
//                    "https://ondrej-vesely-plant-identification-v1.p.rapidapi.com/api/v3/health_assessment?details=local_name%2Cdescription%2Curl%2Ctreatment%2Cclassification%2Ccommon_names%2Ccause&async=true&language=en&full_disease_list=true"
//                val body = """
//                    {
//                        "images": ["data:image/png;base64,$base64Image"],
//                        "latitude": 49.207,
//                        "longitude": 16.608,
//                        "similar_images": true
//                    }
//                """.trimIndent()
//
//                val response: HttpResponse = client.post(url) {
//                    headers {
//                        append(
//                            "x-rapidapi-key",
//                            "d42ce953f4msh0811c294a79b436p1e164ejsncfedaf333068"
//                        )
//                        append(
//                            "x-rapidapi-host",
//                            "ondrej-vesely-plant-identification-v1.p.rapidapi.com"
//                        )
//                        append("Content-Type", "application/json")
//                    }
//                    setBody(body)
//                }
//
//                val responseBody = response.bodyAsText()
//                val jsonResponse =
//                    Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(responseBody)
//                _diseases.value = jsonResponse.diseases
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}
