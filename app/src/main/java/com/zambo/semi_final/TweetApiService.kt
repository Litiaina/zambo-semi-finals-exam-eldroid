package com.zambo.semi_final

import com.zambo.semi_final.data.Tweet
import com.zambo.semi_final.data.TweetRequest
import com.zambo.semi_final.data.TweetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TweetApiService {

    @GET("tweet/Zambo")
    fun getTweets(): Call<List<Tweet>>

    @GET("tweet/Zambo/{tweetId}")
    fun getTweetDetails(@Path("tweetId") tweetId: String): Call<Tweet>

    @POST("tweet/Zambo")
    fun createTweet(@Body tweetRequest: TweetRequest): Call<Tweet>

    @PUT("tweet/Zambo/{tweetId}")
    fun updateTweet(@Path("tweetId") tweetId: String, @Body tweetRequest: TweetRequest): Call<Tweet>

    @DELETE("tweet/Zambo/{tweetId}")
    fun deleteTweet(@Path("tweetId") tweetId: String): Call<TweetResponse>

}