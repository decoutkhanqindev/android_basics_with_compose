package com.example.reply_app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.reply_app.R
import com.example.reply_app.data.AccountDataProvider
import com.example.reply_app.data.EmailDataProvider
import com.example.reply_app.model.Email
import com.example.reply_app.model.MailboxType
import com.example.reply_app.ui.theme.ReplyAppTheme
import com.example.reply_app.ui.utils.ReplyContentType
import com.example.reply_app.ui.utils.ReplyNavigationType

@Composable
fun ReplyHomeScreen(
  navigationType: ReplyNavigationType,
  contentType: ReplyContentType,
  replyUiState: ReplyUiState,
  onTabPressed: (MailboxType) -> Unit,
  onEmailCardPressed: (Email) -> Unit,
  onDetailScreenBackPressed: () -> Unit,
  modifier: Modifier = Modifier
) {
  val navigationItemContentList = listOf(
    NavigationItemContent(
      mailboxType = MailboxType.Inbox,
      icon = Icons.Default.Inbox,
      text = stringResource(id = R.string.tab_inbox)
    ),
    NavigationItemContent(
      mailboxType = MailboxType.Sent,
      icon = Icons.AutoMirrored.Filled.Send,
      text = stringResource(id = R.string.tab_sent)
    ),
    NavigationItemContent(
      mailboxType = MailboxType.Drafts,
      icon = Icons.Default.Drafts,
      text = stringResource(id = R.string.tab_drafts)
    ),
    NavigationItemContent(
      mailboxType = MailboxType.Spam,
      icon = Icons.Default.Report,
      text = stringResource(id = R.string.tab_spam)
    )
  )

  if (navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER) {
    val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
    PermanentNavigationDrawer(
      drawerContent = {
        PermanentDrawerSheet(
          modifier = Modifier.width(dimensionResource(R.dimen.drawer_width)),
          drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) {
          NavigationDrawerContent(
            selectedDestination = replyUiState.currentMailboxType,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList,
            modifier = Modifier
              .wrapContentWidth()
              .fillMaxHeight()
              .background(MaterialTheme.colorScheme.inverseOnSurface)
              .padding(dimensionResource(R.dimen.drawer_padding_content))
          )
        }
      },
      modifier = Modifier.testTag(navigationDrawerContentDescription)
    ) {
      ReplyAppContent(
        navigationType = navigationType,
        contentType = contentType,
        replyUiState = replyUiState,
        onTabPressed = onTabPressed,
        onEmailCardPressed = onEmailCardPressed,
        navigationItemContentList = navigationItemContentList,
        modifier = modifier,
      )
    }
  } else {
    if (replyUiState.isShowingHomepage) {
      ReplyAppContent(
        navigationType = navigationType,
        contentType = contentType,
        replyUiState = replyUiState,
        onTabPressed = onTabPressed,
        onEmailCardPressed = onEmailCardPressed,
        navigationItemContentList = navigationItemContentList,
        modifier = modifier,
      )
    } else {
      ReplyDetailsScreen(
        replyUiState = replyUiState,
        isFullScreen = true,
        onBackPressed = onDetailScreenBackPressed,
        modifier = modifier
      )
    }
  }
}

@Composable
private fun ReplyAppContent(
  navigationType: ReplyNavigationType,
  contentType: ReplyContentType,
  replyUiState: ReplyUiState,
  onTabPressed: (MailboxType) -> Unit,
  onEmailCardPressed: (Email) -> Unit,
  navigationItemContentList: List<NavigationItemContent>,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    Row(modifier = Modifier.fillMaxSize()) {
      AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
        val navigationRailContentDescription = stringResource(R.string.navigation_rail)
        ReplyNavigationRail(
          currentTab = replyUiState.currentMailboxType,
          onTabPressed = onTabPressed,
          navigationItemContentList = navigationItemContentList,
          modifier = Modifier.testTag(navigationRailContentDescription)
        )
      }
      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.inverseOnSurface)
      ) {
        if (contentType == ReplyContentType.LIST_AND_DETAILS) {
          ReplyListAndDetailsContent(
            replyUiState = replyUiState,
            onEmailCardPressed = onEmailCardPressed,
            modifier = Modifier.weight(1f)
          )
        } else {
          ReplyListOnlyContent(
            replyUiState = replyUiState,
            onEmailCardPressed = onEmailCardPressed,
            modifier = Modifier
              .weight(1f)
              .padding(
                horizontal = dimensionResource(R.dimen.email_list_only_horizontal_padding)
              )
          )
        }
        AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
          ReplyBottomNavigationBar(
            currentTab = replyUiState.currentMailboxType,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList,
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
  }
}

@Composable
private fun ReplyNavigationRail(
  currentTab: MailboxType,
  onTabPressed: (MailboxType) -> Unit,
  navigationItemContentList: List<NavigationItemContent>,
  modifier: Modifier = Modifier
) {
  NavigationRail(modifier = modifier) {
    for (navItem in navigationItemContentList) {
      NavigationRailItem(
        selected = currentTab == navItem.mailboxType,
        onClick = { onTabPressed(navItem.mailboxType) },
        icon = {
          Icon(
            imageVector = navItem.icon,
            contentDescription = navItem.text
          )
        }
      )
    }
  }
}

@Composable
private fun ReplyBottomNavigationBar(
  currentTab: MailboxType,
  onTabPressed: (MailboxType) -> Unit,
  navigationItemContentList: List<NavigationItemContent>,
  modifier: Modifier = Modifier
) {
  NavigationBar(modifier = modifier) {
    for (navItem in navigationItemContentList) {
      NavigationBarItem(
        selected = currentTab == navItem.mailboxType,
        onClick = { onTabPressed(navItem.mailboxType) },
        icon = {
          Icon(
            imageVector = navItem.icon,
            contentDescription = navItem.text
          )
        }
      )
    }
  }
}

@Composable
private fun NavigationDrawerContent(
  selectedDestination: MailboxType,
  onTabPressed: (MailboxType) -> Unit,
  navigationItemContentList: List<NavigationItemContent>,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    NavigationDrawerHeader(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.profile_image_padding)),
    )
    for (navItem in navigationItemContentList) {
      NavigationDrawerItem(
        selected = selectedDestination == navItem.mailboxType,
        label = {
          Text(
            text = navItem.text,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
          )
        },
        icon = {
          Icon(
            imageVector = navItem.icon,
            contentDescription = navItem.text
          )
        },
        colors = NavigationDrawerItemDefaults.colors(
          unselectedContainerColor = Color.Transparent
        ),
        onClick = { onTabPressed(navItem.mailboxType) }
      )
    }
  }
}

@Composable
private fun NavigationDrawerHeader(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    ReplyLogo(modifier = Modifier.size(dimensionResource(R.dimen.reply_logo_size)))
    ReplyProfileImage(
      drawableResource = AccountDataProvider.defaultAccount.avatar,
      description = stringResource(id = R.string.profile),
      modifier = Modifier.size(dimensionResource(R.dimen.profile_image_size))
    )
  }
}

private data class NavigationItemContent(
  val mailboxType: MailboxType,
  val icon: ImageVector,
  val text: String
)

@Preview
@Composable
fun ReplyHomeScreenPreview() {
  ReplyAppTheme {
    ReplyHomeScreen(
      navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER,
      contentType = ReplyContentType.LIST_AND_DETAILS,
      replyUiState = ReplyUiState(
        mailboxes = EmailDataProvider.allEmails.groupBy { it.mailbox },
        currentMailboxType = MailboxType.Inbox,
        currentSelectedEmail = EmailDataProvider.defaultEmail
      ),
      onTabPressed = {},
      onEmailCardPressed = {},
      onDetailScreenBackPressed = {},
    )
  }
}
