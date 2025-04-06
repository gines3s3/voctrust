package model

object Members {

    val memberDeva = Member (
        name = "Devakumar C",
        title = "TRUSTEE",
        links = listOf(
            Member.Link(
                href = "https://www.instagram.com/vocyouthsocialwelfare/",
                label = "@vocyouthsocialwelfare",
                linkType = LinkType.Instagram
            ),
            Member.Link(
                href = "tel:+9194873806440",
                label = "Call Us",
                linkType = LinkType.Phone
            ),
        )
    )

    val memberGaneshS = Member (
        name = "Ganesh S",
        title = "FOUNDER CUM MANAGING TRUSTEE",
        links = listOf(
            Member.Link(
                href = "tel:+918124003900",
                label = "Call Us",
                linkType = LinkType.Phone
            ),
        )
    )

    val memberMuruganS = Member (
        name = "Murugan S",
        title = "TRUSTEE",
        links = listOf(
            Member.Link(
                href = "tel:+919442718319",
                label = "Call Us",
                linkType = LinkType.Phone
            ),
        )
    )
}