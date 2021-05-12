package com.lab.swholocron.ui.arch

/**
 *  View that renders by state as part
 *  of architecture
 */
interface ArchView {
    fun render(state: ArchState)
}