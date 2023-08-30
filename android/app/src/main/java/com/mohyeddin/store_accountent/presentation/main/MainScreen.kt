package com.mohyeddin.store_accountent.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mohyeddin.store_accountent.presentation.NavGraphs
import com.mohyeddin.store_accountent.presentation.appCurrentDestinationAsState
import com.mohyeddin.store_accountent.presentation.common.components.BadgeIcon
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.destinations.AddShareholderScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.PurchaseListScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.SaleListScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.TypedDestination
import com.mohyeddin.store_accountent.presentation.main.utils.BottomAppBarItems
import com.mohyeddin.store_accountent.presentation.main.viewmodel.MainViewModel
import com.mohyeddin.store_accountent.presentation.startAppDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.startDestination
import compose.icons.LineAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.lineawesomeicons.ShoppingBagSolid
import compose.icons.tablericons.ShoppingCart
import compose.icons.tablericons.Users
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = koinViewModel()) {
    val controller = rememberNavController()
    val saleCount = viewModel.saleCount.collectAsState()
    val purchaseCount = viewModel.purchaseCount.collectAsState()
    val currentDestination: TypedDestination<out Any?> = controller.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomAppBarItems.values().forEach {
                    BottomBarItem(icon = it.icon, action = {
                        controller.navigate(it.direction){
                            launchSingleTop = true
                        }
                    }, isSelected = currentDestination == it.direction)
                }
            }
        },
        topBar = {
            MediumTopAppBar(
                title = { MyText(text = BottomAppBarItems.getTitleFromDirection(currentDestination)) },
                actions = {
                    BadgeIcon(icon = LineAwesomeIcons.ShoppingBagSolid,number = saleCount.value){
                        navigator.navigate(SaleListScreenDestination)
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    BadgeIcon(icon = TablerIcons.ShoppingCart,number=purchaseCount.value) {
                        navigator.navigate(PurchaseListScreenDestination)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    IconButton(onClick = {
                        navigator.navigate(AddShareholderScreenDestination())
                    }) {
                        IconBox(imageVector = TablerIcons.Users)
                    }
                },
                modifier = Modifier.padding(5.dp)
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            DestinationsNavHost(navGraph = NavGraphs.bottomBar, navController = controller)
        }
    }
}


@Composable
fun BottomBarItem(
    icon: ImageVector,
    action: ()->Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val backColor = if(isSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
    Column(
        modifier
            .padding(10.dp)
            .animateContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = action) {
            IconBox(imageVector = icon, tint = backColor)
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MainScreen(EmptyDestinationsNavigator)
    }
}