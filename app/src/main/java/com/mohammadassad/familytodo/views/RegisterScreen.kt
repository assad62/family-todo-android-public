package com.mohammadassad.familytodo.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohammadassad.familytodo.Screen
import com.mohammadassad.familytodo.views.composables.AppLoader
import com.mohammadassad.familytodo.views.composables.ErrorDialog
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(

    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),

){
    var showErrorDialog by  remember { mutableStateOf(false) }
    var familyIdentifier by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var lastName by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var firstName by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(
        TextFieldValue("")
    ) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.padding(30.dp),
                text = "Family Todo",
                fontSize = 40.sp,
                color = Color.Black,
                fontFamily = FontFamily.Cursive
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                placeholder = { Text(text = "First Name") },
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                placeholder = { Text(text = "Last Name") },
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = { Text(text = "Email") },
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Text(
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                text="Passwords must contain an uppercase character, lowercase character, a digit, and a non-alphanumeric character. Passwords must be at least six characters long. ")
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                value = familyIdentifier,
                onValueChange = {
                    familyIdentifier = it
                },
                placeholder = { Text(text = "Family Identifier") },
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(15.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.register(
                            firstName = firstName.text.trim(), lastName = lastName.text.trim(), email = email.text.trim(), password= password.text.trim(), familyIdentifier = familyIdentifier.text.trim()
                        ){
                            Log.d("9910","7 "+it)
                            if(it){
                                navController.navigate(Screen.HomeScreen.route)
                            }
                            else{
                                showErrorDialog = true
                            }
                        };
                    }

                    //navController.navigate(Screen.HomeScreen.route)
                    //your onclick code here
                }) {
                Text(text = "Register", fontSize = 18.sp,)
            }
            if (showErrorDialog) {
                ErrorDialog(title = "Cannot Register", text = "Please try to register again"){
                    showErrorDialog = false
                }
            }

        }
       if( viewModel.isLoading.value){
           AppLoader()
       }
    }

}