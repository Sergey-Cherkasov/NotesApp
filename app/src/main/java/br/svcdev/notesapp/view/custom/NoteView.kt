package br.svcdev.notesapp.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import br.svcdev.notesapp.R

class NoteView(context: Context?) : View(context) {

    private var mNotePaint: Paint? = null
    // Background of the Note view
    private var mNoteBackgroundColor = 0
    // Height of the Note view
    private var mHeight = 0
    // Width of the Note view
    private var mWidth = 0

    constructor(context: Context?, attrs: AttributeSet?) : this(context) {
        initAttrs(context, attrs)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs) {
        initAttrs(context, attrs)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            this(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
        init()
    }

    private fun initAttrs(context: Context?, attrs: AttributeSet?) {
        val typedArray: TypedArray = context!!
            .obtainStyledAttributes(attrs, R.styleable.NoteView, 0, 0)
        mNoteBackgroundColor =
            typedArray.getColor(R.styleable.NoteView_android_background, Color.RED)
        mHeight = typedArray.getInt(R.styleable.NoteView_android_layout_height, 30)
        mWidth = typedArray.getInt(R.styleable.NoteView_android_layout_width, 60)
        typedArray.recycle()
    }

    private fun init() {
        mNotePaint = Paint()
        mNotePaint!!.color = Color.BLACK
        mNotePaint!!.style = Paint.Style.STROKE
        mNotePaint!!.strokeWidth = 4F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w - paddingLeft - paddingRight
        mHeight = h - paddingTop - paddingBottom
        if (mWidth <= mHeight && mWidth >= 30 && mHeight >= 60) {
            createNotePath()
        }
    }

    private fun createNotePath() {
    }

}