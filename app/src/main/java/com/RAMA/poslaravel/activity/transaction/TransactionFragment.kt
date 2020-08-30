package com.lazday.poslaravel.activity.transaction


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.lazday.poslaravel.R
import com.lazday.poslaravel.activity.cart.CartActivity
import com.lazday.poslaravel.activity.transaction.detail.TransactionDetailFragment
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.database.PrefsManager
import com.lazday.poslaravel.data.model.transaction.DataTransaction
import com.lazday.poslaravel.data.model.transaction.ResponseTransactionList
import kotlinx.android.synthetic.main.fragment_transaction.*

class TransactionFragment : Fragment(), TransactionContract.View {

    private lateinit var prefsManager: PrefsManager
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var presenter: TransactionPresenter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        prefsManager = PrefsManager(context!!)
        transactionAdapter = TransactionAdapter(context!!, arrayListOf()){
                dataTransaction: DataTransaction, position: Int ->
            onClickTransaction(dataTransaction.no_faktur!!)
        }
        presenter = TransactionPresenter(this)
        initView(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = "Transaksi"
        presenter.getTransactionByUsername( prefsManager.prefsUsername )
    }

    override fun initView(view: View) {
        val rcvTransaction = view.findViewById<RecyclerView>(R.id.rcvTransaction)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        rcvTransaction!!.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        swipe.setOnRefreshListener {
            presenter.getTransactionByUsername(prefsManager.prefsUsername)
        }

        fab.setOnClickListener {
            context!!.startActivity(Intent(context, CartActivity::class.java))
        }
    }

    override fun onLoadingTransaction(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultTransaction(responseTransactionList: ResponseTransactionList) {
        val dataTransaction: List<DataTransaction> = responseTransactionList.dataTransaction
        transactionAdapter.updateTransaction(dataTransaction)
    }

    override fun onClickTransaction(invoice: String) {
        Constant.INVOICE = invoice
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.container,
                TransactionDetailFragment(), "transDetail")
            .commit()
    }
}
