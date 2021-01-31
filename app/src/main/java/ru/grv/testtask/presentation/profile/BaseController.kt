package ru.grv.testtask.presentation.profile

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.base_controller.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import moxy.MvpDelegate
import ru.grv.testtask.R
import ru.grv.testtask.di.DaggerWrapper

abstract class BaseController: Controller(), /*OnLocker,*/ View.OnClickListener {

    enum class STATE {
        DEFAULT,
        LOADING
    }

    open val mvpDelegate: MvpDelegate<out BaseController> by lazy {
        MvpDelegate<BaseController>(this)
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initializeInjector()
    protected abstract fun clearInjector()
    protected abstract fun onCreated()

    // Views
    private var baseLifecycleListener: LifecycleListener? = null
    protected var contentLoadingView: View? = null
    protected var contentContainerView: FrameLayout? = null

    init {
        baseLifecycleListener = object : LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                onViewBound()
            }
            override fun onChangeStart(controller: Controller, changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
                super.onChangeStart(controller, changeHandler, changeType)

                if (changeType == ControllerChangeType.POP_ENTER) {
                    afterBack()
                }
            }

        }
        addLifecycleListener(baseLifecycleListener as LifecycleListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val rootView = inflater.inflate(R.layout.base_controller, container, false)
        contentContainerView = rootView.findViewById(R.id.contentContainer)
        contentLoadingView = rootView.findViewById(R.id.contentLoading)

        contentLoadingView?.setOnClickListener(this)

        contentContainerView?.removeAllViews()
        contentContainerView?.addView(inflater.inflate(getLayoutId(), null))
        mvpDelegate.onCreate(args)
        return rootView
    }

    open fun onViewBound() {
        onCreated()
    }

    open fun afterBack() {

    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)

        mvpDelegate.onDetach()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)

        mvpDelegate.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
        clearInjector()
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        initializeInjector()
    }

    protected fun getComponentManager() = DaggerWrapper.getComponentManager(applicationContext as Application)

    protected fun setState(state: STATE) {
        when (state) {
            STATE.DEFAULT -> {
                contentContainerView?.contentContainer?.visibility = View.VISIBLE
                contentLoadingView?.contentLoading?.visibility = View.GONE
            }
            STATE.LOADING -> {
                contentContainerView?.contentContainer?.visibility = View.VISIBLE
                contentLoadingView?.contentLoading?.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.contentLoading -> {}
        }
    }

    fun pushController(
        controller: Controller,
        tag: String
    ) = router.pushController(
        RouterTransaction.with(controller)
            .pushChangeHandler(HorizontalChangeHandler(200))
            .popChangeHandler(HorizontalChangeHandler(200))
            .tag(tag)
    )
}