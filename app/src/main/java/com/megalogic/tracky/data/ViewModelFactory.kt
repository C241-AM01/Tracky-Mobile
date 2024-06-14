package com.megalogic.tracky.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.megalogic.tracky.ui.assetlist.AdminAssetListViewModel
import com.megalogic.tracky.ui.detail.DetailViewModel
import com.megalogic.tracky.ui.home.HomeViewModel
import com.megalogic.tracky.ui.login.LoginViewModel

class ViewModelFactory private constructor(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AdminAssetListViewModel::class.java) -> {
                return AdminAssetListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }




    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context : Context):ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
                    .also { instance = it }
            }
    }

}