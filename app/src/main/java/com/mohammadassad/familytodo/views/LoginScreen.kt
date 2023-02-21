package com.mohammadassad.familytodo.views

import android.content.SharedPreferences
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mohammadassad.familytodo.Screen
import com.mohammadassad.familytodo.firebase.FirebaseMessagingModule
import com.mohammadassad.familytodo.views.composables.AppLoader
import com.mohammadassad.familytodo.views.composables.ErrorDialog
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController ,
                viewModel: LoginViewModel = hiltViewModel(),

){
    var showErrorDialog by  remember { mutableStateOf(false) }
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "", block = {
        viewModel.initFirebaseMessaging()
        viewModel.checkIfJWTIsValid{
                if(it){
                    navController.navigate(Screen.HomeScreen.route)
                }
            }
    })

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
                value = email,
                onValueChange = {
                    email = it
                }, placeholder = { Text(text = "Email") },
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
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

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(15.dp),
                onClick = {
                    coroutineScope.launch {

                        viewModel.login(email = email.text.trim(), password= password.text.trim()){
                            if(it){
                                navController.navigate(Screen.HomeScreen.route)
                            }
                            else{
                                showErrorDialog = true
                                //call error popup
                            }
                        }



                    }

                }) {
                Text(text = "Login",  fontSize = 18.sp,)
            }
            Box(modifier = Modifier.height(40.dp)){}
            Row() {
                Text(text = "Dont have an account? ",  fontSize = 18.sp,)

                ClickableText(
                    style = TextStyle(fontSize = 18.sp, color = Color.Blue),
                    text = AnnotatedString("Register now") ,
                    onClick = {

                        navController.navigate(Screen.RegisterScreen.route)
                    })
            }

        }
        if(viewModel.isLoading.value){
            AppLoader()
        }
        if (showErrorDialog) {
            ErrorDialog(title = "Cannot login", text = "Please check username and password"){
                showErrorDialog = false
            }
        }

    }

}