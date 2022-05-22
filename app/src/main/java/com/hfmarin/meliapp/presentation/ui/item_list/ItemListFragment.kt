package com.hfmarin.meliapp.presentation.ui.item_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.hfmarin.meliapp.MeliApplication
import com.hfmarin.meliapp.presentation.components.ItemList
import com.hfmarin.meliapp.presentation.components.SearchAppBar
import com.hfmarin.meliapp.presentation.theme.AppTheme
import com.hfmarin.meliapp.presentation.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class ItemListFragment : Fragment() {

    @Inject
    lateinit var application: MeliApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    val itemListViewModel: ItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val itemList = itemListViewModel.items.value
                val query = itemListViewModel.query.value
                val error = itemListViewModel.error.value
                val loading = itemListViewModel.loading.value
                val scaffoldState = rememberScaffoldState()
                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState
                ) {
                    Scaffold(topBar = {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = itemListViewModel::onQueryChanged,
                            onExecuteSearch = {
                                itemListViewModel.onTriggerEvent(ItemListEvent.NewSearchEvent)
                            },
                            context = context,
                            onToggleTheme = {
                                application.toggleLightTheme()
                            }
                        )
                    },
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
                        })
                    {
                        ItemList(
                            loading = loading,
                            itemList = itemList,
                            onChangeItemScrollPosition = itemListViewModel::onChangeItemScrollPosition,
                            navController = findNavController()
                        )
                    }
                }
            }
        }
    }
}

