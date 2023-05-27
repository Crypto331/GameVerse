package com.mobbelldev.gameverse.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobbelldev.gameverse.bookmark.ui.BookmarkViewModel
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(gameUseCase) as T
            }

            else -> throw Throwable("Unkwnown View model class: ${modelClass.name}")
        }
}
