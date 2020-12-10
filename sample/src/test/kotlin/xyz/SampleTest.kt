package xyz

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import java.util.*

class SampleTest : StringSpec({

    "sample test" {
        val sample = Sample(id = "sample-id", num = 12345, date = Date())
        sample.id shouldBe "sample-id"
        val s2 = sample.toBuilder().apply {
            id = "sample2"
            num = 67890
            date = Date()
        }.build()
        s2.id shouldBe "sample2"
        s2.num shouldBe 67890
        s2.date.time shouldBeGreaterThanOrEqual sample.date.time
    }

    "mixed mutable test" {
        val mixed = MixedMutableObj("id", "Kurt")
        mixed.name = "Joe"
        val m2 = mixed.toBuilder().apply {
            id = "new"
            name = "Mary"
        }.build()

        m2.id shouldBe "new"
        m2.name shouldBe "Mary"
    }


})