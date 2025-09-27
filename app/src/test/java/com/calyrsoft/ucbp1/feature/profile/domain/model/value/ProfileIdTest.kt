package com.calyrsoft.ucbp1.features.profile.domain.model.value

import org.junit.Assert
import org.junit.Test

class ProfileIdTest {

    @Test
    fun `ProfileId acepta un string válido`() {
        val id = ProfileId("user_123")
        Assert.assertEquals("user_123", id.value)
    }
}
