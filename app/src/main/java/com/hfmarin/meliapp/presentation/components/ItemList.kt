package com.hfmarin.meliapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfmarin.meliapp.R
import com.hfmarin.meliapp.domain.model.Item

@ExperimentalMaterialApi
@Composable
fun ItemList(
    loading: Boolean,
    itemList: List<Item>,
    onChangeItemScrollPosition: (Int) -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (loading && itemList.isEmpty()) {
            LoadingItemListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(items = itemList) { index, item ->
                    onChangeItemScrollPosition(index)
                    ItemCard(item = item, onClick = {
                        val bundle = Bundle()
                        bundle.putString("itemId", item.id)
                        navController.navigate(R.id.viewItem, bundle)
                    })
                }
            }
        }
    }
}