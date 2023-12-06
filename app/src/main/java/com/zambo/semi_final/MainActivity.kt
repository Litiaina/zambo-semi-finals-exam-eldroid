package com.zambo.semi_final

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zambo.semi_final.data.Timestamp
import com.zambo.semi_final.data.Tweet
import com.zambo.semi_final.data.TweetRequest
import com.zambo.semi_final.data.TweetResponse
import com.zambo.semi_final.databinding.ActivityMainBinding
import com.zambo.semi_final.databinding.CreateTweetDialogBinding
import com.zambo.semi_final.databinding.EditTextDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tweetAdapter: TweetAdapter

    companion object {
        var tweetApi: TweetApi? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tweetAdapter = TweetAdapter(this)
        tweetApi = TweetApi(tweetAdapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = tweetAdapter

        tweetApi?.fetchTweets()

        binding.tweetButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogBinding = CreateTweetDialogBinding.inflate(LayoutInflater.from(this))
            builder.apply {
                setTitle("Send tweet?")
                setView(dialogBinding.root)
                setPositiveButton("Send") { _, _ ->
                    tweetApi?.createTweet(TweetRequest(dialogBinding.nameEditText.text.toString(), dialogBinding.descriptionEditText.text.toString()))
                    Toast.makeText(context, "Tweet sent!", Toast.LENGTH_SHORT).show()
                }
            }
            builder.create().show()
        }

    }

}