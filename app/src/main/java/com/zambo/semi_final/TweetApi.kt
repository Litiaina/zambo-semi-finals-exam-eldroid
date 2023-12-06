package com.zambo.semi_final

import android.util.Log
import com.zambo.semi_final.data.Tweet
import com.zambo.semi_final.data.TweetRequest
import com.zambo.semi_final.data.TweetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TweetApi(private val tweetAdapter: TweetAdapter) {

    private var tweetApiService: TweetApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://eldroid.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tweetApiService = retrofit.create(TweetApiService::class.java)
    }
    fun fetchTweets() {
        val call: Call<List<Tweet>> = tweetApiService.getTweets()
        call.enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if (response.isSuccessful) {
                    val tweets: List<Tweet> = response.body() ?: emptyList()
                    tweetAdapter.setTweets(tweets)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("FetchTweets", "Error fetching tweets: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Log.e("FetchTweets", "Failed to fetch tweets", t)
            }
        })
    }

    fun createTweet(tweetRequest: TweetRequest) {
        val call: Call<Tweet> = tweetApiService.createTweet(tweetRequest)
        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    val createdTweet: Tweet? = response.body()
                    Log.d("CreateTweet", "Tweet created: ${createdTweet?.id}")
                    fetchTweets()
                } else {
                    Log.e("CreateTweet", "Error creating tweet")
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                // Handle failure
                Log.e("CreateTweet", "Failed to create tweet", t)
            }
        })
    }

    fun updateTweet(tweetId: String, tweetRequest: TweetRequest) {
        val call: Call<Tweet> = tweetApiService.updateTweet(tweetId, tweetRequest)
        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    val updatedTweet: Tweet? = response.body()
                    Log.d("UpdateTweet", "Tweet updated: ${updatedTweet?.id}")
                    fetchTweets()
                } else {
                    Log.e("UpdateTweet", "Error updating tweet")
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                Log.e("UpdateTweet", "Failed to update tweet", t)
            }
        })
    }

    fun deleteTweet(tweetId: String) {
        val call: Call<TweetResponse> = tweetApiService.deleteTweet(tweetId)
        call.enqueue(object : Callback<TweetResponse> {
            override fun onResponse(call: Call<TweetResponse>, response: Response<TweetResponse>) {
                if (response.isSuccessful) {
                    val tweetResponse: TweetResponse? = response.body()
                    Log.d("DeleteTweet", "Tweet deleted: ${tweetResponse?.id}")
                    fetchTweets() // Refresh the list after deleting a tweet
                } else {
                    // Handle error
                    Log.e("DeleteTweet", "Error deleting tweet")
                }
            }

            override fun onFailure(call: Call<TweetResponse>, t: Throwable) {
                // Handle failure
                Log.e("DeleteTweet", "Failed to delete tweet", t)
            }
        })
    }
}