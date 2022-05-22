package com.hfmarin.meliapp.presentation.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hfmarin.meliapp.MeliApplication
import com.hfmarin.meliapp.presentation.components.ITEM_IMAGE_HEIGHT
import com.hfmarin.meliapp.presentation.components.ItemView
import com.hfmarin.meliapp.presentation.components.LoadingItemViewShimmer
import com.hfmarin.meliapp.presentation.theme.AppTheme
import com.hfmarin.meliapp.presentation.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class ItemFragment : Fragment() {

    @Inject
    lateinit var application: MeliApplication
    private val snackbarController = SnackbarController(lifecycleScope)
    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("itemId")?.let {
            itemViewModel.onTriggerEvent(ItemEvent.GetItemEvent(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = itemViewModel.loading.value
                val item = itemViewModel.item.value
                val error = itemViewModel.error.value
                val scaffoldState = rememberScaffoldState()
                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                            if (error.isNotEmpty()) {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = error,
                                    actionLabel = "Hide"
                                )
                            }
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (loading && item == null) {
                                LoadingItemViewShimmer(imageHeight = ITEM_IMAGE_HEIGHT.dp)
                            } else {
                                item?.let {
                                    ItemView(item = it)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}