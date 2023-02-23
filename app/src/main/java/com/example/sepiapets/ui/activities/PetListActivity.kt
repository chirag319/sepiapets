package com.example.sepiapets.ui.activities
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sepiapets.ui.theme.AppTheme
import com.example.sepiapets.R
import com.example.sepiapets.ui.theme.md_theme_light_inverseOnSurface
import com.example.sepiapets.model.Pet
import com.example.sepiapets.ui.theme.md_theme_dark_inverseOnSurface
import com.example.sepiapets.ui.viewmodel.PetListViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

class PetListActivity : ComponentActivity() {
    private val viewModel: PetListViewModel by viewModels()
    private var petsList = listOf<Pet>()
    private var mutableRefreshList = mutableStateOf(false)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Scaffold(
                    content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        val refreshList = mutableRefreshList.value
                        if(viewModel.isWorkingHour.value == true) {
                            GridPetList()
                        }
                        Text(text = stringResource(id = R.string.non_working_hours))
                        Button(onClick = { finish() }) {
                            Text(text = stringResource(id = R.string.exit))
                        }
                    }
                        ShowAlertDialog(this)
                })
            }
            SetStatusBarColor()
        }

        viewModel.petList.observe(this){ petlist->
            petlist.pets.let { pets->
                petsList = pets
                mutableRefreshList.value = !mutableRefreshList.value
            }
        }

        viewModel.isWorkingHour.observe(this){ isWorkingHour->
            if(isWorkingHour){
                mutableRefreshList.value = !mutableRefreshList.value
            }else{
                openDialog.value = true
            }
        }

        viewModel.checkWorkingHour()
    }

    @Composable
    fun GridPetList() {
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            items(petsList) { pet ->
                PetListItem(pet,navigateToProfile = {
                    val intent = Intent(this@PetListActivity, PetDetailActivity::class.java)
                    intent.putExtra("DATA", pet)
                    startActivity(intent)
                })
            }
        }
    }
}

private var openDialog = mutableStateOf(false)
var time = ""
// @ShowAlertDialog Display Alert Dialog
@Composable
fun ShowAlertDialog(context: ComponentActivity) {
    var expanded by openDialog
    if (expanded) {
        AlertDialog(
            onDismissRequest = {
                expanded = false
            },
            title = {
                Text(text = stringResource(id = R.string.non_working_hours))
            },
            text = {
                Column {
                    Text(text = String.format(stringResource(R.string.available_working_hours),time))
                }
            },
            confirmButton = {
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        expanded = false
                        context.finish()
                    }
                ) {
                    Text(stringResource(id = R.string.exit))
                }
            }
        )
    }
}

// @PetListItem returns Pet List item to Shown in Listview
@Composable
fun PetListItem(pet: Pet, navigateToProfile: (Pet) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(Modifier.clickable { navigateToProfile(pet) }) {
            PuppyImage(pet)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)) {
                Text(text = pet.title, style = MaterialTheme.typography.headlineMedium)
                Text(text = "VIEW DETAIL", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// @SetStatusBarColor Set the Status bar color for Material Dark/Light Theme
@Composable
fun SetStatusBarColor() {
    val color = if(isSystemInDarkTheme()) md_theme_dark_inverseOnSurface else md_theme_light_inverseOnSurface
    val systemUiController = rememberSystemUiController()
    val statusBarDarkIcons = color.luminance() > 0.5f
    systemUiController.setStatusBarColor(color = color)
    if(!isSystemInDarkTheme()){
        systemUiController.statusBarDarkContentEnabled = true
    } else {
        systemUiController.statusBarDarkContentEnabled = statusBarDarkIcons
    }
}

// @PuppyImage Set the Image in List view Item
@Composable
fun PuppyImage(pet: Pet) {
    Box(modifier = Modifier
        .padding(8.dp)
        .size(84.dp),contentAlignment = Alignment.Center,) {
        CircularProgressIndicator()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
            model = pet.imageUrl,
            contentDescription = "pet image",
            contentScale = ContentScale.Crop,
        )
    }
}
