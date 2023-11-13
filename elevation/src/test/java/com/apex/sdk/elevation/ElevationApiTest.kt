package com.apex.sdk.elevation

import kotlinx.coroutines.test.runTest
import org.junit.Test

class ElevationApiTest {

    @Test
    fun postGetElevations() = runTest {

        val elevationsRequest = ElevationsRequest(
            locations = listOf(
                Location(
                    latitude = 37.4219983,
                    longitude = -122.084
                )
            )
        )

        val elevationAPI = ElevationAPI("http://0.0.0.0:8080/")
        val locations = elevationAPI.getLocations(elevationsRequest)

        locations.collect {
            print(it)
            assert(it.results.size == 1)
            assert(it.results[0].elevation == 9)
        }
    }
}