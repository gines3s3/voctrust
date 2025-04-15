package model

object Services {
    val animalRescue = Service(
        title = "Animal Rescue & Welfare",
        description = "We rescue and care for injured, abandoned, and stray animals—especially dogs. Our mission is to ensure every animal gets the love, shelter, and treatment it deserves."
    )

    val templeRestoration = Service(
        title = "Temple Restoration",
        description = "Preserving spiritual heritage through the restoration of ancient and neglected temples. We revive cultural landmarks to keep traditions alive for future generations."
    )

    val humanitarianAid = Service(
        title = "Humanitarian Aid & Philanthropy",
        description = "Supporting underprivileged children and the elderly with food, clothing, education, and care. We believe in restoring dignity and providing hope through selfless giving."
    )

    val allServices = listOf(animalRescue, templeRestoration, humanitarianAid)
}