package com.hfmarin.meliapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.util.DEFAULT_ITEM_IMAGE
import com.hfmarin.meliapp.util.loadPicture

@Composable
fun ItemView(item: Item) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            item.thumbnail.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_ITEM_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "item picture",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                ITEM_IMAGE_HEIGHT.dp
                            ),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        item.title.let { title ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = title,
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .wrapContentWidth(Alignment.Start),
                                    style = MaterialTheme.typography.h3
                                )
                                val price = "${item.currency_id} ${item.price}"
                                Text(
                                    text = price,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.End)
                                        .align(Alignment.CenterVertically),
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        }
                        Text(
                            text = item.id,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.caption
                        )
                        for (attribute in item.attributes) {
                            Text(
                                text = "${attribute.name} ${attribute.value}",
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .wrapContentWidth(),
                                style = MaterialTheme.typography.body1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

const val ITEM_IMAGE_HEIGHT = 260