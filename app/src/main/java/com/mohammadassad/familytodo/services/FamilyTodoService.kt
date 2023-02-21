package com.mohammadassad.familytodo.services

import androidx.room.Delete
import com.mohammadassad.familytodo.model.*
import com.mohammadassad.familytodo.remote.StandardResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FamilyTodoService {

    @DELETE("user")
    suspend fun deleteUserAccount(
        @Header("Authorization")authHeader:String,
    )


    @Multipart
    @POST("family/image/upload")
  suspend fun uploadImageResponse(
        @Header("Authorization")authHeader:String,
        @Part  file: MultipartBody.Part,
  ):Response<StandardResponse<ImageUploadResponse>>

    @GET("family/family_profile")
    suspend fun getFamilyProfile(
        @Header("Authorization")authHeader:String,

        ):Response<StandardResponse<FamilyProfileResponse>>

    @GET("family/name")
    suspend fun getFamilyName(
        @Header("Authorization")authHeader:String,

    ):Response<StandardResponse<FamilyNameResponse>>

    @PATCH("family/image")
    suspend fun updateFamilyImage(
        @Header("Authorization")authHeader:String,
        @Body patchRequest: List<PatchRequest>
    ):Response<StandardResponse<PatchResponse>>

    @PATCH("family/name")
    suspend fun updateFamilyName(
        @Header("Authorization")authHeader:String,
        @Body patchRequest: List<PatchRequest>
    ):Response<StandardResponse<PatchResponse>>


    @GET("user/profile")
    suspend fun getUserProfile(
        @Header("Authorization")authHeader:String
    ):Response<StandardResponse<ProfileResponse>>

    @GET("family/members")
    suspend fun getFamilyMembers(
        @Header("Authorization")authHeader:String
    ):Response<StandardResponse<FamilyMemberListResponse>>

    @GET("family/family_identifier")
    suspend fun getFamilyIdentifier(
        @Header("Authorization")authHeader:String
    ):Response<StandardResponse<FamilyIdentifierResponse>>

    @POST("task")
    suspend fun addTask(
        @Header("Authorization")authHeader:String,
        @Body addTaskRequest: AddTaskRequest
    ):Response<StandardResponse<AddTaskResponse>>

    @PATCH("task/{taskId}")
    suspend fun completeTask(
        @Header("Authorization")authHeader:String,
        @Path("taskId") taskId:String,
        @Body patchRequest: List<PatchRequest>
    ):Response<StandardResponse<PatchResponse>>

   //same as complete task since we are simply patching one value
    @PATCH("task/{taskId}")
    suspend fun updateTaskName(
        @Header("Authorization")authHeader:String,
        @Path("taskId") taskId:String,
        @Body patchRequest: List<PatchRequest>
    ):Response<StandardResponse<PatchResponse>>

    @DELETE("task/{taskId}")
    suspend fun deleteTask(
        @Header("Authorization")authHeader:String,
        @Path("taskId") taskId:String,
    )


    @GET("task/familytasks/in_progress")
    suspend fun getInProgressUserTasks(
        @Header("Authorization")authHeader:String
    ): Response<StandardResponse<Array<UserTask>>>

    @POST("account/register")
    suspend fun register(
       @Body registerUserRequest: RegisterUserRequest
    ): Response<StandardResponse<RegisterUserResponse>>

    @POST("account/login")
    suspend fun login(
        @Body loginUserRequest: LoginUserRequest
    ): Response<StandardResponse<LoginUserResponse>>
}