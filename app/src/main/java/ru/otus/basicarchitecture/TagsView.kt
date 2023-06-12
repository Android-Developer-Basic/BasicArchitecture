package ru.otus.basicarchitecture

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout

class TagsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val root: ConstraintLayout
    private val flow: Flow

    var tags: List<String> = listOf()
        set(value) {
            field = value
            drawTags()
        }
    var selectedTags: List<String> = listOf()
        set(value) {
            field = value
            markSelected()
        }

    var onTagClick: ((String) -> Unit)? = null

    init {
        root = LayoutInflater.from(context)
            .inflate(R.layout.tags_view, this, false) as ConstraintLayout
        addView(root)
        flow = root.findViewById(R.id.flow)
    }

    private fun clearTags() {
        (root.childCount - 1 .. 0).forEach {
            val view = root.getChildAt(it)
            if (view is TextView) {
                root.removeViewAt(it)
            }
        }
    }

    fun getBackgroundColor(tag: String) = if (selectedTags.contains(tag)) {
        Color.YELLOW
    } else {
        Color.TRANSPARENT
    }

    private fun drawTags() {
        clearTags()
        val ids = mutableListOf<Int>()
        tags.forEachIndexed { index, tag ->
            val id = 1000 + index
            val textView = TextView(context)
            textView.setTextAppearance(android.R.style.TextAppearance_Material_Headline)
            textView.id = id
            textView.text = "#$tag"
            textView.isClickable = true
            textView.setOnClickListener {
                onTagClick?.invoke(tag)
            }
            textView.setBackgroundColor(getBackgroundColor(tag))
            root.addView(textView)
            ids.add(id)
        }
        flow.referencedIds = ids.toIntArray()
    }

    private fun markSelected() {
        tags.forEachIndexed { index, tag ->
            val id = 1000 + index
            val textView = root.findViewById<TextView>(id)
            textView.setBackgroundColor(getBackgroundColor(tag))
        }
    }
}