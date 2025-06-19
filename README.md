# Android Basics with Jetpack Compose - Sample Projects

This repository contains a collection of sample projects following the [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course) course by Google.

## Introduction

This repository includes multiple small projects, each serving as a practical example to help you learn and practice key topics in modern Android app development with Jetpack Compose and Kotlin. Covered topics include:

- UI with Jetpack Compose
- State and event handling
- Working with data (Room, Retrofit, DataStore)
- Asynchronous programming with Coroutine, Flow
- Background tasks with WorkManager
- Displaying images with Coil
- Modern app architecture (MVVM, Repository, manual Dependency Injection)

## Sample Project List

| Folder name         | Main topics                         | Short description                                                                       | Link                                         |
| ------------------- | ----------------------------------- | --------------------------------------------------------------------------------------- | -------------------------------------------- |
| blur_o_matic        | WorkManager, Compose UI, Material 3 | Image blurring app using WorkManager for background processing, modern UI with Compose. | [blur_o_matic](./blur_o_matic)               |
| flight_search_app   | DataStore, Compose UI, Navigation   | Flight search app, practice data storage with DataStore and navigation with Compose.    | [flight_search_app](./flight_search_app)     |
| sql_basic_app       | Room, SQLite, Database Inspector    | Practice SQL queries directly via Database Inspector, using Room.                       | [sql_basic_app](./sql_basic_app)             |
| amphibians app      | Retrofit, Coil, Compose, Networking | Display amphibian list from API, practice Retrofit, Coil, and Compose.                  | [amphibians app](./amphibians%20app)         |
| bookshelf_app       | Retrofit, Coil, Repository, Compose | Book management app, practice Repository architecture, Retrofit, Coil, Compose.         | [bookshelf_app](./bookshelf_app)             |
| dessert_release_app | Compose UI, RecyclerView, Layout    | Showcase Android versions as desserts, practice Compose layouts.                        | [dessert_release_app](./dessert_release_app) |
| bus_schedule_app    | Room, Compose, Navigation           | Bus schedule management app, practice Room, Compose, Navigation.                        | [bus_schedule_app](./bus_schedule_app)       |
| inventory_app       | Room, Compose, CRUD                 | Inventory management app, practice CRUD with Room and Compose.                          | [inventory_app](./inventory_app)             |
| cupcake_app         | Compose UI, Navigation, State       | Cupcake ordering app, practice navigation and state management with Compose.            | [cupcake_app](./cupcake_app)                 |
| business_card_app   | Compose UI                          | Personal business card app, practice layout and styling with Compose.                   | [business_card_app](./business_card_app)     |
| compose_article     | Compose UI                          | Article display app, practice text, image, and layout with Compose.                     | [compose_article](./compose_article)         |
| compose_quadrant    | Compose UI                          | Demonstrates basic composables: Text, Image, Row, Column.                               | [compose_quadrant](./compose_quadrant)       |
| happy_birthday_app  | Compose UI                          | Birthday card app, practice image and text with Compose.                                | [happy_birthday_app](./happy_birthday_app)   |
| affirmations_app    | RecyclerView, Compose               | Display affirmations, practice RecyclerView/Compose.                                    | [affirmations_app](./affirmations_app)       |
| dice_roller_app     | Compose UI, State                   | Dice roller app, practice state and dynamic UI with Compose.                            | [dice_roller_app](./dice_roller_app)         |
| woof_app            | Compose UI, List                    | Dog directory app, practice lists and layouts with Compose.                             | [woof_app](./woof_app)                       |
| super_heros_app     | Compose UI, List                    | Superhero list app, practice Compose lists.                                             | [super_heros_app](./super_heros_app)         |
| topic_grid_app      | Compose UI, Grid                    | Display topics in a grid, practice Compose Grid.                                        | [topic_grid_app](./topic_grid_app)           |
| dessert_clicker_app | Compose UI, State                   | Clicker app, practice state and events with Compose.                                    | [dessert_clicker_app](./dessert_clicker_app) |
| lemon_juice_app     | Compose UI, State                   | Lemonade making app, practice state and events with Compose.                            | [lemon_juice_app](./lemon_juice_app)         |
| lunch_tray_app      | Compose UI, Navigation              | Lunch tray ordering app, practice navigation and UI with Compose.                       | [lunch_tray_app](./lunch_tray_app)           |
| run_tracker_app     | Compose UI, State                   | Race tracker app, practice state and dynamic UI with Compose.                           | [run_tracker_app](./run_tracker_app)         |
| 30_days_app         | Compose UI, Multi-screen            | 30 days of Android learning app, covers many small topics.                              | [30_days_app](./30_days_app)                 |
| unscramble_app      | Compose UI, State                   | Word unscramble puzzle app, practice state and UI with Compose.                         | [unscramble_app](./unscramble_app)           |
| art_space_app       | Compose UI, Image                   | Art gallery app, practice image and layout with Compose.                                | [art_space_app](./art_space_app)             |
| tip_calculator_app  | Compose UI, State                   | Tip calculator app, practice input and state with Compose.                              | [tip_calculator_app](./tip_calculator_app)   |
| reply_app           | Compose UI, Navigation              | Sample email client app, practice navigation and Compose.                               | [reply_app](./reply_app)                     |

> **Note:** Each folder is an independent Android Studio project. You can open and practice with each project separately.

## How to Set Up & Run

1. **Requirements:**

   - [Android Studio](https://developer.android.com/studio) installed
   - JDK 11 or higher
   - Internet connection to download dependencies

2. **How to open a project:**

   - In Android Studio: File > Open > select a subproject folder (e.g. `blur_o_matic`)
   - Wait for Gradle sync to complete

3. **Run the app:**
   - Select the `app` module > click Run (Shift+F10) or ▶️ icon
   - Choose a device/emulator to run

## License

Source code is licensed under the [Apache License 2.0](sql_basic_app/LICENSE.md).

## References

- [Android Basics with Compose - Google](https://developer.android.com/courses/android-basics-compose/course)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)

---

> This repository is for learning purposes only and is not an official Google product.
