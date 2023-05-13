package com.cepotdev.dicory

import com.cepotdev.dicory.logic.model.ListStoryItem

object DataDummy {

    fun generateDummyStoriesResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val stories = ListStoryItem(
                "Photo$i",
                "$i",
                "Post $i",
                "Desc $i",
                1.1,
                "Id $i",
                1.2
            )
            items.add(stories)
        }
        return items
    }
}