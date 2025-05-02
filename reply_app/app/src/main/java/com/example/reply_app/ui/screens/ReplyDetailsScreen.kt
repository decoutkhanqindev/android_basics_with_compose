package com.example.reply_app.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.reply_app.R
import com.example.reply_app.data.EmailDataProvider
import com.example.reply_app.model.Email
import com.example.reply_app.model.MailboxType
import com.example.reply_app.ui.theme.ReplyAppTheme

@Composable
fun ReplyDetailsScreen(
  replyUiState: ReplyUiState,
  onBackPressed: () -> Unit,
  isFullScreen: Boolean = false,
  modifier: Modifier = Modifier
) {
  BackHandler {
    onBackPressed()
  }

  Box(modifier = modifier) {
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top))
    ) {
      item {
        if (isFullScreen) {
          ReplyDetailsScreenTopBar(
            replyUiState = replyUiState,
            onBackButtonClicked = onBackPressed,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = dimensionResource(R.dimen.detail_topbar_padding_bottom))
          )
        }
        ReplyEmailDetailsCard(
          email = replyUiState.currentSelectedEmail,
          mailboxType = replyUiState.currentMailboxType,
          isFullScreen = isFullScreen,
          modifier = if (isFullScreen) {
            Modifier.padding(horizontal = dimensionResource(R.dimen.detail_card_outer_padding_horizontal))
          } else {
            Modifier.padding(end = dimensionResource(R.dimen.detail_card_outer_padding_horizontal))
          }
        )
      }
    }
  }
}

@Composable
private fun ReplyDetailsScreenTopBar(
  replyUiState: ReplyUiState,
  onBackButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(
      onClick = onBackButtonClicked,
      modifier = Modifier
        .padding(horizontal = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal))
        .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = stringResource(id = R.string.navigation_back)
      )
    }
    Row(
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(end = dimensionResource(R.dimen.detail_subject_padding_end))
    ) {
      Text(
        text = stringResource(replyUiState.currentSelectedEmail.subject),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun ReplyEmailDetailsCard(
  email: Email,
  mailboxType: MailboxType,
  isFullScreen: Boolean = false,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val displayToast = { text: String ->
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
  }
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.detail_card_inner_padding))
    ) {
      DetailsScreenHeader(
        email = email,
        modifier = Modifier.fillMaxWidth()
      )
      if (isFullScreen) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_content_padding_top)))
      } else {
        Text(
          text = stringResource(email.subject),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.outline,
          modifier = Modifier.padding(
            top = dimensionResource(R.dimen.detail_content_padding_top),
            bottom = dimensionResource(R.dimen.detail_expanded_subject_body_spacing)
          ),
        )
      }
      Text(
        text = stringResource(email.body),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      DetailsScreenButtonBar(mailboxType, displayToast)
    }
  }
}

@Composable
private fun DetailsScreenHeader(
  email: Email,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    ReplyProfileImage(
      drawableResource = email.sender.avatar,
      description = stringResource(email.sender.firstName) + " " + stringResource(email.sender.lastName),
      modifier = Modifier.size(
        dimensionResource(R.dimen.email_header_profile_size)
      )
    )
    Column(
      modifier = Modifier
        .weight(1f)
        .padding(
          horizontal = dimensionResource(R.dimen.email_header_content_padding_horizontal),
          vertical = dimensionResource(R.dimen.email_header_content_padding_vertical)
        ),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = stringResource(email.sender.firstName),
        style = MaterialTheme.typography.labelMedium
      )
      Text(
        text = stringResource(email.createdAt),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.outline
      )
    }
  }
}

@Composable
private fun DetailsScreenButtonBar(
  mailboxType: MailboxType,
  displayToast: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    when (mailboxType) {
      MailboxType.Drafts ->
        ActionButton(
          text = stringResource(id = R.string.continue_composing),
          onButtonClicked = displayToast
        )

      MailboxType.Spam ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)),
          horizontalArrangement =
            Arrangement.spacedBy(dimensionResource(R.dimen.detail_button_bar_item_spacing)),
        ) {
          ActionButton(
            text = stringResource(id = R.string.move_to_inbox),
            onButtonClicked = displayToast,
            modifier = Modifier.weight(1f)
          )
          ActionButton(
            text = stringResource(id = R.string.delete),
            onButtonClicked = displayToast,
            containIrreversibleAction = true,
            modifier = Modifier.weight(1f)
          )
        }

      MailboxType.Sent,
      MailboxType.Inbox ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)),
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.detail_button_bar_item_spacing)),
        ) {
          ActionButton(
            text = stringResource(id = R.string.reply),
            onButtonClicked = displayToast,
            modifier = Modifier.weight(1f)
          )
          ActionButton(
            text = stringResource(id = R.string.reply_all),
            onButtonClicked = displayToast,
            modifier = Modifier.weight(1f)
          )
        }
    }
  }
}

@Composable
private fun ActionButton(
  text: String,
  onButtonClicked: (String) -> Unit,
  modifier: Modifier = Modifier,
  containIrreversibleAction: Boolean = false,
) {
  Box(modifier = modifier) {
    Button(
      onClick = { onButtonClicked(text) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = dimensionResource(R.dimen.detail_action_button_padding_vertical)),
      colors = ButtonDefaults.buttonColors(
        containerColor = if (containIrreversibleAction) {
          MaterialTheme.colorScheme.onErrorContainer
        } else {
          MaterialTheme.colorScheme.primaryContainer
        }
      )
    ) {
      Text(
        text = text,
        color = if (containIrreversibleAction) {
          MaterialTheme.colorScheme.onError
        } else {
          MaterialTheme.colorScheme.onSurfaceVariant
        }
      )
    }
  }
}

@Preview
@Composable
fun ReplyDetailsScreenPreview() {
  ReplyAppTheme {
    ReplyDetailsScreen(
      replyUiState = ReplyUiState(
        currentSelectedEmail = EmailDataProvider.allEmails[0]
      ),
      onBackPressed = { },
    )
  }
}