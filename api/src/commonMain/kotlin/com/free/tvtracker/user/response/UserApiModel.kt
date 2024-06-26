package com.free.tvtracker.user.response

import com.free.tvtracker.base.ApiError
import com.free.tvtracker.base.ApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    @SerialName("created_at_datetime")
    val createdAtDatetime: String,
    val id: Int,
    val username: String?,
    val email: String?,
    @SerialName("preferences_push_allowed")
    val preferencesPushAllowed: Boolean,
    @SerialName("is_anon")
    val isAnonymous: Boolean,
)

@Serializable
data class UserApiResponse(
    override val data: UserApiModel? = null,
    override val application_error: ApiError? = null
) : ApiResponse<UserApiModel>() {

    companion object {
        fun ok(data: UserApiModel): UserApiResponse {
            return UserApiResponse(data = data)
        }

        fun error(application_error: ApiError): UserApiResponse {
            return UserApiResponse(application_error = application_error)
        }
    }
}
