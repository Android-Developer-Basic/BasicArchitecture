package ru.otus.basicarchitecture.api

import com.google.gson.annotations.SerializedName

data class AddressSuggestionsResponse(
    @SerializedName("suggestions")
    val suggestions: List<Suggestion?>?
) {
    data class Suggestion(
        @SerializedName("data")
        val `data`: Data?,
        @SerializedName("unrestricted_value")
        val unrestrictedValue: String?,
        @SerializedName("value")
        val value: String?
    ) {
        data class Data(
            @SerializedName("area")
            val area: Any?,
            @SerializedName("area_fias_id")
            val areaFiasId: Any?,
            @SerializedName("area_kladr_id")
            val areaKladrId: Any?,
            @SerializedName("area_type")
            val areaType: Any?,
            @SerializedName("area_type_full")
            val areaTypeFull: Any?,
            @SerializedName("area_with_type")
            val areaWithType: Any?,
            @SerializedName("beltway_distance")
            val beltwayDistance: Any?,
            @SerializedName("beltway_hit")
            val beltwayHit: Any?,
            @SerializedName("block")
            val block: Any?,
            @SerializedName("block_type")
            val blockType: Any?,
            @SerializedName("block_type_full")
            val blockTypeFull: Any?,
            @SerializedName("capital_marker")
            val capitalMarker: String?,
            @SerializedName("city")
            val city: String?,
            @SerializedName("city_area")
            val cityArea: Any?,
            @SerializedName("city_district")
            val cityDistrict: Any?,
            @SerializedName("city_district_fias_id")
            val cityDistrictFiasId: Any?,
            @SerializedName("city_district_kladr_id")
            val cityDistrictKladrId: Any?,
            @SerializedName("city_district_type")
            val cityDistrictType: Any?,
            @SerializedName("city_district_type_full")
            val cityDistrictTypeFull: Any?,
            @SerializedName("city_district_with_type")
            val cityDistrictWithType: Any?,
            @SerializedName("city_fias_id")
            val cityFiasId: String?,
            @SerializedName("city_kladr_id")
            val cityKladrId: String?,
            @SerializedName("city_type")
            val cityType: String?,
            @SerializedName("city_type_full")
            val cityTypeFull: String?,
            @SerializedName("city_with_type")
            val cityWithType: String?,
            @SerializedName("country")
            val country: String?,
            @SerializedName("country_iso_code")
            val countryIsoCode: String?,
            @SerializedName("divisions")
            val divisions: Any?,
            @SerializedName("entrance")
            val entrance: Any?,
            @SerializedName("federal_district")
            val federalDistrict: Any?,
            @SerializedName("fias_actuality_state")
            val fiasActualityState: String?,
            @SerializedName("fias_code")
            val fiasCode: Any?,
            @SerializedName("fias_id")
            val fiasId: String?,
            @SerializedName("fias_level")
            val fiasLevel: String?,
            @SerializedName("flat")
            val flat: Any?,
            @SerializedName("flat_area")
            val flatArea: Any?,
            @SerializedName("flat_cadnum")
            val flatCadnum: Any?,
            @SerializedName("flat_fias_id")
            val flatFiasId: Any?,
            @SerializedName("flat_price")
            val flatPrice: Any?,
            @SerializedName("flat_type")
            val flatType: Any?,
            @SerializedName("flat_type_full")
            val flatTypeFull: Any?,
            @SerializedName("floor")
            val floor: Any?,
            @SerializedName("geo_lat")
            val geoLat: String?,
            @SerializedName("geo_lon")
            val geoLon: String?,
            @SerializedName("geoname_id")
            val geonameId: String?,
            @SerializedName("history_values")
            val historyValues: List<String?>?,
            @SerializedName("house")
            val house: Any?,
            @SerializedName("house_cadnum")
            val houseCadnum: Any?,
            @SerializedName("house_fias_id")
            val houseFiasId: Any?,
            @SerializedName("house_flat_count")
            val houseFlatCount: Any?,
            @SerializedName("house_kladr_id")
            val houseKladrId: Any?,
            @SerializedName("house_type")
            val houseType: Any?,
            @SerializedName("house_type_full")
            val houseTypeFull: Any?,
            @SerializedName("kladr_id")
            val kladrId: String?,
            @SerializedName("metro")
            val metro: Any?,
            @SerializedName("okato")
            val okato: String?,
            @SerializedName("oktmo")
            val oktmo: String?,
            @SerializedName("postal_box")
            val postalBox: Any?,
            @SerializedName("postal_code")
            val postalCode: Any?,
            @SerializedName("qc")
            val qc: Any?,
            @SerializedName("qc_complete")
            val qcComplete: Any?,
            @SerializedName("qc_geo")
            val qcGeo: String?,
            @SerializedName("qc_house")
            val qcHouse: Any?,
            @SerializedName("region")
            val region: String?,
            @SerializedName("region_fias_id")
            val regionFiasId: String?,
            @SerializedName("region_iso_code")
            val regionIsoCode: String?,
            @SerializedName("region_kladr_id")
            val regionKladrId: String?,
            @SerializedName("region_type")
            val regionType: String?,
            @SerializedName("region_type_full")
            val regionTypeFull: String?,
            @SerializedName("region_with_type")
            val regionWithType: String?,
            @SerializedName("settlement")
            val settlement: Any?,
            @SerializedName("settlement_fias_id")
            val settlementFiasId: Any?,
            @SerializedName("settlement_kladr_id")
            val settlementKladrId: Any?,
            @SerializedName("settlement_type")
            val settlementType: Any?,
            @SerializedName("settlement_type_full")
            val settlementTypeFull: Any?,
            @SerializedName("settlement_with_type")
            val settlementWithType: Any?,
            @SerializedName("source")
            val source: Any?,
            @SerializedName("square_meter_price")
            val squareMeterPrice: Any?,
            @SerializedName("stead")
            val stead: Any?,
            @SerializedName("stead_cadnum")
            val steadCadnum: Any?,
            @SerializedName("stead_fias_id")
            val steadFiasId: Any?,
            @SerializedName("stead_type")
            val steadType: Any?,
            @SerializedName("stead_type_full")
            val steadTypeFull: Any?,
            @SerializedName("street")
            val street: String?,
            @SerializedName("street_fias_id")
            val streetFiasId: String?,
            @SerializedName("street_kladr_id")
            val streetKladrId: String?,
            @SerializedName("street_type")
            val streetType: String?,
            @SerializedName("street_type_full")
            val streetTypeFull: String?,
            @SerializedName("street_with_type")
            val streetWithType: String?,
            @SerializedName("tax_office")
            val taxOffice: String?,
            @SerializedName("tax_office_legal")
            val taxOfficeLegal: String?,
            @SerializedName("timezone")
            val timezone: Any?,
            @SerializedName("unparsed_parts")
            val unparsedParts: Any?
        )
    }
}