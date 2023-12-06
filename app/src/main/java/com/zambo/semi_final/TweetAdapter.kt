package com.zambo.semi_final

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zambo.semi_final.data.Tweet
import com.zambo.semi_final.data.TweetRequest
import com.zambo.semi_final.databinding.EditTextDialogBinding
import com.zambo.semi_final.databinding.ItemTweetBinding

class TweetAdapter(private val context: Context) : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {
    private var tweets: List<Tweet> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setTweets(tweets: List<Tweet>) {
        this.tweets = tweets
        notifyDataSetChanged()
    }

    class TweetViewHolder(val binding: ItemTweetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        return TweetViewHolder(ItemTweetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val currentTweet = tweets[position]
        holder.binding.textName.text = currentTweet.name
        holder.binding.textDescription.text = currentTweet.description
        holder.binding.deleteImageButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Delete ${currentTweet.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    MainActivity.tweetApi?.deleteTweet(currentTweet.id)
                    Toast.makeText(context, "${currentTweet.id} successfully deleted", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("No") { _, _ ->
                    Toast.makeText(context, "Cancelled Deletion", Toast.LENGTH_SHORT).show()
                }
            alertDialog.show()
        }
        holder.binding.editImageButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogBinding = EditTextDialogBinding.inflate(LayoutInflater.from(context))
            builder.apply {
                setTitle("Modify ${currentTweet.name}?")
                setView(dialogBinding.root)
                dialogBinding.uniqueIdEditText.setText(currentTweet.id)
                dialogBinding.nameEditText.setText(currentTweet.name)
                dialogBinding.descriptionEditText.setText(currentTweet.description)
                setPositiveButton("OK") { _, _ ->
                    MainActivity.tweetApi?.updateTweet(
                        currentTweet.id,
                        TweetRequest(
                            dialogBinding.nameEditText.text.toString(),
                            dialogBinding.descriptionEditText.text.toString()
                        )
                    )
                    Toast.makeText(context, "${currentTweet.name} is updated", Toast.LENGTH_SHORT).show()
                }
            }
            builder.create().show()
        }
    }

    override fun getItemCount(): Int = tweets.size

}