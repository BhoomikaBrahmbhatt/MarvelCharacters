package com.android.test.marvelcharacters.mvvm

import com.google.gson.annotations.SerializedName;

data class MarvelCharacterData (

    @SerializedName("code"            ) var code            : Int?    = null,
    @SerializedName("status"          ) var status          : String? = null,
    @SerializedName("copyright"       ) var copyright       : String? = null,
    @SerializedName("attributionText" ) var attributionText : String? = null,
    @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
    @SerializedName("etag"            ) var etag            : String? = null,
    @SerializedName("data"            ) var data            : Data?   = Data()

)
data class Thumbnail (

    @SerializedName("path"      ) var path      : String? = null,
    @SerializedName("extension" ) var extension : String? = null

)

data class Items (

    @SerializedName("resourceURI" ) var resourceURI : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("type"        ) var type        : String? = null

)


data class Stories (

    @SerializedName("available"     ) var available     : Int?             = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf(),
    @SerializedName("returned"      ) var returned      : Int?             = null

)

data class Urls (

    @SerializedName("type" ) var type : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)


data class Results (

    @SerializedName("id"          ) var id          : Int?            = null,
    @SerializedName("name"        ) var name        : String?         = null,
    @SerializedName("description" ) var description : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("comics"      ) var comics      : Comics?         = Comics(),
    @SerializedName("series"      ) var series      : Series?         = Series(),
    @SerializedName("stories"     ) var stories     : Stories?        = Stories(),
    @SerializedName("events"      ) var events      : Events?         = Events(),
    @SerializedName("urls"        ) var urls        : ArrayList<Urls> = arrayListOf()

)


data class Data (

    @SerializedName("offset"  ) var offset  : Int?               = null,
    @SerializedName("limit"   ) var limit   : Int?               = null,
    @SerializedName("total"   ) var total   : Int?               = null,
    @SerializedName("count"   ) var count   : Int?               = null,
    @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)

data class Events (

    @SerializedName("available"     ) var available     : Int?             = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf(),
    @SerializedName("returned"      ) var returned      : Int?             = null

)
data class Comics (

    @SerializedName("available"     ) var available     : Int?             = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf(),
    @SerializedName("returned"      ) var returned      : Int?             = null

)



data class Series (

    @SerializedName("available"     ) var available     : Int?             = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf(),
    @SerializedName("returned"      ) var returned      : Int?             = null

)
