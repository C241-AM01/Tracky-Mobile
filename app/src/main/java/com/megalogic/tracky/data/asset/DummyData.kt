package com.megalogic.tracky.data.asset

object DummyData {
    val itemAsset = listOf(
        AssetResponse(
            trackerId = 1,
            title = "Bitcoin",
            image = "https://picsum.photos/500/500",
            description = "The first and most well-known cryptocurrency.",
            depreciation = 5000,
            initialPrice = 500000,
            finalPrice = 495000,
            date = "2009-01-03"
        ),
        AssetResponse(
            trackerId = 1,
            title = "Ethereum",
            image = "https://picsum.photos/400/400",
            description = "A decentralized platform that runs smart contracts.",
            depreciation = 10000,
            initialPrice = 200000,
            finalPrice = 190000,
            date = "2015-07-30"
        ),
        AssetResponse(
            trackerId = 2,
            title = "Cardano",
            image = "https://picsum.photos/300/300",
            description = "A proof-of-stake blockchain platform.",
            depreciation = 5000,
            initialPrice = 20000,
            finalPrice = 15000,
            date = "2017-09-29"
        ),
        AssetResponse(
            trackerId = 3,
            title = "AssetResponse 2",
            image = "https://picsum.photos/200/200",
            depreciation = 20000,
            description = "This is my asset.",
            initialPrice = 100000,
            finalPrice = 80000,
            date = "2023-04-03"
        ),
        AssetResponse(
            trackerId = 3,
            title = "AssetResponse 3",
            image = "https://picsum.photos/100/100",
            depreciation = 10000,
            description = "This is my asset.",
            initialPrice = 720000,
            finalPrice = 710000,
            date = "2023-04-04"
        )
    )
}
