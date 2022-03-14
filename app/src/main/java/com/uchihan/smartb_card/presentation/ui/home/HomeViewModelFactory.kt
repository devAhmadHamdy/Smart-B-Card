package com.uchihan.smartb_card.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uchihan.smartb_card.domain.use_case.AddCardUseCase
import com.uchihan.smartb_card.domain.use_case.GetAllCardsUseCase

class HomeViewModelFactory(private val getAllCardsUseCase: GetAllCardsUseCase,private val addCardUseCase: AddCardUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getAllCardsUseCase,addCardUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}