package com.mohyeddin.store_accountent.data.trade.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import kotlinx.coroutines.flow.Flow


@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: Sale)

    @Query("SELECT * FROM Sale WHERE date = :date AND productId = :productId" )
    suspend fun getDateSale(productId: String, date: String) : Sale?
    @Query("SELECT * FROM Sale")
    fun getList() : Flow<List<Sale>>

    @Delete
    suspend fun delete(vararg sale: Sale)

    @Query("DELETE FROM sale")
    suspend fun deleteAll()

    @Update
    suspend fun update(sale: Sale)

    @Query("SELECT COUNT(id) FROM sale")
    fun countSales() : Flow<Int>
}