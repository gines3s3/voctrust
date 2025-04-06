package model

data class Member(
    var name: String,
    var title: String,
    var description: String = "",
    var imageUrl: String = "",
    var links: List<Link> = listOf(),
) {
    data class Link(
        var href: String,
        var label: String,
        var linkType: LinkType
    )
}

