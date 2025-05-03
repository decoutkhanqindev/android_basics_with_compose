package com.example.my_city_app.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.my_city_app.R
import com.example.my_city_app.data.local.CategoryDataSource
import com.example.my_city_app.domain.model.Category
import com.example.my_city_app.domain.model.CategoryType
import com.example.my_city_app.domain.model.Recommendation
import com.example.my_city_app.ui.state.MyCityUiState
import com.example.my_city_app.ui.theme.My_city_appTheme

enum class MyCityScreen {
  HOME, RECOMMENDATIONS, RECOMMENDATION_DETAILS
}

@Composable
fun MyCityAppListOnlyContent(
  uiState: MyCityUiState,
  onCategoryClick: (Category) -> Unit,
  onRecommendationClick: (Recommendation) -> Unit,
  modifier: Modifier = Modifier
) {
  val navController = rememberNavController()
  val backStackEntry = navController.currentBackStackEntryAsState()
  val currentScreen = MyCityScreen.valueOf(
    backStackEntry.value?.destination?.route ?: MyCityScreen.HOME.name
  )

  val currentScreenTitle: String = when (currentScreen) {
    MyCityScreen.HOME -> stringResource(R.string.app_name)
    MyCityScreen.RECOMMENDATIONS -> stringResource(uiState.currentCategory!!.name)
    MyCityScreen.RECOMMENDATION_DETAILS -> stringResource(uiState.currentRecommendation!!.name)
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        currentScreenTitle = currentScreenTitle,
        canNavigateBack = navController.previousBackStackEntry != null,
        navigateUp = { navController.navigateUp() },
      )
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      NavHost(
        navController = navController,
        startDestination = MyCityScreen.HOME.name,
      ) {
        composable(route = MyCityScreen.HOME.name) {
          CategoryList(
            categories = uiState.categories,
            onCategoryClick = {
              onCategoryClick(it)
              navController.navigate(MyCityScreen.RECOMMENDATIONS.name)
            },
            modifier = Modifier.fillMaxSize()
          )
        }
        composable(route = MyCityScreen.RECOMMENDATIONS.name) {
          RecommendationList(
            recommendations = uiState.recommendationsOfCategory,
            onRecommendationClick = {
              onRecommendationClick(it)
              navController.navigate(MyCityScreen.RECOMMENDATION_DETAILS.name)
            },
            modifier = Modifier.fillMaxSize()
          )
        }
        composable(route = MyCityScreen.RECOMMENDATION_DETAILS.name) {
          RecommendationDetails(
            recommendation = uiState.currentRecommendation!!,
            onBackClick = {
              navController.navigateUp()
            },
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }
  }
}

@Composable
fun MyCityAppNavigationRailAndListContent(
  uiState: MyCityUiState,
  onCategoryClick: (Category) -> Unit,
  onRecommendationClick: (Recommendation) -> Unit,
  onDetailsScreenBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        currentScreenTitle = stringResource(R.string.app_name),
        canNavigateBack = false,
        navigateUp = { }
      )
    }
  ) {
    Surface(
      modifier = Modifier
        .padding(it)
        .background(MaterialTheme.colorScheme.background)
    ) {
      Row {
        if (uiState.isShowingHomeScreen) {
          CategoryNavigationRail(
            categories = uiState.categories,
            currentCategory = uiState.currentCategory!!,
            onCategoryClick = onCategoryClick,
          )
          RecommendationList(
            recommendations = uiState.recommendationsOfCategory,
            onRecommendationClick = onRecommendationClick,
          )
        } else {
          RecommendationDetails(
            recommendation = uiState.currentRecommendation!!,
            onBackClick = onDetailsScreenBackClick,
          )
        }
      }
    }
  }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun MyCityAppListAndDetailsContent(
  uiState: MyCityUiState,
  onCategoryClick: (Category) -> Unit,
  onRecommendationClick: (Recommendation) -> Unit,
  onDetailsScreenBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      AppTopBar(
        currentScreenTitle = stringResource(R.string.app_name),
        canNavigateBack = false,
        navigateUp = onDetailsScreenBackClick
      )
    }
  ) {
    Box(modifier = Modifier.padding(it)) {
      PermanentNavigationDrawer(
        drawerContent = {
          PermanentDrawerSheet(
            modifier = Modifier
              .width(225.dp)
              .fillMaxHeight()
              .verticalScroll(rememberScrollState())
              .padding(end = dimensionResource(R.dimen.medium_padding))
          ) {
            CategoryNavigationDrawerContent(
              categories = uiState.categories,
              currentCategory = uiState.currentCategory!!,
              onCategoryClick = onCategoryClick,
            )
          }
        },
      ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
          RecommendationList(
            recommendations = uiState.recommendationsOfCategory,
            onRecommendationClick = {
              onRecommendationClick(it)
            },
            modifier = Modifier.weight(1f)
          )
          Spacer(modifier = Modifier.width(dimensionResource(R.dimen.medium_padding)))
          val activity = LocalContext.current as Activity
          RecommendationDetails(
            recommendation = uiState.currentRecommendation!!,
            onBackClick = {
              activity.finish()
            },
            isFullScreen = true,
            modifier = Modifier
              .verticalScroll(rememberScrollState())
              .weight(1f)
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  currentScreenTitle: String,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit,
  modifier: Modifier = Modifier
) {
  CenterAlignedTopAppBar(
    title = {
      Text(
        text = currentScreenTitle,
        style = MaterialTheme.typography.titleLarge,
      )
    },
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
          )
        }
      } else {
        Box {}
      }
    },
    modifier = modifier
  )
}

@Composable
private fun CategoryList(
  categories: List<Category>,
  onCategoryClick: (Category) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(categories) { item ->
      CategoryItem(
        category = item,
        onCategoryClick = { onCategoryClick(item) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = dimensionResource(R.dimen.small_padding))
      )
    }
  }
}

@Composable
private fun CategoryItem(
  category: Category,
  onCategoryClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    onClick = onCategoryClick
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.small_padding)),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(
        painter = painterResource(category.image),
        contentDescription = null,
        modifier = Modifier
          .size(dimensionResource(R.dimen.medium_image_dimension))
          .padding(end = dimensionResource(R.dimen.small_padding))
      )
      Text(
        text = stringResource(category.name),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
          .padding(start = dimensionResource(R.dimen.small_padding))
      )
    }
  }
}

@Composable
private fun RecommendationList(
  recommendations: List<Recommendation>,
  onRecommendationClick: (Recommendation) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier) {
    items(recommendations) { item ->
      RecommendationItem(
        recommendation = item,
        onRecommendationClick = { onRecommendationClick(item) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = dimensionResource(R.dimen.small_padding))
      )
    }
  }
}

@Composable
private fun RecommendationItem(
  recommendation: Recommendation,
  onRecommendationClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    onClick = onRecommendationClick
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        painter = painterResource(recommendation.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
      )
      Text(
        text = stringResource(recommendation.name),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
      )
    }
  }
}

@Composable
private fun RecommendationDetails(
  recommendation: Recommendation,
  onBackClick: () -> Unit,
  isFullScreen: Boolean = false,
  modifier: Modifier = Modifier
) {
  BackHandler {
    onBackClick()
  }

  Column(modifier = modifier) {
    if (isFullScreen) {
      Text(
        text = stringResource(recommendation.name),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(dimensionResource(R.dimen.small_padding))
      )
    }
    Image(
      painter = painterResource(recommendation.image),
      contentDescription = null,
      modifier = Modifier.fillMaxWidth(),
      contentScale = ContentScale.Crop
    )
    Text(
      text = stringResource(recommendation.description),
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
    )
  }
}

@Composable
private fun CategoryNavigationRail(
  categories: List<Category>,
  currentCategory: Category,
  onCategoryClick: (Category) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationRail(modifier = modifier) {
    for (navItem in categories) {
      NavigationRailItem(
        selected = currentCategory.name == navItem.name,
        onClick = { onCategoryClick(navItem) },
        icon = {
          Image(
            painter = painterResource(navItem.image),
            contentDescription = null,
            modifier = Modifier
              .size(dimensionResource(R.dimen.medium_image_dimension))
          )
        },
        modifier = Modifier.padding(
          bottom = dimensionResource(R.dimen.medium_padding),
          end = dimensionResource(R.dimen.medium_padding)
        )
      )
    }
  }
}

@Composable
private fun CategoryNavigationDrawerContent(
  categories: List<Category>,
  currentCategory: Category,
  onCategoryClick: (Category) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
//    CategoryNavigationDrawerHeader(
//      modifier = Modifier
//        .fillMaxWidth()
//        .padding(dimensionResource(R.dimen.small_padding))
//    )
    for (navItem in categories) {
      NavigationDrawerItem(
        selected = currentCategory.name == navItem.name,
        icon = {
          Image(
            painter = painterResource(navItem.image),
            contentDescription = null,
            modifier = Modifier
              .size(dimensionResource(R.dimen.medium_image_dimension))
              .padding(horizontal = dimensionResource(R.dimen.small_padding))
          )
        },
        label = { Text(text = stringResource(navItem.name)) },
        onClick = { onCategoryClick(navItem) },
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small_padding)),
      )
    }
  }
}

@Composable
private fun CategoryNavigationDrawerHeader(
  modifier: Modifier = Modifier
) {
  Text(
    text = stringResource(R.string.categories_text),
    style = MaterialTheme.typography.headlineMedium,
    textAlign = TextAlign.Center,
    modifier = modifier
  )
  HorizontalDivider(thickness = 1.dp)
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun MyCityAppListOnlyContentPreview() {
  My_city_appTheme {
    MyCityAppListOnlyContent(
      uiState = MyCityUiState(
        categories = CategoryDataSource.listOfCategories,
        recommendationsOfCategory = listOf(
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
        ),
      ),
      onCategoryClick = {},
      onRecommendationClick = {},
      modifier = Modifier.padding(16.dp)
    )
  }
}


@Preview(showBackground = true, device = Devices.PIXEL_FOLD)
@Composable
fun MyCityAppNavigationRailAndListContentPreview() {
  My_city_appTheme {
    MyCityAppNavigationRailAndListContent(
      uiState = MyCityUiState(
        categories = CategoryDataSource.listOfCategories,
        recommendationsOfCategory = listOf(
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
        ),
      ),
      onCategoryClick = {},
      onRecommendationClick = {},
      onDetailsScreenBackClick = {},
      modifier = Modifier.padding(16.dp)
    )
  }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun MyCityAppListAndDetailsContentPreview() {
  My_city_appTheme {
    MyCityAppListAndDetailsContent(
      uiState = MyCityUiState(
        categories = CategoryDataSource.listOfCategories,
        recommendationsOfCategory = listOf(
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
          Recommendation(
            name = R.string.coffe_shop_title_1,
            description = R.string.coffe_shop_detail_1,
            image = R.drawable.coffee_shops_1,
            categoryType = CategoryType.COFFEE_SHOPS
          ),
        ),
      ),
      onCategoryClick = {},
      onRecommendationClick = {},
      onDetailsScreenBackClick = {},
      modifier = Modifier.padding(16.dp)
    )
  }
}