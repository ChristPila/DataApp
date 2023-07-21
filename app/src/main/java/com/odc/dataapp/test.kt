package com.odc.dataapp

fun main(){
    print ("entrez une valeur : ")
    var nbr:Double = readLine()!!.toDouble()
    var grp: Double
    var a : Int
    if (nbr % 4 == 0.0){
        grp = (nbr/4)
        println("Le nombre de groupe sera : $grp")
    } else{
        grp = (nbr/4)
        a = grp.toInt() + 1
        println("Le nombre de groupe sera : $a")
    }
}