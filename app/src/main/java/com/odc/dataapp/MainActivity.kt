package com.odc.dataapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.odc.dataapp.adapter.ListAdapterCovid
import com.odc.dataapp.databinding.ActivityMainBinding
import com.odc.dataapp.http.API
import com.odc.dataapp.http.Requettes
import com.odc.dataapp.models.Comments
import com.odc.dataapp.models.CommentsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    lateinit var adapter: ListAdapterCovid
    val allData = ArrayList<Comments>()

    // declarer variable à execution regulière
    var timer = Timer()

    var requetteComments: CommentsDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // arret service
        val intent = Intent(this, CommenatiresService::class.java)
        stopService(intent)

        // initialiser la liste recycleView
        initialiserListe()
        requetteComments = (application as MyApplication).db.commentsDAO()
        lifecycleScope.launch {
            recupererHttpDonnees()

        }

        lifecycleScope.launch {
            selectionnerDataDB()
        }

        lifecycleScope.launch {
            // insertions
            insererDansLaTable()
            insererDansLaTable()
        }
    }

    suspend fun insererDansLaTable(){
        withContext(Dispatchers.IO){
            Log.i("INSERT", "ACTION")
            var data = Comments(email = "agyf@gmail.com", body = "hisas")
            requetteComments?.insert(data )
        }
    }

    suspend fun selectionnerDataDB(){
        // gerer les requettes
        requetteComments?.select()?.collect {
            Log.i("DATA DB", it.toString())
        }
    }

    // Déclarer la classe qui va gerer l'execution régulière
    inner class TimeTask(): TimerTask(){
        override fun run() {
            Log.i("TIME", "EXECUTION")
            allData.add(Comments(id = allData.size+1, email = "Odc@orange.com", body = "Hello"))
            runOnUiThread {
                initialiserListe(allData)
            }

        }

    }

    fun initialiserListe(data : List<Comments> = listOf()){
        binding?.recycleListeV?.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,
            false)
        // adapter reçoit les données
        adapter = ListAdapterCovid(this, data.sortedByDescending { it.id })
        binding?.commentText?.text = "Commentaire (${data.size})"

        // attacher l'adapter à la vue xml
        binding?.recycleListeV?.adapter = adapter
    }

    suspend fun recupererHttpDonnees(){
        withContext(Dispatchers.IO) {
            val requettes = Requettes.getInstance().create(API::class.java)
            val resultat = requettes.covidDataHttp()

            if (resultat != null){
                Log.i("API", resultat.body().toString())
                val listCovid = resultat.body()

                runOnUiThread {
                    if (listCovid != null) {
                        allData.addAll(listCovid)
                        initialiserListe(listCovid)
                        timer.scheduleAtFixedRate(TimeTask(), 0, 10000)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ON DESTROY", "DESTRUCTION DE L'ACTIVITE")
        val intent = Intent(this, CommenatiresService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        }
    }
}