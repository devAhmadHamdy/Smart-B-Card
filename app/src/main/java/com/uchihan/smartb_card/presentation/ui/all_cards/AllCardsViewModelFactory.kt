package com.uchihan.smartb_card.presentation.ui.all_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uchihan.smartb_card.domain.use_case.DeleteCardUseCase
import com.uchihan.smartb_card.domain.use_case.GetAllCardsUseCase

class AllCardsViewModelFactory(private val getAllCardsUseCase: GetAllCardsUseCase,private val deleteCardUseCase: DeleteCardUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllCardsViewModel::class.java)) {
            return AllCardsViewModel(getAllCardsUseCase,deleteCardUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}