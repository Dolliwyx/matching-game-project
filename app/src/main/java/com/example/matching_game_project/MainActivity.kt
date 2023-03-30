package com.example.matching_game_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.matching_game_project.R.drawable.*
import com.example.matching_game_project.R.id.*



class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images: MutableList<Int> = mutableListOf(apple, banana, grapes, orange, apple, banana,
                grapes, orange, apple, banana, grapes, orange, apple, banana, grapes, orange)


        val buttons = arrayOf(Card1, Card2, Card3, Card4, Card5, Card6, Card7, Card8, Card9, Card10,
                Card11, Card12, Card13, Card14, Card15, Card16)

        val cardBack = cardasset
        var clicked = 0
        var turnOver = false
        var lastClicked = -1
        var firstClickedButton: AppCompatButton? = null
        var matches = 0

        images.shuffle()

        for (i in 0..15) {

            val button = findViewById<AppCompatButton>(buttons[i])
            button.setBackgroundResource(cardBack)
            button.text = "cardBack"
            button.textSize = 0F
            button.setOnClickListener {
                if (button.text == "cardBack" && !turnOver) {
                    button.setText(images[i])
                    button.animate().apply {
                        rotationY(90f)
                        duration = 200L
                        withEndAction {
                            button.setBackgroundResource(images[i])
                            button.animate().apply {
                                rotationY(0f)
                                duration = 200L
                                start()
                            }
                        }
                        start()
                    }
                    if (clicked == 0) {
                        lastClicked = i
                        firstClickedButton = button
                    }
                    clicked++
                    firstClickedButton?.isClickable = false
                } else if (button.text !in "cardBack") {
                    button.setBackgroundResource(cardBack)
                    button.text = "cardBack"
                    clicked--
                }

                if (clicked == 2) {
                    val buttonLastClicked = findViewById<AppCompatButton>(buttons[lastClicked])
                    turnOver = true
                    if (button.text == buttonLastClicked.text) {
                        button.isClickable = false
                        buttonLastClicked.isClickable = false
                        turnOver = false
                        clicked = 0
                        matches++
                        if (matches == 8) Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
                    } else {
                        button.text = "cardBack"
                        buttonLastClicked.text = "cardBack"
                        Handler(Looper.getMainLooper()).postDelayed({
                            button.animate().apply {
                                rotationY(90f)
                                duration = 200L
                                withEndAction {
                                    button.setBackgroundResource(cardBack)
                                    button.animate().apply {
                                        rotationY(0f)
                                        duration = 200L
                                        start()
                                    }
                                }
                                start()
                            }
                            buttonLastClicked.animate().apply {
                                rotationY(90f)
                                duration = 200L
                                withEndAction {
                                    buttonLastClicked.setBackgroundResource(cardBack)
                                    buttonLastClicked.animate().apply {
                                        rotationY(0f)
                                        duration = 200L
                                        start()
                                    }
                                }
                                start()
                            }

                            turnOver = false
                            clicked = 0

                            firstClickedButton?.isClickable = true
                        }, 700)
                    }
                } else if (clicked == 0) {
                    turnOver = false
                }
            }
        }
    }



}