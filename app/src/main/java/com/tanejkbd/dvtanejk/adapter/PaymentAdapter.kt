package com.tanejkbd.dvtanejk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tanejkbd.dvtanejk.R
import com.tanejkbd.dvtanejk.listener.PaymentListener
import com.tanejkbd.dvtanejk.models.PaymentModel
import com.bumptech.glide.Glide

class PaymentAdapter(
    private val paymentList: ArrayList<PaymentModel>,
    private val context: Context,
    private val paymentListener: PaymentListener
) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val paymentLogo: ImageView = itemView.findViewById(R.id.payment_btn_1_logo)
        val paymentName: TextView = itemView.findViewById(R.id.payment_btn_1_name)
        val paymentDesc: TextView = itemView.findViewById(R.id.payment_btn_1_desc)
        val paymentCoins: TextView = itemView.findViewById(R.id.payment_btn_1_coins)
        val paymentCard: CardView = itemView.findViewById(R.id.payment_btn_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_btn_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(paymentList[position]) {
            holder.paymentName.text = payment_btn_name
            holder.paymentDesc.text = payment_btn_desc
            holder.paymentCoins.text = payment_btn_coins
            Glide.with(context)
                .load(payment_btn_logo)
                .into(holder.paymentLogo)

            holder.paymentCard.setOnClickListener {
                paymentListener.paymentIndex(position)
            }
        }
    }

    override fun getItemCount(): Int = paymentList.size
}