package com.turingalan.pokemon.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import com.turingalan.pokemon.R
import com.turingalan.pokemon.ui.detail.DetailUiState
import com.turingalan.pokemon.ui.detail.PokemonDetailViewModel
import com.turingalan.pokemon.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    backStackEntry: NavBackStackEntry? = null,
    onBack: () -> Unit = {},
) {
    val isDetail = backStackEntry?.destination?.hasRoute<Route.Detail>() == true

    if (isDetail && backStackEntry != null) {
        DetailTopBar(backStackEntry = backStackEntry, onBack = onBack)
    } else {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    backStackEntry: NavBackStackEntry,
    onBack: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(viewModelStoreOwner = backStackEntry),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val title = when (val state = uiState) {
        is DetailUiState.Success -> state.name
        else -> ""
    }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }
    )
}