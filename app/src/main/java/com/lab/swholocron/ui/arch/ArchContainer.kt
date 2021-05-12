package com.lab.swholocron.ui.arch

import kotlinx.coroutines.flow.StateFlow

/**
 * Where the architecture pattern logic lives and runs
 */
interface ArchContainer {
    fun getStateFlow(): StateFlow<ArchState>
    fun process(event: ArchEvent)
}


