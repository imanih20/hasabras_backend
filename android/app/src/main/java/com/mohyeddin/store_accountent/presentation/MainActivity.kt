package com.mohyeddin.store_accountent.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.common.checkAndRequestInternetPermission
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.presentation.NavGraphs
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.theme.Store_accountentTheme
import com.mohyeddin.store_accountent.presentation.destinations.MainScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.SignScreenDestination
import com.mohyeddin.store_accountent.presentation.main.MainScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.utils.startDestination
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs : Prefs = koinInject()
            Store_accountentTheme {
                var showNoticeDialog by remember {
                    mutableStateOf(false)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()){
                            showNoticeDialog = !it.values.reduce{acc,next -> acc && next}
                        }
                        checkAndRequestInternetPermission(this@MainActivity,launcher)
                        val startRoute = if (prefs.getIsSigned()) MainScreenDestination else SignScreenDestination
                        if (showNoticeDialog){
                            MyDialog(
                                onDismissRequest = {
                                    this.finish()
                                },
                                onPositiveBtnClick = {
                                    checkAndRequestInternetPermission(this@MainActivity, launcher)
                                },
                                positiveBtnTxt = "اجازه می دهم",
                                negativeBtnText = "خارج می شوم"
                            ) {
                                MyText(text = "برای استفاده نیاز است که برنامه اجازه دسترسی به اینترنت را داشته باشد. اجازه می دهید؟")
                            }
                        }else {
                            DestinationsNavHost(navGraph = NavGraphs.root, startRoute = startRoute)
                        }
                    }
                }
            }
        }
    }
}
