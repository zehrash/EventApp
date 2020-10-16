package com.example.eventapp.model.data

data class PerformerData(
    override var name: String,
    var type: String,
    override var id: String,
    var slug: String
) : DataInterface