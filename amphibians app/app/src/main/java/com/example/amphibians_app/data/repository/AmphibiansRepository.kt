package com.example.amphibians_app.data.repository

import com.example.amphibians_app.model.Amphibian


//  Repository that fetch mars photos list from Amphibians Api.
interface AmphibiansRepository {
  suspend fun getAmphibians(): List<Amphibian>
}