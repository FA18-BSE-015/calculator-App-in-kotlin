package com.example.calculator

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
private const val STATE_PENDING_OPERATION="PendingOperation"
private const val STATE_OPERAND="Operand"
private const val STATE_OPERAND_STORED="Operand_Stored"
class MainActivity : AppCompatActivity() {
    //declear properties 16 & 17
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) {findViewById<TextView>(R.id.operation)}
    //
    private var operand: Double?=null

    private var pendingOperation= "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get referance to the widgets
        result=findViewById(R.id.result)
        newNumber=findViewById(R.id.newNumber)
         val button0: Button=findViewById(R.id.button0)
        val button1: Button=findViewById(R.id.button1)
        val button2: Button=findViewById(R.id.button2)
        val button3: Button=findViewById(R.id.button3)
        val button4: Button=findViewById(R.id.button4)
        val button5: Button=findViewById(R.id.button5)
        val button6: Button=findViewById(R.id.button6)
        val button7: Button=findViewById(R.id.button7)
        val button8: Button=findViewById(R.id.button8)
        val button9: Button=findViewById(R.id.button9)
        val buttonDot:Button=findViewById(R.id.buttonDot)
        val buttonEqual=findViewById<Button>(R.id.buttonEqual)
        val buttonDivide=findViewById<Button>(R.id.buttonDivide)
        val buttonMultiply=findViewById<Button>(R.id.buttonMultiply)
        val buttonMinus=findViewById<Button>(R.id.buttonMinus)
        val buttonPlus=findViewById<Button>(R.id.buttonPlus)

        val listener= View.OnClickListener { v->
            val b= v as Button
            newNumber.append(b.text)
        }
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener=View.OnClickListener { v->
            val op= (v as Button).text.toString()
            try{
                val value=newNumber.text.toString().toDouble()
                performOperation(value, op)
            }catch (e:NumberFormatException){
                newNumber.setText("")
            }
            pendingOperation=op
            displayOperation.text=pendingOperation
        }
        buttonEqual.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
    }
    private fun performOperation(values:Double,operation:String){
        if (operand==null){
            operand=values
        }else{

            if (pendingOperation=="="){
                pendingOperation=operation
            }
            when (pendingOperation){
                "="->operand=values
                "/"-> operand = if(values==0.0){
                    Double.NaN
                }else{
                    operand!!/values
                }
                "*"->operand=operand!!*values
                "-"->operand=operand!!-values
                "+"->operand=operand!!+values
            }
        }
        //displayOperation.text=operation
        result.setText(operand.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand!=null){
            outState?.putDouble(STATE_OPERAND,operand!!)
            outState.putBoolean(STATE_OPERAND_STORED, true)
            }
        outState.putString(STATE_PENDING_OPERATION,pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.getBoolean(STATE_OPERAND_STORED, false)){
            operand = savedInstanceState.getDouble(STATE_OPERAND)
        }else{
            operand=null
        }
        //operand = savedInstanceState.getDouble(STATE_OPERAND)
        pendingOperation= savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
        displayOperation.text=pendingOperation

    }

}