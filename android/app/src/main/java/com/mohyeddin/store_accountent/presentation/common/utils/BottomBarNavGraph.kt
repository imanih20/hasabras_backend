package com.mohyeddin.store_accountent.presentation.common.utils

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class BottomBarNavGraph(
    val start : Boolean = false
)