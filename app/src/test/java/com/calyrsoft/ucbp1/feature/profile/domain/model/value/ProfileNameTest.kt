package com.calyrsoft.ucbp1.features.profile.domain.model.value

import org.junit.Test

class ProfileNameTest {

    @Test(expected = IllegalArgumentException::class)
    fun `ProfileName no acepta nombre vacío`() {
        ProfileName("")
    }
}
