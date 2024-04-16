package com.example.carsearch.common

import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.core.model.main.Year

object TestData {

    private const val DEFAULT_PAGE_SIZE = 15
    private const val LONG_PAGE_SIZE = 2147483647
    private const val DEFAULT_TOTAL_PAGES = 6
    private const val SINGLE_PAGE = 1

    //Manufacturer Methods
    fun getManufacturerResponseJson(): String {
        return getResponseJson("""
            "107": "Bentley",
            "125": "Borgward",
            "130": "BMW",
            "141": "Buick",
            "145": "Brilliance",
            "150": "Cadillac",
            "157": "Caterham",
            "160": "Chevrolet",
            "020": "Abarth",
            "040": "Alfa Romeo",
            "042": "Alpina",
            "043": "Alpine",
            "057": "Aston Martin",
            "060": "Audi",
            "095": "Barkas"
        """)
    }

    fun getManufacturersAsDto(): List<ManufacturerDto> {
        return listOf(
            ManufacturerDto("107", "Bentley"),
            ManufacturerDto("125", "Borgward"),
            ManufacturerDto("130", "BMW"),
            ManufacturerDto("141", "Buick"),
            ManufacturerDto("145", "Brilliance"),
            ManufacturerDto("150", "Cadillac"),
            ManufacturerDto("157", "Caterham"),
            ManufacturerDto("160", "Chevrolet"),
            ManufacturerDto("20", "Abarth"),
            ManufacturerDto("40", "Alfa Romeo"),
            ManufacturerDto("42", "Alpina"),
            ManufacturerDto("43", "Alpine"),
            ManufacturerDto("57", "Aston Martin"),
            ManufacturerDto("60", "Audi"),
            ManufacturerDto("95", "Barkas")
        )
    }

    fun getManufacturersAsDomainModels(): List<Manufacturer> {
        return listOf(
            Manufacturer(60, "Audi"),
            Manufacturer(160, "Chevrolet"),
            Manufacturer(150, "Cadillac"),
            Manufacturer(40, "Alfa Romeo"),
            Manufacturer(95, "Barkas"),
            Manufacturer(130, "BMW"),
            Manufacturer(141, "Buick"),
            Manufacturer(20, "Abarth"),
            Manufacturer(42, "Alpina"),
            Manufacturer(43, "Alpine"),
            Manufacturer(145, "Brilliance"),
            Manufacturer(57, "Aston Martin"),
            Manufacturer(157, "Caterham"),
            Manufacturer(125, "Borgward"),
            Manufacturer(107, "Bentley")
        )
    }

    //Model Methods
    fun getModelResponseJson(): String {
        return getResponseJson("""
            "1er": "1er",
            "2er": "2er",
            "3er": "3er",
            "4er": "4er",
            "5er": "5er",
            "6er": "6er",
            "7er": "7er",
            "8er": "8er",
            "i3": "i3",
            "i8": "i8",
            "X1": "X1",
            "X2": "X2",
            "X3": "X3",
            "X4": "X4",
            "X5": "X5",
            "X6": "X6",
            "X7": "X7",
            "Z1": "Z1",
            "Z3": "Z3",
            "Z4": "Z4",
            "Z8": "Z8"
        """, LONG_PAGE_SIZE, SINGLE_PAGE)
    }

    fun getModelAsDomainModels(): List<Model> {
        return listOf(
            Model("3er", "3er"),
            Model("4er", "4er"),
            Model("5er", "5er"),
            Model("6er", "6er"),
            Model("7er", "7er"),
            Model("8er", "8er"),
            Model("i3", "i3"),
            Model("i8", "i8"),
            Model("Z1", "Z1"),
            Model("X1", "X1"),
            Model("Z3", "Z3"),
            Model("X2", "X2"),
            Model("Z4", "Z4"),
            Model("X3", "X3"),
            Model("X4", "X4"),
            Model("X5", "X5"),
            Model("1er", "1er"),
            Model("X6", "X6"),
            Model("Z8", "Z8"),
            Model("2er", "2er"),
            Model("X7", "X7")
        )
    }

    fun getModelAsDto(): List<ModelDto> {
        return listOf(
            ModelDto("1er", "1er"),
            ModelDto("2er", "2er"),
            ModelDto("3er", "3er"),
            ModelDto("4er", "4er"),
            ModelDto("5er", "5er"),
            ModelDto("6er", "6er"),
            ModelDto("8er", "8er"),
            ModelDto("i3", "i3"),
            ModelDto("i8", "i8"),
            ModelDto("X1", "X1"),
            ModelDto("X2", "X2"),
            ModelDto("X3", "X3"),
            ModelDto("X4", "X4"),
            ModelDto("X5", "X5"),
            ModelDto("X6", "X6"),
            ModelDto("X7", "X7"),
            ModelDto("Z1", "Z1"),
            ModelDto("Z3", "Z3"),
            ModelDto("Z4", "Z4"),
            ModelDto("Z8", "Z8")
        )
    }

    //Year Methods
    fun getCarYearResponseJson(): String {
        return """{
          "wkda": {
            "2014": "2014",
            "2015": "2015",
            "2016": "2016",
            "2017": "2017",
            "2018": "2018",
            "2019": "2019"
          }
        }""".trim()
    }

    fun getYearsAsDomainModels(): List<Year> {
        return listOf(
            Year(2019, "2019"),
            Year(2018, "2018"),
            Year(2017, "2017"),
            Year(2016, "2016"),
            Year(2015, "2015"),
            Year(2014, "2014")
        )
    }

    fun getYearsAsDto(): List<YearDto> {
        return listOf(
            YearDto("2019", "2019"),
            YearDto("2018", "2018"),
            YearDto("2017", "2017"),
            YearDto("2016", "2016"),
            YearDto("2015", "2015"),
            YearDto("2014", "2014")
        )
    }

    //JSON Utilities
    fun getResponseJson(jsonObj: String, pageSize: Int = DEFAULT_PAGE_SIZE, totalPages: Int = DEFAULT_TOTAL_PAGES): String {
        return """
          {
            "page": 0,
            "pageSize": $pageSize,
            "totalPageCount": $totalPages,
            "wkda": {$jsonObj}
          }
        """.trim()
    }
}