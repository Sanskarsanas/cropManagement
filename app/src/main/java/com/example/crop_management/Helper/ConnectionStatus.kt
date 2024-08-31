package com.example.crop_management.Helper

sealed class ConnectionStatus {
    object Available:ConnectionStatus()
    object Unavailable:ConnectionStatus()
}