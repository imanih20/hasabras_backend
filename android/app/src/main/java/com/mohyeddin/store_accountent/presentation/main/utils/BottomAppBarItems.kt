package com.mohyeddin.store_accountent.presentation.main.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.mohyeddin.store_accountent.presentation.destinations.DashboardScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.ProductListScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.StatisticScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.TradeScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.TypedDestination
import com.mohyeddin.store_accountent.presentation.destinations.UserInfoScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import compose.icons.TablerIcons
import compose.icons.tablericons.ChartBar
import compose.icons.tablericons.Dashboard
import compose.icons.tablericons.ListSearch
import compose.icons.tablericons.ReportMoney
import compose.icons.tablericons.User
import compose.icons.tablericons.UserX

enum class BottomAppBarItems(
    val icon: ImageVector,
    val title: String,
    val direction: DirectionDestinationSpec
) {
    Dashboard(TablerIcons.Dashboard,"داشبورد",DashboardScreenDestination),
    ProductList(TablerIcons.ListSearch,"محصولات",ProductListScreenDestination),
    TradeList(TablerIcons.ReportMoney,"گزارشات",TradeScreenDestination),
    Statistics(TablerIcons.ChartBar,"آمار",StatisticScreenDestination),
    User(TablerIcons.User,"کاربر",UserInfoScreenDestination);

    companion object{
        fun getTitleFromDirection(direction: TypedDestination<*>): String {
            return values().firstOrNull() { it.direction  == direction }?.title ?: ""
        }
    }
}