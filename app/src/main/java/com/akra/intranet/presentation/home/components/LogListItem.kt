package com.akra.intranet.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.akra.intranet.R
import com.akra.intranet.common.formatToDateString
import com.akra.intranet.domain.model.Log

@Composable
fun LogListItem(
    modifier: Modifier = Modifier,
    item: Log,
    onItemClick: (Log) -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .border(BorderStroke(1.dp, Color.Gray))
            .padding(8.dp)
            .clickable { onItemClick(item) }
    ) {

        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic
            )
        }

        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.date_msg, item.date.formatToDateString())
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.from_to_msg, item.from, item.to)
            )
        }
    }
}