package com.megalogic.tracky.data.asset

object DummyData {
    val itemAsset = listOf(
        AssetResponse(
            title = "Bitcoin",
            image = "https://picsum.photos/500/500",
            description = "The first and most well-known cryptocurrency.",
            price = 30000.0,
            date = "2009-01-03"
        ),
        AssetResponse(
            title = "Ethereum",
            image = "https://picsum.photos/400/400",
            description = "A decentralized platform that runs smart contracts.",
            price = 2000.0,
            date = "2015-07-30"
        ),
        AssetResponse(
            title = "Cardano",
            image = "https://picsum.photos/300/300",
            description = "A proof-of-stake blockchain platform.",
            price = 100.0,
            date = "2017-09-29"
        ),
        AssetResponse(
            title = "AssetResponse 2",
            image = "https://picsum.photos/200/200",
            description = "This is my asset.",
            price = 200.0,
            date = "2023-04-03"
        ),
        AssetResponse(
            title = "AssetResponse 3",
            image = "https://picsum.photos/100/100",
            description = "This is my asset.",
            price = 300.0,
            date = "2023-04-04"
        )
    )
}