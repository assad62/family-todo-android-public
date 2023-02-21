package com.mohammadassad.familytodo.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohammadassad.familytodo.Screen
import com.mohammadassad.familytodo.views.composables.AppLoader
import com.mohammadassad.familytodo.views.composables.CustomAlertDialog
import com.mohammadassad.familytodo.views.composables.EditUpdateTextField
import com.mohammadassad.familytodo.views.composables.ImagePickerView
import kotlinx.coroutines.launch


@Composable
fun Profile(parentNavController: NavController, viewModel: MyProfileViewModel = hiltViewModel()) {

    val showDeleteAccountDialog = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = "", block = {
        viewModel.getProfile()
        viewModel.getFamilyProfile()
    })

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            if(viewModel.familyImage.value.isNullOrEmpty()){
                ImagePickerView(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onSelection = {
                        coroutineScope.launch {
                            if (it != null) {
                                viewModel.uploadImage(it, context){
                                    coroutineScope.launch {
                                        viewModel.getFamilyProfile()
                                    }
                                }
                            }
                        }

                    }
                )
            }
            else{
                ImagePickerView(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    remoteImageUrl = viewModel.familyImage.value,
                    onSelection = {

                        coroutineScope.launch {
                            if (it != null) {
                                viewModel.uploadImage(it, context){
                                    coroutineScope.launch {
                                        viewModel.getFamilyProfile()
                                    }
                                }
                            }
                        }

                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            viewModel.profileName.value?.let { Text(text = it, fontSize = 24.sp) }
            Spacer(modifier = Modifier.height(10.dp))
            viewModel.familyIdentifier.value?.let { FamilyIdField(familyId = it) }

            viewModel.familyName.value?.let {
                EditUpdateTextField(familyName = it) {
                    coroutineScope.launch {
                        viewModel.updateFamilyName(it){
                            coroutineScope.launch {
                                viewModel.getFamilyProfile()
                            }

                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Family Members", style = TextStyle(fontSize = 24.sp))
            if(viewModel.familyMembers.isEmpty()){
                Text(text = "No Family Members. Share your family ID to add family members", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W300))
            }
            else{
                FamilyMemberList(familyMemberList = viewModel.familyMembers)
            }

        }
        if(viewModel.isLoading.value){
            AppLoader()
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(15.dp), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                ),
                onClick = {
                    //code to delete account
                   showDeleteAccountDialog.value = true
                }) {
                Text(
                    text = "Delete Account", fontSize = 18.sp, style = TextStyle(
                        color = Color.White
                    )
                )
            }
        }
    }
    if(showDeleteAccountDialog.value){
        CustomAlertDialog(openDialog = showDeleteAccountDialog, onDelete = {
              coroutineScope.launch{
                  viewModel.deleteUserAccount(){
                      parentNavController.navigate(Screen.LoginScreen.route) {
                          popUpTo(Screen.HomeScreen.route) {
                              inclusive = true

                          }
                      }
                  };
              }
        }, titleText = "Delete Account? This action is irreversible")
    }

}


@Composable
fun FamilyIdField(familyId: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Family ID: $familyId",
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            // Copy family ID to clipboard
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Family ID", familyId))
            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }) {
            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy family ID")
        }
        IconButton(onClick = {
            // Share family ID using a share intent
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, familyId)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share family ID")
            context.startActivity(shareIntent)
        }) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share family ID")
        }
    }
}


@Composable
fun FamilyMemberList(familyMemberList: SnapshotStateList<String?>) {
    Column() {
        familyMemberList.forEach { familyMember ->
            if (familyMember != null) {
                FamilyMemberRow(familyMember)
            }
        }
    }
}
@Composable
fun FamilyMemberRow(familyMember: String) {
    Column(modifier = Modifier
        .padding(all = 10.dp)
        .fillMaxSize(), horizontalAlignment = Alignment.Start) {
        Text(familyMember, fontSize = 18.sp, fontWeight = FontWeight.W300)
    }
}

