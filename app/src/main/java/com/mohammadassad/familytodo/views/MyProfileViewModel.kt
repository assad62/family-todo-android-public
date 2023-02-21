package com.mohammadassad.familytodo.views

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammadassad.familytodo.Extensions.getName
import com.mohammadassad.familytodo.model.PatchRequest
import com.mohammadassad.familytodo.remote.RemoteServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import java.io.File
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val remoteServiceManager: RemoteServiceManager) : ViewModel()  {

    val familyImage = mutableStateOf<String?>("")
    val familyIdentifier = mutableStateOf<String?>("")
    val profileName = mutableStateOf<String?>("")
    val familyName = mutableStateOf<String?>("")
    val familyMembers = mutableStateListOf<String?>("")
    val isLoading = mutableStateOf(false)

    suspend fun getFamilyId(){
         viewModelScope.launch {
             val response = remoteServiceManager.getFamilyIdentifier()
             if (response != null) {
                 if(response.isSuccessful){
                        familyIdentifier.value = response.body()?.data?.familyIdentifier ?:""
                 }
             }
         }
    }


    suspend fun getProfile() {
        viewModelScope.launch {

            val response = remoteServiceManager.getProfile()
            if (response != null) {
                if(response.isSuccessful){
                    response.body()?.data?.name?.let {
                        profileName.value = it
                    }

                }

            }
        }
    }

    suspend fun getFamilyMembers() {
        viewModelScope.launch {
            familyMembers.removeAll(elements = familyMembers)
            val response = remoteServiceManager.getFamilyMembers()
            if (response != null) {
                if(response.isSuccessful){
                    response.body()?.data?.familyMembers?.let {
                        familyMembers.addAll(it)
                    }

                }

            }
        }
    }

    suspend fun getFamilyName(){

            val response = remoteServiceManager.getFamilyName()
            if (response != null) {
                if(response.isSuccessful){
                    response.body()?.data?.
                    let {
                        familyName.value = it.familyName
                    }

                }

            }

    }

    suspend fun updateFamilyName(newName:String, onResult: () -> Unit){
        val newNamePatch = PatchRequest(
            op = "replace",
            path = "name",
            value = newName
        )
        remoteServiceManager.updateFamilyName(patchRequest = listOf(newNamePatch) )
        onResult()
    }

    suspend fun getFamilyProfile() {
            isLoading.value = true
            familyMembers.removeAll(elements = familyMembers)
            val response = remoteServiceManager.getFamilyProfile()
            if (response != null) {
                if(response.isSuccessful){
                    response.body()?.data?.let {
                        familyIdentifier.value = it.familyIdentifier
                        familyName.value = it.name
                        familyMembers.addAll(it.listOfFamilyMembers)
                        familyImage.value = it.image
                    }

                }

            }
        isLoading.value = false

    }

    suspend fun uploadImage(uri: Uri, context: Context, onResult:()->Unit): Boolean {
        isLoading.value = true

        val fileName = uri.getName(context = context)
        val file = fileName?.let {
            createTmpFileFromUri(context, uri, fileName = it)
        }
        return withContext(Dispatchers.IO) {
            return@withContext try {

                val response = file?.let {
                    MultipartBody.Part.createFormData(
                        "file",
                        file.name,
                        it.asRequestBody()
                    )
                }?.let {
                    remoteServiceManager.uploadImageResponse(
                        it
                    )
                }
                if (response != null) {
                    if(response.isSuccessful) {
                        response.body()?.data?.url?.let {
                            patchFamilyImage(it,onResult)

                        }
                    }

                }

                true
            } catch (e: HttpException) {

                false
            } catch (e: IOException) {

                false
            }
        }
    }


    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, File(fileName).extension , context.cacheDir)
            org.apache.commons.io.FileUtils.copyInputStreamToFile(stream,file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun patchFamilyImage(familyImageUrl:String,onResult:()->Unit){
        val newNamePatch = PatchRequest(
            op = "replace",
            path = "image",
            value = familyImageUrl
        )
        val response = remoteServiceManager.updateFamilyImage(patchRequest = listOf(newNamePatch) )
        onResult()
        isLoading.value = false
    }


     suspend fun deleteUserAccount(onResult: () -> Unit){
         isLoading.value = true
         val response = remoteServiceManager.deleteUserAccount()
         clearToken()
         isLoading.value = false
         onResult()


     }

    private fun clearToken(){

        sharedPreferences.edit().remove("access_token").apply()
    }
}


