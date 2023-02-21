package com.mohammadassad.familytodo.views.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagePickerView(
    modifier: Modifier = Modifier,
    lastSelectedImage: Uri?=null,
    remoteImageUrl:String? = null,
    onSelection: (Uri?) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        onSelection(it)
    }


    if(remoteImageUrl == null){
        Image(
            modifier = modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            painter = rememberAsyncImagePainter(null),
           contentDescription = "Profile Picture",
           contentScale = ContentScale.Crop)
    }
    else{
        Image(
            modifier = modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            painter = rememberAsyncImagePainter(remoteImageUrl),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop)
    }



}