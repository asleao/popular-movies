package br.com.popularmovies.utils

import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.InstantExecutorExtension
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.core.network.retrofit.model.Error
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class ResourceExtensionsTest {

    lateinit var success: MutableLiveData<Boolean>
    lateinit var error: MutableLiveData<Error>
    @BeforeEach
    fun setupBeforeEach() {
        success = MutableLiveData()
        error = MutableLiveData()
    }

    @Nested
    inner class ValidateResponse {
        @Test
        fun `em caso de sucesso, success deve ser preenchido`() {
            val resource = Resource.success(true)
            resource.validateResponse(success, error)
            assertThat(success.value).isTrue()
            assertThat(error.value).isNull()
        }

        @Test
        fun `em caso de erro, error deve ser preenchido`() {
            val resource = Resource.error<Boolean>(Error(-1, "", ""))
            resource.validateResponse(success, error)
            assertThat(success.value).isNull()
            assertThat(error.value).isNotNull()
        }

        @Test
        fun `caso de sucesso e o data estiver nulo, o livedata de sucesso nao deve ser alimentado`() {
            val resource = mockk<Resource<Boolean>>()
            every { resource.status } answers { Resource.Status.SUCCESS }
            every { resource.data } answers { null }

            resource.validateResponse(success, error)

            assertThat(resource.data).isNull()
            assertThat(error.value).isNull()
        }

        @Test
        fun `caso de erro e o error estiver nulo,o livedata de erro nao deve ser alimentado`() {
            val resource = mockk<Resource<Boolean>>()
            every { resource.status } answers { Resource.Status.ERROR }
            every { resource.error } answers { null }

            resource.validateResponse(success, error)

            assertThat(resource.error).isNull()
            assertThat(error.value).isNull()
        }
    }
}