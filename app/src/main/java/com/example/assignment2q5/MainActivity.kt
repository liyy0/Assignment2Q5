package com.example.assignment2q5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for every button in the layout, set the onClickListener to the function
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener { buttonClicked(button1.text) }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener { buttonClicked(button2.text) }
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener { buttonClicked(button3.text) }
        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener { buttonClicked(button4.text) }
        val button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener { buttonClicked(button5.text) }
        val button6 = findViewById<Button>(R.id.button6)
        button6.setOnClickListener { buttonClicked(button6.text) }
        val button7 = findViewById<Button>(R.id.button7)
        button7.setOnClickListener { buttonClicked(button7.text) }
        val button8 = findViewById<Button>(R.id.button8)
        button8.setOnClickListener { buttonClicked(button8.text) }
        val button9 = findViewById<Button>(R.id.button9)
        button9.setOnClickListener { buttonClicked(button9.text) }
        val button0 = findViewById<Button>(R.id.button0)
        button0.setOnClickListener { buttonClicked(button0.text) }

        val buttonplus = findViewById<Button>(R.id.buttonplus)
        buttonplus.setOnClickListener { buttonClicked(buttonplus.text) }
        val buttonminus = findViewById<Button>(R.id.buttonminus)
        buttonminus.setOnClickListener { buttonClicked(buttonminus.text) }
        val buttontimes = findViewById<Button>(R.id.buttontimes)
        buttontimes.setOnClickListener { buttonClicked(buttontimes.text) }
        val buttondivide = findViewById<Button>(R.id.buttondivide)
        buttondivide.setOnClickListener { buttonClicked(buttondivide.text) }
        val buttondot = findViewById<Button>(R.id.buttondot)
        buttondot.setOnClickListener { buttonClicked(buttondot.text) }
        val buttonsqrt = findViewById<Button>(R.id.buttonsqrt)
        buttonsqrt.setOnClickListener { buttonClicked(buttonsqrt.text) }


        // when press eauqls, either calculate the result or clear the screen
        val buttonequals = findViewById<Button>(R.id.buttonequals)
        buttonequals.setOnClickListener { buttoncal() }

    }


    fun buttonClicked(text: CharSequence) {
        var stack = findViewById<android.widget.TextView>(R.id.textshow)
        if (stack.text.toString().startsWith('=')){
            // check if there's already a result on the screen.
            stack.setText("= have result, press equal first")
            return
        }
        var res = stack.text.toString()+text.toString()

//        Toast.makeText(this, res, Toast.LENGTH_LONG).show()
        stack.setText(res)
    }

    fun buttoncal() {
        var stack = findViewById<android.widget.TextView>(R.id.textshow)
        var res = stack.text.toString()

//        var res = "1sqrt2"
        if (res.startsWith('=') || res == ""){
            stack.setText("")
        }else{
            // regex using gpt to generate
            // https://chat.openai.com/share/48c00eb1-9610-436e-b093-65c14ed9fda4
            val regex = Regex("(\\d+(\\.\\d+)?|\\+|-|\\*|/|sqrt)")
            var x = regex.findAll(res).map { it.value }.toList()
//            Toast.makeText(this, x.toString(), Toast.LENGTH_LONG).show()
            var answer = try {
                calculate(x)
            }
            catch (e: Exception){
                "calculation formula Error"
            }
            stack.setText("=$answer")
        }



    }
    // reference from this post fix calculator
    // https://stackoverflow.com/questions/22579121/stack-calculator-postfix-python
    fun calculate(ls: List<String>): Any {
        var stack: MutableList<Double> = mutableListOf()
        var x = 0
        while (ls.isNotEmpty() && x < ls.size){
            var cur = ls[x]
            if (isNumber(cur)) {
                if (stack.size > 0){
                    return "more than 1 element in stack, Error"
                }
                stack.add(cur.toDouble())
                x += 1
            }
            else{
                if (stack.isEmpty()){
                    return "stack is empty Error"
                }
                if (cur == "sqrt"){
                    var num = stack.removeAt(stack.size-1)
                    stack.add(Math.sqrt(num))
                    x += 1
                }
                else{
                    var num1 = stack.removeAt(stack.size-1)
                    var num2 = ls[x+1].toDouble()
                    x += 1
                    if (cur == "+"){
                        stack.add(num1+num2)
                        x += 1
                    }
                    else if (cur == "-"){
                        stack.add(num1-num2)
                        x += 1
                    }
                    else if (cur == "*"){
                        stack.add(num1*num2)
                        x += 1
                    }
                    else if (cur == "/"){
                        if (num2 == 0.0){
                            print("Cannot divide by 0")
                            return "Cannot divide by 0"
                        }
                        stack.add(num1/num2)
                        x += 1
                    }

                }
            }
        }
        return stack.removeAt(stack.size-1).toString()

    }

    // reference from this post to check if the string is number
    // https://stackoverflow.com/questions/65474874/kotlin-check-if-string-is-numeric
    fun isNumber(input: String): Boolean {
        val integerChars = "0123456789"
        var dotOccurred = 0
        return input.all { it in integerChars || it == '.' && dotOccurred++ < 1 }
    }


}