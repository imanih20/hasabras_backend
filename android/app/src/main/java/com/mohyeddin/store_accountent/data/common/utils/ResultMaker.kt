package com.mohyeddin.store_accountent.data.common.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class ResultMaker<T : Any,U : Any>() {
    fun getResult(response: Response<U>?) : Flow<BaseResult<T, WrappedResponse<U>>> {
        if (response == null){
            throw NetworkNotConnectedException()
        }
        return flow {
            if (response.isSuccessful){
                val body = response.body()
                Log.i("response_tag",response.body().toString())
                val model = body?.let {
                    getModel(it)
                }
                emit(BaseResult.Success(model!!))
            } else {
                val err = getWrappedResponse(response)
                emit(BaseResult.Error(err))
            }
        }
    }

    fun getListResult(response: Response<List<U>>?): Flow<BaseResult<List<T>, WrappedListResponse<U>>>{
        if (response == null){
            throw NetworkNotConnectedException()
        }
        return flow {
            if (response.isSuccessful){
                val body = response.body()
                val modelList = mutableListOf<T>()
                body?.forEach{
                    modelList.add(getModel(it))
                }
                emit(BaseResult.Success(modelList))
            } else {
                val err = getWrappedListResponse(response)
                emit(BaseResult.Error(err))
            }
        }
    }

    protected abstract fun getModel(res: U) : T

    private fun getWrappedResponse(response: Response<U>): WrappedResponse<U> {
        val type = object : TypeToken<WrappedResponse<U>>(){}.type
        val err = Gson().fromJson<WrappedResponse<U>>(response.errorBody()!!.charStream(), type)!!
        err.code = response.code()
        return err
    }

    private fun getWrappedListResponse(response: Response<List<U>>): WrappedListResponse<U> {
        val type = object : TypeToken<WrappedListResponse<U>>(){}.type
        val err = Gson().fromJson<WrappedListResponse<U>>(response.errorBody()!!.charStream(), type)!!
        err.code = response.code()
        return err
    }
}