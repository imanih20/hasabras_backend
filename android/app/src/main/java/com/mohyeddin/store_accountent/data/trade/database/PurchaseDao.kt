package com.mohyeddin.store_accountent.data.trade.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import kotlinx.coroutines.flow.Flow


@Dao
interface PurchaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(purchase: Purchase)


    @Query("SELECT * FROM Purchase")
    fun getAll() : Flow<List<Purchase>>

    @Delete
    suspend fun delete(vararg purchase: Purchase)

    @Query("DELETE FROM Purchase")
    suspend fun deleteAll()

    @Update
    suspend fun update(purchase: Purchase)

    @Query("SELECT COUNT(id) FROM Purchase")
    fun countPurchases() : Flow<Int>
}