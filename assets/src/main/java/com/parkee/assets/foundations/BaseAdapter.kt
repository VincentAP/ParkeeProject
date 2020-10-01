package com.parkee.assets.foundations

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Vincent Ardyan Putra on 30/9/2020.
 */

interface BaseItem {
    /**
     * This function to let onCreateViewHolder to inflate the layout for specific ViewHolder
     */
    fun layoutId(): Int

    /**
     * This function will give id to the item in RecyclerView
     * when override this, use hashcode to make it more unique
     * e.g. override fun id(): Long = (unique_item_id_in_your_class).hashCode().toLong()
     */
    fun id(): Long
}

abstract class BaseViewHolder<in T : BaseItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * @param listener implement this to register the listener in ViewHolder class
     */
    abstract fun bind(item: T, listener: (BaseItem, View) -> Unit)
    protected fun getContext(): Context = itemView.context
}

object ItemDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem)
            : Boolean = oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem)
            : Boolean = oldItem.toString() == newItem.toString()
}

abstract class ViewHolderFactory {
    abstract fun layoutId(): Int
    abstract fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    )
    abstract fun createViewHolder(parent: ViewGroup): BaseViewHolder<*>
}

class BaseRecyclerAdapter(
    private var listener: (BaseItem, View) -> Unit,
    private val viewHolderFactory: List<ViewHolderFactory> = listOf()
) : ListAdapter<BaseItem, BaseViewHolder<*>>(AsyncDifferConfig.Builder(ItemDiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return viewHolderFactory.firstOrNull { it.layoutId() == viewType }?.createViewHolder(parent)
            ?: throw Throwable(
                "ViewHolderFactory for ${parent.context.resources.getResourceEntryName(
                    viewType
                )} haven't registered yet."
            )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = getItem(position)
        viewHolderFactory.firstOrNull { it.layoutId() == item.layoutId() }
            ?.bindView(item, holder, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id()
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    fun setOnItemClicked(listener: (BaseItem, View) -> Unit) {
        this.listener = listener
    }
}