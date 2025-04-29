package com.example.lunch_tray_app.data

import com.example.lunch_tray_app.model.MenuItem.Accompaniment
import com.example.lunch_tray_app.model.MenuItem.Entree
import com.example.lunch_tray_app.model.MenuItem.SideDish

object MenuDataSource {
  val entreeMenuItems = listOf(
    Entree(
      name = "Cauliflower",
      description = "Whole cauliflower, brined, roasted, and deep fried",
      price = 7.00,
    ),
    Entree(
      name = "Three Bean Chili",
      description = "Black beans, red beans, kidney beans, slow cooked, topped with onion",
      price = 4.00,
    ),
    Entree(
      name = "Mushroom Pasta",
      description = "Penne pasta, mushrooms, basil, with plum tomatoes cooked in garlic " +
          "and olive oil",
      price = 5.50,
    ),
    Entree(
      name = "Spicy Black Bean Skillet",
      description = "Seasonal vegetables, black beans, house spice blend, served with " +
          "avocado and quick pickled onions",
      price = 5.50,
    )
  )

  val sideDishMenuItems = listOf(
    SideDish(
      name = "Summer Salad",
      description = "Heirloom tomatoes, butter lettuce, peaches, avocado, balsamic dressing",
      price = 2.50,
    ),
    SideDish(
      name = "Butternut Squash Soup",
      description = "Roasted butternut squash, roasted peppers, chili oil",
      price = 3.00,
    ),
    SideDish(
      name = "Spicy Potatoes",
      description = "Marble potatoes, roasted, and fried in house spice blend",
      price = 2.00,
    ),
    SideDish(
      name = "Coconut Rice",
      description = "Rice, coconut milk, lime, and sugar",
      price = 1.50,
    )
  )

  val accompanimentMenuItems = listOf(
    Accompaniment(
      name = "Lunch Roll",
      description = "Fresh baked roll made in house",
      price = 0.50,
    ),
    Accompaniment(
      name = "Mixed Berries",
      description = "Strawberries, blueberries, raspberries, and huckleberries",
      price = 1.00,
    ),
    Accompaniment(
      name = "Pickled Veggies",
      description = "Pickled cucumbers and carrots, made in house",
      price = 0.50,
    )
  )
}