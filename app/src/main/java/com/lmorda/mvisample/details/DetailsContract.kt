package com.lmorda.mvisample.details

import com.lmorda.mvisample.domain.RepoDetailsDto

interface DetailsContract {

    sealed class State {
        data object Initial : State()
        data object Loading : State()
        data class Loaded(val detailsDto: RepoDetailsDto) : State()
        data object LoadError : State()
    }

    sealed class Event {
        abstract class Internal : Event()
        data class OnLoadClick(val id: Long) : Event()
    }
}
