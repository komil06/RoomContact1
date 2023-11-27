package uz.ictschool.roomcontact1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.ictschool.roomcontact.AppDatabase
import uz.ictschool.roomcontact.Contact
import uz.ictschool.roomcontact1.ui.theme.RoomContact1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomContact1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "contactList") {
        composable("contactList") {
            ContactListScreen(navController)
        }
        composable("addContact") {
            AddContactScreen(navController)
        }
    }
}

@Composable
fun ContactListScreen(navController: NavController) {
    val contactDao = AppDatabase.getInstance(LocalContext.current).contactDao()

    var contacts = contactDao.getAllContacts()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Contact List",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(contacts) { contact ->
                Text(
                    text = "Name: ${contact.name}, Phone: ${contact.phone}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("addContact") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Add Contact")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(navController: NavController) {
    val contactDao = AppDatabase.getInstance(LocalContext.current).contactDao()
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add Contact",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                contactDao.insertContact(Contact(name = name, phone = phoneNumber))
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save this Contact")
        }
    }
}

@Preview
@Composable
fun GreetingPreview() {
    RoomContact1Theme {
        Greeting()
    }
}