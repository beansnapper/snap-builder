package xyz

import io.beansnapper.annotations.SnapBuilder
import java.util.*

@SnapBuilder
class Sample(
    val id : String,
    val num : Int,
    val date : Date
)