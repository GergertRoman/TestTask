package ru.grv.testtask.common.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpDelegate

abstract class BaseMvpController : BaseController {
    open val mvpDelegate: MvpDelegate<out BaseMvpController> by lazy {
        MvpDelegate<BaseMvpController>(this)
    }

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = super.onCreateView(inflater, container)

        mvpDelegate.onCreate(args)

        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)

        mvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        mvpDelegate.onDestroy()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)

        mvpDelegate.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
    }
}