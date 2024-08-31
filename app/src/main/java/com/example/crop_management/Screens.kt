package com.example.crop_management

sealed class Screens(val route:String){
    data object Home:Screens("home")
    data object Profile22:Screens("profile")
    data object About_Us:Screens("aboutus")
    data object Notifications:Screens("notifications")
    data object Settings:Screens("settings")
}