package com.calyrsoft.ucbp1.features.profile.domain.model.value

import org.junit.Assert
import org.junit.Test

class ProfileAvatarUrlTest {

    @Test
    fun `ProfileAvatarUrl devuelve valor por defecto si es null`() {
        val avatar = ProfileAvatarUrl(null)
        Assert.assertEquals("https://example.com/default-avatar.png", avatar.orDefault())
    }
}
