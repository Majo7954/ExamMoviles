package com.calyrsoft.ucbp1.features.profile.domain.model.value

import org.junit.Assert
import org.junit.Test

class ProfileEmailTest {

    @Test
    fun `ProfileEmail acepta un correo válido`() {
        val email = ProfileEmail("test@ucb.edu.bo")
        Assert.assertEquals("test@ucb.edu.bo", email.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `ProfileEmail lanza excepción si no contiene arroba`() {
        ProfileEmail("correo_invalido")
    }
}
