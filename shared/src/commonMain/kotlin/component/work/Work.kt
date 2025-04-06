package component.work

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc
import voctrust.shared.generated.resources.personal_project
import voctrust.shared.generated.resources.work_kotlin_material_ui_description
import voctrust.shared.generated.resources.work_kotlin_material_ui_headline

internal data class Work(
    val headline: StringResource,
    val description: StringResource,
    val type: WorkType,
    val links: List<Link>,
    val time: Time,
    val thumbnail: DrawableResource,
)

internal enum class WorkType(val label: StringResource) {
    PersonalProject(Res.string.personal_project),
}

internal data class Link(
    val href: String,
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: String,
)

internal sealed class Time {
    data class UntilNow(val start: Int) : Time() {
        override fun toString() = "$start - Present"
    }
    data class Range(val start: Int, val end: Int) : Time() {
        override fun toString() = "$start - $end"
    }
    data class Event(val year: Int) : Time() {
        override fun toString() = year.toString()
    }
}



internal val KotlinMaterialUi = Work(
    Res.string.work_kotlin_material_ui_headline,
    Res.string.work_kotlin_material_ui_description,
    WorkType.PersonalProject,
    listOf(),
    Time.Range(2019, 2022),
    Res.drawable.logo_voc,
)

internal val Works = listOf(
    KotlinMaterialUi,
)
