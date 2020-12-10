package xyz

import io.beansnapper.annotations.SnapBuilder
import java.util.*

@SnapBuilder
data class Sample(
    val id: String,
    val num: Int,
    val date: Date
)

@SnapBuilder
data class MixedMutableObj(val id: String, var name: String)

@SnapBuilder
data class ExtraProperties(val id: String) {
    var name: String = "name"
}
