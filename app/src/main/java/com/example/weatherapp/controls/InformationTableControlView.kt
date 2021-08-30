package com.example.weatherapp.controls

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.marginStart
import androidx.core.view.setMargins
import com.example.weatherapp.R
import com.example.weatherapp.models.InformationField
import com.example.weatherapp.models.enums.ViewsGravityEnum
import kotlin.math.ceil
import kotlin.math.roundToInt

class InformationTableControlView @JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var itemsList: ArrayList<InformationField>
    private var mainView: View
    private lateinit var topLine: View
    private lateinit var bottomLine: View
    private var columns: Int
    private var gravity: ViewsGravityEnum
    private var lastRowGravity: ViewsGravityEnum

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InformationTable
        )
        columns = typedArray.getInt(R.styleable.InformationTable_columns, 1)
        gravity = ViewsGravityEnum.fromInt(
            typedArray.getInt(
                R.styleable.InformationTable_viewsGravity,
                0
            )
        )
        lastRowGravity = ViewsGravityEnum.fromInt(
            typedArray.getInt(
                R.styleable.InformationTable_lastRowViewsGravity,
                0
            )
        )

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mainView = inflater.inflate(R.layout.control_information_table_view, this)
    }

    fun createTable(items: ArrayList<InformationField>) {
        initTopAndBottomLine()
        initFields(items)
    }

    private fun initTopAndBottomLine() {
        topLine = View(context)
        bottomLine = View(context)

        topLine.setBackgroundColor(context.getColor(R.color.white))
        bottomLine.setBackgroundColor(context.getColor(R.color.white))

        topLine.id = View.generateViewId()
        bottomLine.id = View.generateViewId()

        val paramsTop = LayoutParams(
            LayoutParams.MATCH_PARENT,
            2
        )

        val paramsBottom = LayoutParams(
            LayoutParams.MATCH_PARENT,
            2
        )

        topLine.layoutParams = paramsTop
        bottomLine.layoutParams = paramsBottom


        this.addView(topLine)
        this.addView(bottomLine)

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(topLine.id, ConstraintSet.TOP, mainView.id, ConstraintSet.TOP)
        constraintSet.connect(
            bottomLine.id,
            ConstraintSet.BOTTOM,
            mainView.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    private fun initFields(items: ArrayList<InformationField>) {
        val horizontalLayoutsList = createHorizontalLayoutList(items)
        val verticalLayout = createVerticalLinearLayout()
        verticalLayout.id = View.generateViewId()

        for (i in 0 until horizontalLayoutsList.size) {
            verticalLayout.addView(horizontalLayoutsList[i])
            if (i != horizontalLayoutsList.size - 1) {
                val separator = createHorizontalSeparator()
                verticalLayout.addView(separator)
            }
        }

        this.addView(verticalLayout)

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            verticalLayout.id,
            ConstraintSet.TOP,
            topLine.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            verticalLayout.id,
            ConstraintSet.BOTTOM,
            bottomLine.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            verticalLayout.id,
            ConstraintSet.START,
            this.id,
            ConstraintSet.START,
            32
        )
        constraintSet.connect(
            verticalLayout.id,
            ConstraintSet.END,
            this.id,
            ConstraintSet.END,
            32
        )
        constraintSet.applyTo(this)
    }

    private fun createHorizontalLayoutList(items: ArrayList<InformationField>): ArrayList<LinearLayout> {
        val rows = ceil(items.size.toDouble() / columns.toDouble()).toInt()
        val layoutList = ArrayList<LinearLayout>()

        for (i in 0 until rows) {
            val rowItems = getRowItems(items, i)

            val infoFields = if (i == rows - 1){
                createInfoFields(rowItems, true)
            } else {
                createInfoFields(rowItems, false)
            }

            val layout = createLinearLayoutWithFields(rowItems.size, infoFields)
            layoutList.add(layout)
        }

        return layoutList
    }

    private fun createInfoFields(items: ArrayList<InformationField>, isLast: Boolean): ArrayList<InformationControlView> {
        val controlsList = ArrayList<InformationControlView>()
        for (item in items) {
            val infoCV = InformationControlView(context)
            infoCV.fillFields(item.title, item.description)

            val params = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                10.0f
            )
            params.setMargins(8)

            infoCV.layoutParams = params


            if (isLast){
                infoCV.titleTextView.gravity = getGravityFromEnum(lastRowGravity)
                infoCV.descriptionTextView.gravity = getGravityFromEnum(lastRowGravity)
            } else {
                infoCV.titleTextView.gravity = getGravityFromEnum(gravity)
                infoCV.descriptionTextView.gravity = getGravityFromEnum(gravity)
            }

            controlsList.add(infoCV)
        }
        return controlsList
    }

    private fun getGravityFromEnum(gravity: ViewsGravityEnum): Int {
        return when(gravity){
            ViewsGravityEnum.START -> Gravity.START
            ViewsGravityEnum.CENTER -> Gravity.CENTER_HORIZONTAL
            ViewsGravityEnum.END -> Gravity.END
        }
    }

    private fun getRowItems(
        items: ArrayList<InformationField>,
        row: Int
    ): ArrayList<InformationField> {
        val rowItems = ArrayList<InformationField>()
        for (i in 0 until columns) {
            if ((row * columns + i) >= items.size) {
                break
            } else {
                rowItems.add(items[row * columns + i])
            }
        }
        return rowItems
    }

    private fun createLinearLayoutWithFields(
        itemsSize: Int,
        fields: ArrayList<InformationControlView>
    ): LinearLayout {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.weightSum = (itemsSize * 10).toFloat()
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.layoutParams = params

        for (field in fields) {
            layout.addView(field)
        }
        return layout
    }

    private fun createVerticalLinearLayout(): LinearLayout {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(16, 0, 16, 0)
        layout.layoutParams = params
        return layout
    }

    private fun createHorizontalSeparator(): View {
        val view = View(context)
        view.setBackgroundColor(context.getColor(R.color.white))
        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            1
        )
        view.layoutParams = params
        return view
    }
}
