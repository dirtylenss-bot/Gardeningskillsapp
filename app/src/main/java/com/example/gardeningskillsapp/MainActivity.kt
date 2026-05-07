package com.example.gardeningskillsapp

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat


class MainActivity : Activity() {
    private data class QuestionPage(
        val question: String,
        val correctAnswer: Boolean,
        val reviewAnswer: String
    )

    private val questionPages = listOf(
        QuestionPage(
            question = "Baking Soda makes flowers bloom.?",
            correctAnswer = false,
            reviewAnswer = "Sprinkling it around plants does not reliably increase blooms and can negatively affect soil pH."
        ),
        QuestionPage(
            question = "Eggshells benefit the composting system??",
            correctAnswer = true,
            reviewAnswer = "Add them to compost, not directly onto the soil, to slowly boost calcium."
        ),
        QuestionPage(
            question = "Coffee Ground makes great Fertilizer?",
            correctAnswer = false,
            reviewAnswer = "Used grounds contain low nutrients and can inhibit plant growth; instead, add them to compost."
        ),
        QuestionPage(
            question = "Using Copper pennies to prevent Fungi.",
            correctAnswer = true,
            reviewAnswer = "Bury pennies near plants to use copper for fighting fungus."
        ),
        QuestionPage(
            question = "It is better top lay compost on top of plant soil without digging, than to dig it in the ground.?",
            correctAnswer = true,
            reviewAnswer = "Lay compost on top of soil rather than tilling, which destroys soil structure.."
        ),
        QuestionPage(
            question = "There is no difference whether you are watering your plants in the day time or during the night time ?",
            correctAnswer = false,
            reviewAnswer = "Watering on sunny days does not burn leaves, though it is less efficient due to evaporation.."
        ),
        QuestionPage(
            question = "Pouring a diluted milk solution on plants can prevent mildew?",
            correctAnswer = true,
            reviewAnswer = "Use a 20% milk dilution to treat powdery mildew"
        ),
        QuestionPage(
            question = "Dish soap is a great pesticide substitute?",
            correctAnswer = false,
            reviewAnswer = "Dish soap can damage plants; use insecticidal soap specifically designed for gardens."
        ),
        QuestionPage(
            question = "Placing gravel in the bottom layer of a plant helps with draining the water supply?",
            correctAnswer = false,
            reviewAnswer = "Adding gravel/rocks to the bottom of pots decreases drainage by creating a perched water table, limiting root space.."
        ),
        QuestionPage(
            question = "You cannot grow a banana buy using a store bought banana",
            correctAnswer = true,
            reviewAnswer = "This is a myth; store-bought bananas are sterile hybrids and cannot grow new plants."
        )
    )

    private val selectedAnswers = MutableList<Boolean?>(questionPages.size) { null }
    private var currentQuestionIndex = 0

    private lateinit var cancelButton: Button
    private lateinit var pageTitleText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var messageText: TextView
    private lateinit var answerButtonsLayout: LinearLayout
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var feedbackText: TextView
    private lateinit var reviewButton: Button
    private lateinit var reviewText: TextView
    private lateinit var resultsListLayout: LinearLayout
    private lateinit var scoreText: TextView
    private lateinit var startButton: Button
    private lateinit var nextButton: Button
    private lateinit var quitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupButtons()
        showWelcomeScreen()
    }

    private fun bindViews() {
        cancelButton = findViewById(R.id.cancelButton)
        pageTitleText = findViewById(R.id.pageTitleText)
        questionNumberText = findViewById(R.id.questionNumberText)
        messageText = findViewById(R.id.messageText)
        answerButtonsLayout = findViewById(R.id.answerButtonsLayout)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        feedbackText = findViewById(R.id.feedbackText)
        reviewButton = findViewById(R.id.reviewButton)
        reviewText = findViewById(R.id.reviewText)
        resultsListLayout = findViewById(R.id.resultsListLayout)
        scoreText = findViewById(R.id.scoreText)
        startButton = findViewById(R.id.startButton)
        nextButton = findViewById(R.id.nextButton)
        quitButton = findViewById(R.id.quitButton)
    }

    private fun setupButtons() {
        startButton.setOnClickListener {
            currentQuestionIndex = 0
            selectedAnswers.fill(null)
            showQuestionPage()
        }
        cancelButton.setOnClickListener { showFinalScorePage() }
        trueButton.setOnClickListener { answerQuestion(true) }
        falseButton.setOnClickListener { answerQuestion(false) }
        reviewButton.setOnClickListener { reviewCurrentAnswer() }
        nextButton.setOnClickListener { moveToNextPage() }
        quitButton.setOnClickListener {

            if (quitButton.text.toString() == "Finish") {

                // Open farewell page
                showFarewellPage()

            } else if (quitButton.text.toString() == "Quit") {

                // Close app
                finishAffinity()
            }
        }
    }

    private fun showWelcomeScreen() {
        hideAllOptionalViews()
        pageTitleText.text = "Gardening Info App"
        pageTitleText.gravity = Gravity.CENTER
        messageText.text = "Welcome to the Gardening Skills Quiz!"
        messageText.gravity = Gravity.CENTER
        startButton.visibility = View.VISIBLE
    }

    private fun showQuestionPage() {
        hideAllOptionalViews()
        val questionPage = questionPages[currentQuestionIndex]

        pageTitleText.text = "Question"
        messageText.text = questionPage.question

        answerButtonsLayout.visibility = View.VISIBLE
        cancelButton.visibility = View.VISIBLE
    }

    private fun answerQuestion(answer: Boolean) {
        selectedAnswers[currentQuestionIndex] = answer

        val correct = answer == questionPages[currentQuestionIndex].correctAnswer
        val selectedText = if (answer) "True" else "False"
        val resultText = if (correct) "Correct" else "Wrong"

        feedbackText.text = "Your answer: $selectedText\n$resultText choice."
        feedbackText.visibility = View.VISIBLE

        reviewButton.visibility = View.VISIBLE


        nextButton.text = if (currentQuestionIndex == questionPages.lastIndex) "Final score" else "Next page"
        nextButton.visibility = View.VISIBLE
    }

    private fun reviewCurrentAnswer() {
        reviewText.text = questionPages[currentQuestionIndex].reviewAnswer
        reviewText.visibility = View.VISIBLE
        nextButton.text = if (currentQuestionIndex == questionPages.lastIndex) "Final score" else "Next page"
        nextButton.visibility = View.VISIBLE
    }

    private fun moveToNextPage() {
        if (currentQuestionIndex == questionPages.lastIndex) {
            showFinalScorePage()
        } else {
            currentQuestionIndex += 1
            showQuestionPage()
        }
    }

    private fun showFinalScorePage() {
        hideAllOptionalViews()

        var score = 0

        selectedAnswers.forEachIndexed { index, answer ->
            if (answer == questionPages[index].correctAnswer) {
                score++
            }
        }

        // Final score title
        pageTitleText.text = "Final Score"
        pageTitleText.setTextColor(android.graphics.Color.WHITE)

        // Feedback message
        if (score > 5) {
            messageText.text =
                "Well done you are ten steps closer to becoming a better gardener"
        } else {
            messageText.text =
                "You have failed to get a good score, You can always watch online videos or read books to help improve your agricultural knowledge"
        }

        messageText.setTextColor(android.graphics.Color.WHITE)

        // Show all selected answers
        resultsListLayout.removeAllViews()

        questionPages.forEachIndexed { index, questionPage ->

            val selectedAnswer =
                selectedAnswers[index]?.let {
                    if (it) "True" else "False"
                } ?: "Not Answered"

            val correctAnswer =
                if (questionPage.correctAnswer) "True" else "False"

            val resultText = TextView(this).apply {

                text =
                    "${index + 1}. ${questionPage.question}\n" +
                            "Selected Answer: $selectedAnswer\n" +
                            "Correct Answer: $correctAnswer"

                textSize = 18f
                setPadding(20, 20, 20, 20)

                setTextColor(android.graphics.Color.WHITE)

                setBackgroundColor(
                    android.graphics.Color.parseColor("#66000000")
                )
            }

            resultsListLayout.addView(resultText)
        }

        resultsListLayout.visibility = View.VISIBLE

        // Final score out of 10
        scoreText.text = "Final Score: $score/10"
        scoreText.setTextColor(android.graphics.Color.WHITE)
        scoreText.textSize = 24f
        scoreText.visibility = View.VISIBLE

        // Quit button
        quitButton.text = "Finish"
        quitButton.visibility = View.VISIBLE
    }

    private fun showFarewellPage() {

        hideAllOptionalViews()

        pageTitleText.text = "Farewell"

        messageText.text =
            "Thank you for playing our quiz, we hope you have learned more about gardening, Goodbye"

        pageTitleText.setTextColor(android.graphics.Color.WHITE)
        messageText.setTextColor(android.graphics.Color.WHITE)

        messageText.gravity = Gravity.CENTER

        quitButton.text = "Quit"
        quitButton.visibility = View.VISIBLE
    }


    private fun resultItem(index: Int, questionPage: QuestionPage): TextView {
        val selected = selectedAnswers[index]?.let { if (it) "True" else "False" } ?: "Not answered"
        val correct = if (questionPage.correctAnswer) "True" else "False"

        return TextView(this).apply {
            text = "${index + 1}. ${questionPage.question}\nSelected answer: $selected\nCorrect answer: $correct"
            textSize = 16f
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.garden_text))
            setPadding(20, 20, 20, 20)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 10)
            }
        }
    }

    private fun hideAllOptionalViews() {
        cancelButton.visibility = View.GONE
        questionNumberText.visibility = View.GONE
        answerButtonsLayout.visibility = View.GONE
        feedbackText.visibility = View.GONE
        reviewButton.visibility = View.GONE
        reviewText.visibility = View.GONE
        resultsListLayout.visibility = View.GONE
        scoreText.visibility = View.GONE
        startButton.visibility = View.GONE
        nextButton.visibility = View.GONE
        quitButton.visibility = View.GONE
        resultsListLayout.removeAllViews()
    }
}
