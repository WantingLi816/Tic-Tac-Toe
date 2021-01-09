package com.example.tictactoe

import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var gameState = arrayListOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    val winningPosition = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), listOf(0, 3, 6),
        listOf(1, 4, 7), listOf(2, 5, 8), listOf(0, 4, 8), listOf(2, 4, 6)
    )
    var activeGame = true

    // 0 is circle, 1 is cross indicate which image should appear
    private var activePlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.translationY = -500f
    }

    fun dropIn(view: View) {
        val image = view as ImageView

        val tappedSlot = view.tag.toString().toInt()

        // make sure slot is empty and game is active
        if (gameState[tappedSlot] == 2 && activeGame) {
            gameState[tappedSlot] = activePlayer

            // hide image out of screen
            image.translationY = -2000f

            // swtich betweem x and o
            if (activePlayer == 0) {
                image.setImageResource(R.drawable.tic_tac_toe_o)
                activePlayer = 1
            } else {
                image.setImageResource(R.drawable.tic_tac_toe_x)
                activePlayer = 0
            }

            // drop in image
            image.animate().translationYBy(2000f).rotation(720f).setDuration(500)

            // log game state
            Log.i("gameState", gameState.toString())

            detectTie()

            detectWinner()


        }
    }

    fun detectWinner() {
        for (combo in winningPosition) {
            if (gameState[combo[0]] == gameState[combo[1]] &&
                gameState[combo[1]] == gameState[combo[2]] &&
                gameState[combo[0]] != 2
            ) {
                binding.textView.animate().translationY(0f).setDuration(1000)
                if (activePlayer == 0) {
                    binding.textView.setText("Winner is X!")
                } else {
                    binding.textView.setText("Winner is O!")
                }
                activeGame = false
                binding.restartButton.animate().alpha(1f).setDuration(1500)
            }
        }
    }

    fun detectTie() {
        if (!gameState.contains(2)) {
            binding.textView.setText("Nobody wins!")
            binding.textView.animate().translationY(0f).setDuration(1000)
            activeGame = false
            binding.restartButton.animate().alpha(1f).setDuration(1500)
        }
    }


    fun restart(view: View) {
//        gameState = arrayListOf(2,2,2,2,2,2,2,2,2)
        for (slot in 0..8) {
            gameState[slot] = 2
        }

        binding.root.children.forEach {
            if (it !is Button && it !is TextView) {
                val image = it as ImageView
                if (image.tag != null) {
                    image.setImageDrawable(null)
                }
            }
        }

        activeGame = true
        binding.textView.translationY = -500f
        binding.restartButton.animate().alpha(0f).setDuration(1500)
    }
}