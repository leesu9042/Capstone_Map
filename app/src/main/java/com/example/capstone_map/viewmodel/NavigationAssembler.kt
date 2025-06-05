package com.example.capstone_map.viewmodel


import com.example.capstone_map.voice.*



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

class NavigationAssembler(
    private val context: Context,
    private val owner: ViewModelStoreOwner
) {

    
    // 공통 상태를 가지는 뷰모델
    val stateViewModel: NavigationStateViewModel by lazy {
        ViewModelProvider(owner)[NavigationStateViewModel::class.java]
    }

    private val _ttsManager: TTSManager by lazy { TTSManager(context) }
    private val _sttManager: STTManager by lazy { STTManager(context) }

    val ttsManager: TTSManager get() = _ttsManager
    val sttManager: STTManager get() = _sttManager

    // ViewModel 팩토리 모음 (확장 쉽게)
    private val factoryMap: Map<KClass<out ViewModel>, ViewModelProvider.Factory> = mapOf(
        DestinationViewModel::class to DestinationViewModelFactory(stateViewModel, ttsManager, sttManager),
        //ConfirmationViewModel::class to ConfirmationViewModelFactory(stateViewModel, ttsManager)
    )


    // generic하게 ViewModel을 가져오는 함수
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getViewModel(vmClass: KClass<T>): T {
        val factory = factoryMap[vmClass] ?: throw IllegalArgumentException("Factory not found")
        return ViewModelProvider(owner, factory)[vmClass.java]
    }




    // 편의상 개별 접근자도 지원 (필요 시)
    val destinationViewModel: DestinationViewModel by lazy {
        getViewModel(DestinationViewModel::class)
    }

//    val confirmationViewModel: ConfirmationViewModel by lazy {
//        getViewModel(ConfirmationViewModel::class)
//    }
    
    
    
}




//✅ NavigationAssembler란?
//"여러 개의 객체(ViewModel, Manager 등)를 한 번에 묶어서 초기화하고,
//그걸 외부에서 간단하게 꺼내 쓸 수 있게 만들어주는 조립기(assembler)"

