package com.uchihan.smartb_card.presentation.ui.all_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uchihan.smartb_card.domain.use_case.NearbyUseCase

class AllCardsViewModelFactory(private val nearbyUseCase: NearbyUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllCardsViewModel::class.java)) {
            return AllCardsViewModel(nearbyUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}