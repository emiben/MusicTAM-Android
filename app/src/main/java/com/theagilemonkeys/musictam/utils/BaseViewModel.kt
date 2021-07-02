package com.theagilemonkeys.musictam.utils

import androidx.lifecycle.*
import com.theagilemonkeys.musictam.models.Response
import com.theagilemonkeys.musictam.network.repositories.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableLiveData<Resource<T>>()
    val uiState: LiveData<Resource<T>> = _uiState
    val isLoading = Transformations.map(uiState) { it.status == Status.LOADING }
    val isSuccess =
        Transformations.map(_uiState) { it.status == Status.SUCCESS }
    val isError = Transformations.map(_uiState) { it.status == Status.ERROR }


    private val _contentLiveData = MediatorLiveData<T>()
        .apply {
            addSource(_uiState) {
                if (it.status == Status.SUCCESS) {

                    this.value = it.data
                }
            }
        }
    val contentLiveData: LiveData<T> = _contentLiveData


    fun executeAPI(vararg args: String) {

        _uiState.postValue(Resource.loading(data = null))

        viewModelScope.launch(dispatcher) {
            val resource = try {
                val response = apiCall(*args)
                if (response?.status == Status.SUCCESS) {
                    Resource.success(
                        data = response.data
                    ) as? Resource<T>

                } else {
                    val errorMessage =
                        "Error: ${response?.message}"
                    getErrorResource(errorMessage)
                }
            } catch (exception: Exception) {
                val errorMessage = exception.message ?: "Error Occurred!"
                getErrorResource(errorMessage)
            }
            resource.let { _uiState.postValue(it) }
        }
    }

    abstract suspend fun apiCall(vararg args: String): Resource<T>?

    private fun getErrorResource(errorMessage: String): Resource<T> {
        return Resource.error(
            data = null,
            message = errorMessage
        )
    }
}