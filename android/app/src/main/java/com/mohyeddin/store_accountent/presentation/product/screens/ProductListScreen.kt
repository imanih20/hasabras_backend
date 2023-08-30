package com.mohyeddin.store_accountent.presentation.product.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.product.components.ProductListShimmer
import com.mohyeddin.store_accountent.presentation.product.viewmodels.ProductScreenState
import com.mohyeddin.store_accountent.presentation.product.viewmodels.ProductViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun ProductListScreen(viewModel: ProductViewModel = koinViewModel()) {
    val products = viewModel.products.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    MyScaffold(viewModel = viewModel) {
        if (isLoading.value){
            ProductListShimmer()
        }else{
            ProductListScreenContent(products.value){
                viewModel.addSaleToDb(it)
            }
        }
    }

}

@Composable
fun ProductListScreenContent(productList: List<Product>,addToList:(Product)->Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {
        MyInputTextField(
            value = searchQuery,
            onValueChange = {searchQuery = it},
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "" )
            }
        )
        if (productList.isEmpty()) Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            MyText(text ="محصولی در برنامه ثبت نشده است.")
        }else{
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn{
                items(productList.filter { it.title.contains(searchQuery) }){
                    ProductListItem(product = it){
                        addToList(it)
                    }
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }

        }
    }
}

@Composable
fun ProductListItem(product: Product,onAddBtnClick:()->Unit) {
    val unit = if (product.unit == "n") "عدد" else "کیلو"
    val quantity = if(product.unit == "n") product.quantity.toInt() else product.quantity
    Card(elevation = CardDefaults.cardElevation(1.dp), colors = CardDefaults.cardColors(contentColor = Color.Black), modifier = Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                , horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                MyText(text = product.title)
                MyText(text = "${product.price} تومان")

            }
            Spacer(modifier = Modifier.size(5.dp))
            Divider()
            Spacer(modifier = Modifier.size(5.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyText(text = "$quantity $unit موجود است")
                Button(
                    onClick = { onAddBtnClick() },
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = product.quantity > 0
                ) {
                    MyText(text = "اضافه به لیست فروش", fontSize = 12.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    val productList = listOf(
        Product("","شیر", price = 10000,10.0,2000,"")
    )
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface {
            ProductListScreenContent(productList = productList){

            }
        }
        
    }
}