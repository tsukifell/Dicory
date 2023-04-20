package com.cepotdev.dicory.ui.customview

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.cepotdev.dicory.R

class PasswordEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val minPasswordLength = 8
    private var minLength: Int = minPasswordLength

    init {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        typeface = Typeface.create("sans-serif", Typeface.NORMAL)

        attrs.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PasswordEditText)
            minLength = typedArray.getInt(R.styleable.PasswordEditText_minLength, minPasswordLength)
            typedArray.recycle()
        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                error = if ((p0?.length ?: 0) < minLength) {
                    context.getString(R.string.password_err)
                } else {
                    null
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }
}