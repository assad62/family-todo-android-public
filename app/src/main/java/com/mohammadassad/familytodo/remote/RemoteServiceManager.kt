package com.mohammadassad.familytodo.remote

import android.content.SharedPreferences
import com.mohammadassad.familytodo.model.*
import com.mohammadassad.familytodo.services.FamilyTodoService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RemoteServiceManager @Inject constructor(
    private val familyTodoService: FamilyTodoService,
    private val sharedPreferences: SharedPreferences,

) {

    private fun getAuthToken():String?{
        val token = sharedPreferences.getString("access_token", "")
        return "Bearer $token"
    }


    suspend fun addTask(taskRequest: AddTaskRequest):Response<StandardResponse<AddTaskResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.addTask(it, taskRequest)
        }
    }

    suspend fun completeTask(taskId:String, patchRequest: List<PatchRequest>):Response<StandardResponse<PatchResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.completeTask(it, taskId = taskId, patchRequest = patchRequest)
        }
    }

    suspend fun updateTaskName(taskId:String, patchRequest: List<PatchRequest>):Response<StandardResponse<PatchResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.updateTaskName(it, taskId = taskId, patchRequest = patchRequest)
        }
    }

    suspend fun getUserTasks():Response<StandardResponse<Array<UserTask>>>?{
        return getAuthToken()?.let {
             familyTodoService.getInProgressUserTasks(it)
         }
    }

    suspend fun login(loginUserRequest: LoginUserRequest): Response<StandardResponse<LoginUserResponse>> {
        return familyTodoService.login(loginUserRequest)
    }

    suspend fun register(registerUserRequest: RegisterUserRequest): Response<StandardResponse<RegisterUserResponse>> {
        return familyTodoService.register(registerUserRequest)
    }

    suspend fun getFamilyIdentifier():Response<StandardResponse<FamilyIdentifierResponse>>?
    {
        return getAuthToken()?.let {
            familyTodoService.getFamilyIdentifier(it)
        }

    }

    suspend fun getFamilyMembers():Response<StandardResponse<FamilyMemberListResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.getFamilyMembers(it)
        }
    }

    suspend fun getProfile():Response<StandardResponse<ProfileResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.getUserProfile(it)
        }
    }

    suspend fun updateFamilyName(patchRequest: List<PatchRequest>):Response<StandardResponse<PatchResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.updateFamilyName(it,patchRequest);
        }
    }
    suspend fun updateFamilyImage(patchRequest: List<PatchRequest>):Response<StandardResponse<PatchResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.updateFamilyImage(it,patchRequest);
        }
    }

    suspend fun getFamilyName():Response<StandardResponse<FamilyNameResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.getFamilyName(it);
        }
    }

    suspend fun getFamilyProfile():Response<StandardResponse<FamilyProfileResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.getFamilyProfile(it);
        }
    }

    suspend fun uploadImageResponse(body: MultipartBody.Part):Response<StandardResponse<ImageUploadResponse>>?{
        return getAuthToken()?.let {
            familyTodoService.uploadImageResponse(it,body)
        }
    }

    suspend fun deleteTask(taskId: String): Unit? {
        return getAuthToken()?.let {
            familyTodoService.deleteTask(it,taskId)
        }
    }

    suspend fun deleteUserAccount(): Unit? {
        return getAuthToken()?.let {
            familyTodoService.deleteUserAccount(it)
        }
    }

}