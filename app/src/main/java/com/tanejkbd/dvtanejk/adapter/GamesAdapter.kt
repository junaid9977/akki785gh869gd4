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
import com.tanejkbd.dvtanejk.models.GameModel
import com.bumptech.glide.Glide

class GamesAdapter(
    private val gamesArrayList: ArrayList<GameModel>,
    private val context: Context,
    private val paymentListener: PaymentListener
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameLayout: CardView = itemView.findViewById(R.id.mp_game_layout)
        val gameImage: ImageView = itemView.findViewById(R.id.mp_game)
        val gameTitle: TextView = itemView.findViewById(R.id.mp_game_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(gamesArrayList[position]) {
            holder.gameTitle.text = title
            Glide.with(context)
                .load(image)
                .into(holder.gameImage)
            holder.gameLayout.setOnClickListener {
                paymentListener.paymentIndex(position)
            }
        }
    }

    override fun getItemCount(): Int = gamesArrayList.size
}