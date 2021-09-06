package za.co.clivewatts.gkweather.boilerplate

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {

    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null
    private var mainScope: CoroutineScope? = null

    fun attachView(view: View, viewLifecycle: Lifecycle, mainScope: CoroutineScope) {
        this.view = view
        this.viewLifecycle = viewLifecycle
        this.mainScope = mainScope

        viewLifecycle.addObserver(this)
    }

    protected fun view(): View? {
        return view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
        mainScope?.cancel("Termination application")
        mainScope = null
    }
}