package com.example.a230802

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a230802.database.UserDao
import com.example.a230802.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userDao: UserDao) : ViewModel() {

    val user = MutableLiveData<UserEntity>() //activity를 관찰하다 신호가 생김 함수를 호출함..

    //비지니스 로직 저장소
    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        val userData = userDao.getUser()
        launch(Dispatchers.Main) {
            user.value = userData
        }
    }

    fun insertUser(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        userDao.insertInfo(entity)
    }

    fun updateUser(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        userDao.updateInfo(entity)
    }

    fun deleteUser(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        userDao.deleteInfo(entity)
    }

    //provider로 반환한 뷰 모델을 받는 곳. ( ViewModelProvider -> viewModelProvider.factory -> viewModel -> viewModelStore)
    class ViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
        //viewModel 을 상속받는 아무 타입 / as: 강제 형변환
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(userDao) as T
            } else {
                throw IllegalAccessException()
            }
        }
    }


}