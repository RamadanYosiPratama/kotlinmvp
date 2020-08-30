package com.lazday.poslaravel.activity.transaction.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.lazday.poslaravel.R
import com.lazday.poslaravel.data.Constant
import com.lazday.poslaravel.data.model.transaction.detail.DataDetail
import com.lazday.poslaravel.data.model.transaction.detail.ResponseTransactionDetail
import kotlinx.android.synthetic.main.fragment_transaction_detail.*

class TransactionDetailFragment : Fragment(), TransactionDetailContract.View {

    private lateinit var detailAdapter: TransactionDetailAdapter
    private lateinit var presenter: TransactionDetailPresenter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_detail, container, false)
        detailAdapter = TransactionDetailAdapter( context!!, arrayListOf() )
        presenter = TransactionDetailPresenter(this)
        initFragment(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = "Detail Transaksi"
        presenter.getTransactionByInvoice( Constant.INVOICE )
    }

    override fun initFragment(view: View) {
        val txvInvoice = view.findViewById<TextView>(R.id.txvInvoice)
        val rcvDetail = view.findViewById<RecyclerView>(R.id.rcvDetail)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        txvInvoice.text = Constant.INVOICE
        rcvDetail!!.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailAdapter
        }
        swipe.setOnRefreshListener {
            presenter.getTransactionByInvoice(Constant.INVOICE)
        }
    }

    override fun onLoadingDetail(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail) {
        val dataDetail: List<DataDetail> = responseTransactionDetail.dataDetail
        detailAdapter.updateTransaction(dataDetail)
    }

}
