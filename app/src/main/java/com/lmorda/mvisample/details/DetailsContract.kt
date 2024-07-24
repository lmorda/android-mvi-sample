package com.lmorda.mvisample.details

import com.lmorda.mvisample.data.RepoDetailsDto

interface DetailsContract {

    sealed class State {
        data object Initial : State()
        data object Loading : State()
        data class Loaded(val detailsDto: RepoDetailsDto) : State()
        data class LoadError(val errorMessage: String) : State()
    }

    sealed class Event {
        abstract class Internal : Event()
        data class OnLoadClick(val id: Long) : Event()
    }
}