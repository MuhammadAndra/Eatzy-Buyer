package com.example.eatzy_buyer.ui.screen.addMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.eatzy_buyer.data.model.AddOn
import com.example.eatzy_buyer.data.model.AddOnCategory
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.getAddOnCategories

import com.example.eatzy_buyer.data.model.getMenuById
import com.example.eatzy_buyer.ui.components.TopBar
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AddMenuScreen(
    modifier: Modifier = Modifier,
    idCategoryMenu: Int,
    idMenu: Int,
    navController: NavController,
    onNavigateUp: () -> Unit,
) {
    val menu = getMenuById(idCategoryMenu = idCategoryMenu, idMenu = idMenu)
    val addOnCategories = getAddOnCategories(menu = menu)
    var quantity by remember { mutableIntStateOf(1) }
    val checkedAddOns =
        remember { mutableStateMapOf<Int, Boolean>() } // Multiple
    val selectedAddOnPerCategory =
        remember { mutableStateMapOf<Int, Int>() } // Single (Map<CategoryId, SelectedAddOnId>)

    //bawah ini untuk ambil addon yang di select
    val selectedAddOns = checkedAddOns.filterValues { it }.keys
    var notes by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    var totalAddOnPrice by remember { mutableDoubleStateOf(0.0) }

    fun calculateTotalAddOnPrice(
        allAddOns: List<AddOn>,
        checkedAddOns: Map<Int, Boolean>,
        selectedAddOnPerCategory: Map<Int, Int>
    ): Double {
        val checkedPrice = allAddOns
            .filter { checkedAddOns[it.id] == true }
            .sumOf { it.price }

        val selectedPrice = allAddOns
            .filter { addOn -> selectedAddOnPerCategory.values.contains(addOn.id) }
            .sumOf { it.price }

        return checkedPrice + selectedPrice
    }

    fun getSelectedAddOns(
        allAddOns: List<AddOn>,
        checkedAddOns: Map<Int, Boolean>,
        selectedAddOnPerCategory: Map<Int, Int>
    ): List<AddOn> {
        return allAddOns.filter { addOn ->
            checkedAddOns[addOn.id] == true || selectedAddOnPerCategory.values.contains(
                addOn.id
            )
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Tambahkan",
                onNavigateUp = onNavigateUp
            )
        },
//        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(), // supaya tidak ketabrak tombol
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        model = menu.imageUrl,
                        contentDescription = "Image ${menu.name}",
                        contentScale = ContentScale.Crop,
                        loading = placeholder {
                            Box(
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0XFFFC9824))
                            }
                        }
                    )
                }
                item {
                    MenuHeader(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                        ),
                        menu = menu,
                        quantity = quantity,
                        onIncrement = { quantity++ },
                        onDecrement = { if (quantity > 1) quantity-- }
                    )
                }
                addOnCategories.forEach { addOnCategory ->
                    item {
                        AddMenuCustomCard(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                            ),
                            name = addOnCategory.name
                        ) {
                            Column {
                                if (addOnCategory.isMultipleChoice) {
                                    addOnCategory.addOns.forEach { addOn ->
                                        val isChecked =
                                            checkedAddOns[addOn.id] ?: false
                                        AddOnCardCheckbox(
                                            addOn = addOn,
                                            checked = isChecked,
                                            onCheckedChange = { checked ->
                                                checkedAddOns[addOn.id] =
                                                    checked
                                                totalAddOnPrice =
                                                    calculateTotalAddOnPrice(
                                                        allAddOns = addOnCategories.flatMap { it.addOns },
                                                        checkedAddOns = checkedAddOns,
                                                        selectedAddOnPerCategory = selectedAddOnPerCategory
                                                    )
                                            }
                                        )
                                    }
                                } else {
                                    val selectedId =
                                        selectedAddOnPerCategory[addOnCategory.id]
                                    addOnCategory.addOns.forEach { addOn ->
                                        AddOnCardRadioButton(
                                            addOn = addOn,
                                            selected = selectedId == addOn.id,
                                            onSelect = {
                                                selectedAddOnPerCategory[addOnCategory.id] =
                                                    addOn.id
                                                totalAddOnPrice =
                                                    calculateTotalAddOnPrice(
                                                        allAddOns = addOnCategories.flatMap { it.addOns },
                                                        checkedAddOns = checkedAddOns,
                                                        selectedAddOnPerCategory = selectedAddOnPerCategory
                                                    )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    AddMenuCustomCard(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 70.dp
                        ),
                        name = "Catatan Untuk Kantin"
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(10.dp),
                            value = notes,
                            onValueChange = { input ->
                                // Hapus karakter newline yang dimasukkan pengguna lewat Enter
                                notes = input.replace("\n", "")
                            },
                            maxLines = 5,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF455E84),
                            )
                        )
                    }
                }
                item {

                }
            }

            // Tombol mengambang
            ElevatedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(0XFFFFFFFF))
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .border(
                        1.dp,
                        Color(0xffFC9824),
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    ) // Outline-nya di sini
                    .align(Alignment.BottomCenter),
                elevation = CardDefaults.elevatedCardElevation(4.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                )
            ) {
                AddMenuCustomButton(
                    onClick = {},
                    fullPrice = (menu.price * quantity + menu.price * quantity + totalAddOnPrice)
                )
            }
        }
    }
}


@Preview
@Composable
private fun AddMenuScreenPreview() {
    AddMenuScreen(
        idCategoryMenu = 0,
        idMenu = 0,
        navController = rememberNavController(),
        onNavigateUp = {}
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuHeader(
    modifier: Modifier = Modifier,
    menu: Menu,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = menu.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF675E5E)
            )
            Text(
                text = NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(menu.price),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF675E5E)
            )
            IncrementButton(
                count = quantity,
                onIncrement = onIncrement,
                onDecrement = onDecrement
            )
        }
    }
}

@Preview
@Composable
private fun MenuHeaderPreview() {
    MenuHeader(
        menu = Menu(
            id = 0,
            name = "Tahu Tempe Goreng",
            price = 8000.0,
            isAvailable = true,
            imageUrl = "https://img-global.cpcdn.com/recipes/5383ed7a1fb0315e/1200x630cq70/photo.jpg",
            addOnCategoryId = listOf(0),
            preparationTime = 15
        ),
        quantity = 1,
        onIncrement = {},
        onDecrement = {}
    )
}

@Composable
fun IncrementButton(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        IconButton(
            onClick = onDecrement,
            modifier = Modifier.size(25.dp),
            colors = IconButtonDefaults.iconButtonColors(
                Color(0XFFFC9824)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Icon Decrement",
                modifier = Modifier.size(20.dp),
                tint = Color(0XFFFFFFFF)
            )
        }
        Text(count.toString(), fontSize = 15.sp)
        IconButton(
            onClick = onIncrement,
            modifier = Modifier.size(25.dp),
            colors = IconButtonDefaults.iconButtonColors(
                Color(0XFFFC9824)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Icon Decrement",
                modifier = Modifier.size(20.dp),
                tint = Color(0XFFFFFFFF)
            )
        }
    }
}

@Preview
@Composable
private fun IncrementButtonPreview() {
    IncrementButton(count = 1, onIncrement = {}, onDecrement = {})
}

@Composable
fun AddMenuCustomCard(
    modifier: Modifier = Modifier,
    name: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(top = 12.dp, start = 12.dp)) {
            Text(name)
        }
        content()
    }
}

@Preview
@Composable
private fun AddOnCategoryCardPreview() {
    val addOnCategory = AddOnCategory(
        id = 0,
        name = "Pilih Sambal",
        isMultipleChoice = false,
        addOns = listOf(
            AddOn(id = 0, name = "Sambal Bawang", price = 0.0),
            AddOn(id = 1, name = "Sambal Matah", price = 2000.0),
            AddOn(id = 2, name = "Sambal Terasi", price = 2000.0)
        )
    )
    AddMenuCustomCard(
        name = addOnCategory.name
    ) {
        LazyColumn {
            items(addOnCategory.addOns) { addOn ->
                AddOnCardCheckbox(
                    addOn = addOn,
                    checked = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}

@Composable
fun AddOnCardCheckbox(
    modifier: Modifier = Modifier,
    addOn: AddOn,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange?.invoke(!checked) }
            .padding(15.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                onCheckedChange = null, checked = checked,
                colors = CheckboxDefaults.colors(Color(0XFF455E84)),
            )
            Text(
                text = addOn.name,
                fontSize = 13.sp,
                color = Color(0XFF675E5E)
            )
        }
        Text(
            text = NumberFormat
                .getCurrencyInstance(Locale("in", "ID"))
                .format(addOn.price),
            fontSize = 13.sp,
            color = Color(0XFF675E5E)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddOnCardCheckBoxPreview() {
    AddOnCardCheckbox(
        addOn = AddOn(id = 0, name = "Sambal Bawang", price = 5000.0),
        checked = false,
        onCheckedChange = {}
    )
}

@Composable
fun AddOnCardRadioButton(
    modifier: Modifier = Modifier,
    addOn: AddOn,
    selected: Boolean,
    onSelect: (() -> Unit)?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect?.invoke() }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0XFF455E84)
                )
            )
            Text(
                text = addOn.name,
                fontSize = 13.sp,
                color = Color(0XFF675E5E)
            )
        }
        Text(
            text = NumberFormat
                .getCurrencyInstance(Locale("in", "ID"))
                .format(addOn.price),
            fontSize = 13.sp,
            color = Color(0XFF675E5E)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddOnCardRadioButtonPreview() {
    AddOnCardRadioButton(
        addOn = AddOn(id = 0, name = "Sambal Bawang", price = 5000.0),
        selected = false,
        onSelect = {}
    )
}

@Composable
fun AddMenuCustomButton(
    modifier: Modifier = Modifier,
    fullPrice: Double,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 18.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.elevatedButtonColors(
            Color(
                0xFFFC9824
            )
        )
    ) {
        Text(
            text = "Tambah ke Keranjang ${
                NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(fullPrice)
            }",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
