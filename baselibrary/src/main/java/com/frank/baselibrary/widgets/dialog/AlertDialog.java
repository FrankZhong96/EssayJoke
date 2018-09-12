package com.frank.baselibrary.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.frank.baselibrary.R;

/**
 * Created by Frank on 2018/9/12.
 *
 * @fuction 万能的Dialog
 */
public class AlertDialog extends Dialog{


    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this,getWindow());
    }

    /**
     * 规范产品的组建，一般是由子类实现具体的组件过程
     */
    public static class Builder{

        private final AlertController.AlertParams P;

        /**
         * 为使用默认对话框创建一个生成器
         * @param context
         */
        public Builder(Context context) {
            this(context, R.style.dialog);//默认样式
        }

        /**
         * 创建一个使用显式主题的对话框
         * @param context
         * @param themeResId
         */
        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        // 设置布局内容的view
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        // 设置布局内容的layoutId
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }

        // 设置文本
        public Builder setText(int viewId,CharSequence text){
            P.mTextArray.put(viewId,text);
            return this;
        }

        // 设置点击事件
        public Builder setOnClickListener(int view , View.OnClickListener listener){
            P.mClickArray.put(view,listener);
            return this;
        }

        // 配置一些万能的参数
        public Builder fullWidth(){
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder formBottom(boolean isAnimation){
            if(isAnimation){
                P.mAnimations = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }


        /**
         * 设置Dialog的宽高
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height){
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 添加默认动画
         * @return
         */
        public Builder addDefaultAnimation(){
            P.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         * @param styleAnimation
         * @return
         */
        public Builder setAnimations(int styleAnimation){
            P.mAnimations = styleAnimation;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener) setOnDismissListener}.</p>
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Creates an {@link android.app.AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link android.app.AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
