package com.hockey.ui.screens

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comhockey.ui.theme.ComhockeyTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComhockeyTheme {


                Modifier.background(color = Color.Blue)
                var TeamName by remember {
                    mutableStateOf("")
                }
                var TeamNames by remember {
                    mutableStateOf(listOf<String>())

                }


                var ManagerName by remember {
                    mutableStateOf("")
                }
                var ManagerNames by remember {
                    mutableStateOf(listOf<String>())

                }

                var Contact by remember {
                    mutableStateOf("")
                }
                var Contacts by remember {
                    mutableStateOf(listOf<Int>())

                }
                var EmailAdress by remember {
                    mutableStateOf("")
                }
                var EmailAdresses by remember {
                    mutableStateOf(listOf<String>())

                }
                var MobileNumber by remember {
                    mutableStateOf("")
                }
                var MobileNumbers by remember {
                    mutableStateOf(listOf<Int>())

                }


                Modifier.background(color = Color.Blue)
                Column(

                    modifier = Modifier.size(width = 500.dp, height = 800.dp)
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(color = Color.White)
                ) {


                    Text(
                        text = "Team Registration",
                        modifier = Modifier ,
                            style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.heightIn(50.dp))
                    Text(
                        text = "Team Details",


                        modifier = Modifier

                    )
                    Spacer(Modifier.heightIn(30.dp))
                    Text(
                        text = "Team Name",
                        modifier = Modifier

                    )

                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        OutlinedTextField(
                            value = TeamName, onValueChange = { text -> TeamName = text },
                            modifier = Modifier.weight(1f)
                        )

                    }
                    Text(
                        text = "Manager Name",
                        modifier = Modifier

                    )
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        OutlinedTextField(
                            value = ManagerName,
                            onValueChange = { text -> ManagerName = text },
                            modifier = Modifier.weight(1f))

                    }
                    Text(
                        text = "contacts",
                        modifier = Modifier

                    )
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        OutlinedTextField(
                            value = Contact, onValueChange = { text -> Contact = text },
                            modifier = Modifier.weight(1f)
                        )

                    }
                    Spacer(Modifier.heightIn(30.dp))
                    Row {

                        Text(text = "players")
                        Spacer(Modifier.width(100.dp))
                        Button(modifier = Modifier.
                        height(50.dp).width(200.dp),
                            shape = RoundedCornerShape(topStart =10.dp, bottomEnd =10.dp),
                            enabled = true,
                            colors = ButtonDefaults.buttonColors(
                            )

                            ,onClick = {
                            if (TeamName.isNotBlank() && ManagerName.isNotBlank()) {
                                TeamNames = TeamNames + TeamName
                                ManagerNames = ManagerNames + ManagerName
                            }
                        }) {
                            Text(text = "+ add player")

                        }
                    }
                    Spacer(Modifier.heightIn(30.dp))
                    Row {
                        Text(text = "Player 1")
                        Spacer(Modifier.width(30.dp))
                    }
                    Spacer(Modifier.heightIn(30.dp))

                    Text(text = "Player emails")
                    Row {
                        OutlinedTextField(
                            value = EmailAdress, onValueChange = { text -> EmailAdress = text },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Text(text = "Player Mobile numbers")

                    Row {
                        OutlinedTextField(
                            value = MobileNumber, onValueChange = { text -> MobileNumber = text },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.heightIn(50.dp))



                    Button(modifier = Modifier.height(50.dp).width(140.dp), onClick = {}
                    ) {
                        Text(text = "Submit registration")

                    }


                }
            }
            /* Spacer(Modifier.heightIn(30.dp))
                Column(modifier = Modifier.size(width = 500.dp, height =700.dp)
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(color = Color.Gray)) {


                }*/


        }


            }
        }




