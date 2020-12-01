package xyz

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

class SampleTest : StringSpec( {

    "simple test" {
        val sample = Sample(id = "sample-id", num = 12345, date = Date())
        sample.id shouldBe "sample-id"
    }
})