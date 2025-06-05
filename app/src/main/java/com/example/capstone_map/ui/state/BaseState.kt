package com.example.capstone_map.ui.state

interface BaseState<VM> {
    fun handle(viewModel: VM) {}
    fun onPrimaryInput(viewModel: VM) {}
    fun onSecondaryInput(viewModel: VM) {}
    fun onTertiaryInput(viewModel: VM) {}
}
