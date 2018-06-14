package com.libra.core.widget

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.libra.core.R
import com.libra.utils.toast
import kotlinx.android.synthetic.main.layout_custom_dialog.*
import java.util.*

/**
 * Created by libra on 2017/6/17.
 */

class CustomDialog : AlertDialog {
    private var params: Params? = null

    private constructor(context: Context, params: Params) : super(context) {
        this.params = params
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, null, false))
        if (TextUtils.isEmpty(params?.mTitle)) {
            dialog_title.visibility = View.GONE
        } else {
            dialog_title.text = params?.mTitle
        }
        if (params?.mTitleColor != -1) {
            dialog_title.setTextColor(params?.mTitleColor as Int)
        }
        if (TextUtils.isEmpty(params?.mMessage)) {
            dialog_message.visibility = View.GONE
        } else {
            dialog_message.text = params?.mMessage
        }
        if (params?.isEdit as Boolean) {
            window!!.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            dialog_editLayout.visibility = View.VISIBLE
            dialog_editText.hint = params?.mEditTextHint
            if (params?.mEditTextNumber!! > 0) {
                dialog_editTextNumber.text = String.format(Locale.getDefault(), "%d/%d", 0,
                        params?.mEditTextNumber)
                dialog_editText.filters = arrayOf<InputFilter>(
                        InputFilter.LengthFilter(params?.mEditTextNumber as Int))
                dialog_editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                                   after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                               count: Int) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        dialog_editTextNumber.text = String.format(Locale.getDefault(), "%d/%d",
                                s?.length ?: 0,
                                params?.mEditTextNumber)
                    }
                })
            }
        }
        if (params?.mNegativeButtonListener != null) {
            dialog_negative.text = params?.mNegativeButtonText
            dialog_negative.setOnClickListener {
                params?.mNegativeButtonListener!!.onClick(this@CustomDialog,
                        DialogInterface.BUTTON_NEGATIVE)
                dismiss()
            }
        } else {
            dialog_negative.visibility = View.GONE
        }
        if (params?.mPositiveButtonListener != null) {
            dialog_positive.text = params?.mPositiveButtonText
            dialog_positive.setOnClickListener(View.OnClickListener {
                if (params?.isEdit!!) {
                    val text = editText
                    if (TextUtils.isEmpty(text)) {
                        context.toast("Please Input")
                        return@OnClickListener
                    }
                }
                params?.mPositiveButtonListener!!.onClick(this@CustomDialog,
                        DialogInterface.BUTTON_POSITIVE)
                dismiss()
            })
        } else {
            dialog_positive.visibility = View.GONE
        }
    }

    val editText: String
        get() = dialog_editText.text.toString()

    val edit: EditText
        get() = dialog_editText

    class Builder(context: Context) {
        private val P: Params = Params(context)

        val context: Context
            get() = P.mContext

        fun setTitle(@StringRes titleId: Int): Builder {
            P.mTitle = P.mContext.getText(titleId)
            return this
        }

        fun setTitle(title: CharSequence?): Builder {
            P.mTitle = title
            return this
        }

        fun setTitleColor(titleColor: Int): Builder {
            P.mTitleColor = titleColor
            return this
        }

        fun setMessage(@StringRes messageId: Int): Builder {
            P.mMessage = P.mContext.getText(messageId)
            return this
        }

        fun setMessage(message: CharSequence?): Builder {
            P.mMessage = message
            return this
        }

        val isEditText: Builder
            get() {
                P.isEdit = true
                return this
            }

        fun setEditTextHint(hint: String?): Builder {
            P.mEditTextHint = hint
            return this
        }

        fun setEditTextNumber(number: Int): Builder {
            P.mEditTextNumber = number
            return this
        }


        fun setPositiveButton(@StringRes textId: Int,
                              listener: DialogInterface.OnClickListener?): Builder {
            P.mPositiveButtonText = P.mContext.getText(textId)
            P.mPositiveButtonListener = listener
            return this
        }

        fun setPositiveButton(text: CharSequence?,
                              listener: DialogInterface.OnClickListener?): Builder {
            P.mPositiveButtonText = text
            P.mPositiveButtonListener = listener
            return this
        }

        fun setNegativeButton(@StringRes textId: Int,
                              listener: DialogInterface.OnClickListener?): Builder {
            P.mNegativeButtonText = P.mContext.getText(textId)
            P.mNegativeButtonListener = listener
            return this
        }

        fun setNegativeButton(text: CharSequence?,
                              listener: DialogInterface.OnClickListener?): Builder {
            P.mNegativeButtonText = text
            P.mNegativeButtonListener = listener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            P.mCancelable = cancelable
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }

        fun create(): CustomDialog {
            val dialog = CustomDialog(P.mContext, P)
            return dialog

        }

        fun show(): CustomDialog {
            val dialog = create()
            if (P.mContext is Activity) {
                if ((P.mContext as Activity).isFinishing) {
                    return dialog
                }
            }
            dialog.show()
            return dialog
        }
    }

    class Params(var mContext: Context) {
        var mTitle: CharSequence? = null
        var mTitleColor: Int = -1
        var mMessage: CharSequence? = null
        var isEdit: Boolean = false
        var mEditText: CharSequence? = null
        var mEditTextNumber: Int = 0
        var mPositiveButtonText: CharSequence? = null
        var mPositiveButtonListener: DialogInterface.OnClickListener? = null
        var mNegativeButtonText: CharSequence? = null
        var mNegativeButtonListener: DialogInterface.OnClickListener? = null
        var mCancelable: Boolean = false
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mEditTextHint: String? = null

        init {
            mCancelable = true
        }
    }

    companion object {
        /**
         * 提示框

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param message 内容
         * *
         * @param yes 确定按钮
         * *
         * @param yesListener 确定按钮事件
         * *
         * @param no 取消按钮
         * *
         * @param noListener 取消按钮事件
         * *
         * @return AlertDialog
         */
        fun showAlertDialog(context: Context, title: String, message: String, yes: String,
                            yesListener: DialogInterface.OnClickListener, no: String?,
                            noListener: DialogInterface.OnClickListener?): CustomDialog {
            return Builder(context).setTitle(title).setMessage(message).setPositiveButton(yes,
                    yesListener).setNegativeButton(
                    no, noListener).show()
        }

        /**
         * 提示框(ok,cancel)

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param message 内容
         * *
         * @param yesListener 确认事件
         * *
         * @return dialog
         */
        fun showCustomDialog(context: Context, title: String, message: String,
                             yesListener: DialogInterface.OnClickListener): CustomDialog {
            return showAlertDialog(context, title, message, context.getString(R.string.ok),
                    yesListener, context.getString(R.string.cancle),
                    DialogInterface.OnClickListener { _, _ -> })
        }

        /**
         * ok确认框

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param message 内容
         * *
         * @return dialog
         */
        fun showComfirmDialog(context: Context, title: String, message: String): CustomDialog {
            return showAlertDialog(context, title, message, context.getString(R.string.ok),
                    DialogInterface.OnClickListener { _, _ -> }, null, null)
        }


        /**
         * 显示带编辑的提示框

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param yesListener 事件
         * *
         * @return dialog
         */
        fun showEditDialog(context: Context, title: String,
                           yesListener: DialogInterface.OnClickListener): CustomDialog {
            return Builder(context).setTitle(title).isEditText.setPositiveButton(
                    R.string.ok, yesListener).setNegativeButton(R.string.cancle,
                    DialogInterface.OnClickListener { _, _ -> }).show()
        }

        /**
         * 显示带编辑的提示框

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param limitNumber 限制输入数量
         * *
         * @param yesListener 事件
         * *
         * @return dialog
         */
        fun showEditDialog(context: Context, title: String, limitNumber: Int,
                           yesListener: DialogInterface.OnClickListener): CustomDialog {
            return Builder(context).setTitle(title).isEditText.setEditTextNumber(
                    limitNumber).setPositiveButton(R.string.ok, yesListener).setNegativeButton(
                    R.string.cancle, DialogInterface.OnClickListener { _, _ -> }).show()
        }

        /**
         * 显示带编辑的提示框

         * @param context 上下文
         * *
         * @param title 标题
         * *
         * @param editHint 输入提示语
         * *
         * @param limitNumber 限制输入数量
         * *
         * @param yesListener 事件
         * *
         * @return dialog
         */
        fun showEditDialog(context: Context, title: String, editHint: String, limitNumber: Int,
                           yesListener: DialogInterface.OnClickListener): CustomDialog {
            return Builder(context).setTitle(title).isEditText.setEditTextHint(
                    editHint).setEditTextNumber(limitNumber).setPositiveButton(R.string.ok,
                    yesListener).setNegativeButton(
                    R.string.cancle, DialogInterface.OnClickListener { _, _ -> }).show()
        }
    }
}
