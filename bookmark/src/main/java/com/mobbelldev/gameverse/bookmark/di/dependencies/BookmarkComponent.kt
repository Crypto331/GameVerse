package com.mobbelldev.gameverse.bookmark.di.dependencies

import android.content.Context
import com.mobbelldev.gameverse.bookmark.ui.BookmarkFragment
import com.mobbelldev.gameverse.di.BookmarkModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [BookmarkModuleDependencies::class])
interface BookmarkComponent {
    fun inject(fragment: BookmarkFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(bookmarkModuleDependencies: BookmarkModuleDependencies): Builder
        fun build(): BookmarkComponent
    }
}