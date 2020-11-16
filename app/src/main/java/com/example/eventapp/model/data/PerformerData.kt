package com.example.eventapp.model.data

data class PerformerData(
    override var name: String,
    var genre: String,
    override var id: String,
    var slug: String
) : DataInterface