package com.vvechirko.newrepository.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vvechirko.newrepository.R
import com.vvechirko.newrepository.toast
import com.vvechirko.newrepository.vm.provideViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = provideViewModel()

        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        val endlessScroll = recyclerView.endlessScroll {
            viewModel.fetch()
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.usersData.observe(this,
            { data -> adapter.data = data },
            { t -> toast(t) },
            { b -> swipeRefreshLayout.isRefreshing = b; endlessScroll.isLoading = b }
        )

        viewModel.userData.observe(this)
    }
}
