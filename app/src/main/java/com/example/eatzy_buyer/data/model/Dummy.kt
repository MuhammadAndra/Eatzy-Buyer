package com.example.eatzy_buyer.data.model

//fungsi dummy
fun getCanteenById(id: Int): Canteen {
    return canteenList.find { canteen ->
        canteen.id == id
    } ?: throw IllegalArgumentException("Canteen with ID $id not found")
}

fun getMenuCategoriesForCanteen(canteen: Canteen): List<MenuCategory> {
    val menuCategoryIds = canteen.menuCategoryId ?: emptyList()
    return categoryMenuList.filter { it.id in menuCategoryIds }
}

fun getMenuById(idCategoryMenu: Int, idMenu: Int): Menu {
    val category = categoryMenuList.find { it.id == idCategoryMenu }
        ?: throw IllegalArgumentException("Kategori dengan ID $idCategoryMenu tidak ditemukan.")

    val menu = category.menus.find { it.id == idMenu }
        ?: throw IllegalArgumentException("Menu dengan ID $idMenu tidak ditemukan dalam kategori $idCategoryMenu.")

    return menu
}

fun getAddOnCategories(menu:Menu):List<AddOnCategory>{
    val addOnCategoryIds = menu.addOnCategoryId?: emptyList()
    return addOnCategoryList.filter { it.id in addOnCategoryIds }
}

//list dummy
val canteenList = listOf(
    Canteen(
        id = 0,
        name = "Kantin Tahu Telor",
        url = "https://img-global.cpcdn.com/recipes/2b31923cb4aff00c/680x482cq70/tahu-telur-foto-resep-utama.jpg",
        status = true,
        menuCategoryId = listOf(0, 2, 3) // Menyesuaikan dengan kategori menu unik
    ),
    Canteen(
        id = 1,
        name = "Kantin Lalapan",
        url = "https://img-global.cpcdn.com/recipes/bbc8ae6d7a514653/400x400cq70/photo.jpg",
        status = true,
        menuCategoryId = listOf(1, 4, 5) // Menyesuaikan dengan kategori menu unik
    ),
    Canteen(
        id = 2,
        name = "Kantin Katsu",
        url = "https://img-global.cpcdn.com/recipes/b51561ce4711d66a/1200x630cq70/photo.jpg",
        status = true,
        menuCategoryId = listOf(6, 7, 8) // Menyesuaikan dengan kategori menu unik
    ),
    Canteen(
        id = 3,
        name = "Kantin Ayam Bakar",
        url = "https://img-global.cpcdn.com/recipes/18e75e45937347db/1200x630cq70/photo.jpg",
        status = true,
        menuCategoryId = listOf(9, 10, 11) // Menyesuaikan dengan kategori menu unik
    )
)
val categoryMenuList = listOf(
    MenuCategory(
        id = 0,
        name = "Pilihan Tahu Tempe",
        menus = listOf(
            Menu(id = 0, name = "Tahu Tempe Goreng", price = 8000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/5383ed7a1fb0315e/1200x630cq70/photo.jpg", addOnCategoryId = listOf(0,2,3), preparationTime = 15),
            Menu(id = 1, name = "Tahu Telor Spesial", price = 10000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/2b31923cb4aff00c/680x482cq70/tahu-telur-foto-resep-utama.jpg", addOnCategoryId = listOf(0,2,3), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 1,
        name = "Pilihan Lalapan",
        menus = listOf(
            Menu(id = 2, name = "Lalapan Ayam Bakar", price = 15000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/bbc8ae6d7a514653/400x400cq70/photo.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15),
            Menu(id = 3, name = "Lalapan Ikan Bakar", price = 17000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/c1a5341e7292da77/680x482cq70/bumbu-ikan-bakar-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 2,
        name = "Pilihan Sambal",
        menus = listOf(
            Menu(id = 4, name = "Sambal Terasi", price = 5000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/bf000d5f873791e7/1200x630cq70/photo.jpg", addOnCategoryId = listOf(1, 2), preparationTime = 15),
            Menu(id = 5, name = "Sambal Matah", price = 6000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/67493461f6a46476/680x482cq70/sambal-matah-bali-foto-resep-utama.jpg", addOnCategoryId = listOf(1, 2), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 6,
        name = "Pilihan Katsu",
        menus = listOf(
            Menu(id = 6, name = "Chicken Katsu", price = 20000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/b51561ce4711d66a/1200x630cq70/photo.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15),
            Menu(id = 7, name = "Dori Katsu", price = 22000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/cad04dda37c642b6/680x482cq70/dori-katsu-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 7,
        name = "Pilihan Rice Katsu",
        menus = listOf(
            Menu(id = 8, name = "Nasi Chicken Katsu", price = 13000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/f909207cc86efcda/680x482cq70/chicken-katsu-curry-sauce-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2), preparationTime = 15),
            Menu(id = 9, name = "Nasi Dori Katsu", price = 15000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/3915965642b5b141/680x482cq70/nasi-dori-cabe-bawang-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 8,
        name = "Pilihan Ramen",
        menus = listOf(
            Menu(id = 10, name = "Ramen Ayam", price = 18000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/08bd3fb1141d272b/680x482cq70/ayam-ramen-foto-resep-utama.jpg", addOnCategoryId = listOf(3, 2), preparationTime = 15),
            Menu(id = 11, name = "Ramen Miso", price = 20000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/ddbf907fd1f8a7dc/1200x630cq70/photo.jpg", addOnCategoryId = listOf(3, 2), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 9,
        name = "Pilihan Ayam Bakar",
        menus = listOf(
            Menu(id = 12, name = "Ayam Bakar Original", price = 15000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/18e75e45937347db/1200x630cq70/photo.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15),
            Menu(id = 13, name = "Ayam Bakar Madu", price = 16000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/5434dafc8b5b3fc5/680x482cq70/ayam-bakar-madu-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2, 3), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 10,
        name = "Pilihan Nasi Bakar",
        menus = listOf(
            Menu(id = 14, name = "Nasi Bakar Ayam", price = 18000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/2556314567203ac5/680x482cq70/nasi-bakar-ayam-suwir-kemangi-foto-resep-utama.jpg", addOnCategoryId = listOf(0, 2), preparationTime = 15),
            Menu(id = 15, name = "Nasi Bakar Ikan", price = 19000.0, isAvailable = true, imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQMtcjurQgVRznOH7xWm7Sn6r60TUMjXt_gZw&s", addOnCategoryId = listOf(0, 2), preparationTime = 15)
        )
    ),
    MenuCategory(
        id = 11,
        name = "Pilihan Sambal Bakar",
        menus = listOf(
            Menu(id = 16, name = "Sambal Bakar Terasi", price = 6000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/60463007fb1a3a69/680x482cq70/sambal-bakar-terasi-foto-resep-utama.jpg", addOnCategoryId = listOf(1, 2), preparationTime = 15),
            Menu(id = 17, name = "Sambal Bakar Matah", price = 7000.0, isAvailable = true, imageUrl = "https://img-global.cpcdn.com/recipes/c9c57db41113c5d2/680x482cq70/tempe-bakar-sambal-matah-foto-resep-utama.jpg", addOnCategoryId = listOf(1, 2), preparationTime = 15)
        )
    )
)

val addOnCategoryList = listOf(
    AddOnCategory(
        id = 0,
        name = "Pilih Sambal",
        isMultipleChoice = false,
        addOns = listOf(
            AddOn(id = 0, name = "Sambal Bawang", price = 0.0),
            AddOn(id = 1, name = "Sambal Matah", price = 2000.0),
            AddOn(id = 2, name = "Sambal Terasi", price = 2000.0)
        )
    ),
    AddOnCategory(
        id = 1,
        name = "Pilih Lalapan & Sambal",
        isMultipleChoice = false,
        addOns = listOf(
            AddOn(id = 3, name = "Lalapan Kol", price = 1000.0),
            AddOn(id = 4, name = "Lalapan Timun", price = 1000.0),
            AddOn(id = 5, name = "Sambal Terasi", price = 2000.0),
            AddOn(id = 6, name = "Sambal Matah", price = 2000.0)
        )
    ),
    AddOnCategory(
        id = 2,
        name = "Tambahan Topping Katsu/Ramen",
        isMultipleChoice = true,
        addOns = listOf(
            AddOn(id = 7, name = "Telur Setengah Matang", price = 3000.0),
            AddOn(id = 8, name = "Keju Leleh", price = 4000.0),
            AddOn(id = 9, name = "Mayonnaise", price = 2000.0)
        )
    ),
    AddOnCategory(
        id = 3,
        name = "Pilihan Tambahan Lauk & Nasi",
        isMultipleChoice = true,
        addOns = listOf(
            AddOn(id = 10, name = "Nasi Tambahan", price = 4000.0),
            AddOn(id = 11, name = "Tempe Goreng", price = 2000.0),
            AddOn(id = 12, name = "Tahu Goreng", price = 2000.0)
        )
    )
)

