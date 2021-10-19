package net.matrixhome.kino_test

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import net.matrixhome.kino_test.api.KinoService
import net.matrixhome.kino_test.data.KinoRepository
import net.matrixhome.kino_test.ui.ViewModelFactory

object Injection {
    private fun provideMovieRepository(): KinoRepository{
        return KinoRepository(KinoService.create())
    }


    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory{
        return ViewModelFactory(owner, provideMovieRepository())

    }
}